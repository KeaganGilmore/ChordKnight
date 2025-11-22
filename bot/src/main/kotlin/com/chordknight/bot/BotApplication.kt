package com.chordknight.bot

import com.chordknight.bot.config.BotConfig
import com.chordknight.bot.config.DependencyInjection
import com.chordknight.bot.features.FeatureLoader
import kotlinx.coroutines.runBlocking
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.requests.GatewayIntent
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin
import org.slf4j.LoggerFactory

/**
 * Main Discord bot application
 */
class BotApplication : KoinComponent {
    private val logger = LoggerFactory.getLogger(BotApplication::class.java)
    private val config: BotConfig by inject()
    private val featureLoader: FeatureLoader by inject()
    
    /**
     * Start the bot
     */
    fun start() = runBlocking {
        logger.info("Starting ChordKnight Bot...")
        
        // Load features
        featureLoader.loadFeatures()
        
        // Build JDA instance
        val jda = JDABuilder.createDefault(config.discordToken)
            .enableIntents(
                GatewayIntent.GUILD_MESSAGES,
                GatewayIntent.GUILD_VOICE_STATES,
                GatewayIntent.MESSAGE_CONTENT
            )
            .setActivity(Activity.playing("Chess & Music | ${config.prefix}help"))
            .addEventListeners(CommandListener(config.prefix, featureLoader))
            .build()
        
        logger.info("Bot started successfully!")
        
        // Wait for JDA to be ready
        jda.awaitReady()
        logger.info("JDA is ready. Connected to ${jda.guilds.size} guilds")
        
        // Shutdown hook
        Runtime.getRuntime().addShutdownHook(Thread {
            runBlocking {
                logger.info("Shutting down bot...")
                featureLoader.shutdownFeatures()
                jda.shutdown()
                logger.info("Bot shutdown complete")
            }
        })
    }
}

/**
 * Command listener for handling bot commands
 */
class CommandListener(
    private val prefix: String,
    private val featureLoader: FeatureLoader
) : ListenerAdapter() {
    private val logger = LoggerFactory.getLogger(CommandListener::class.java)
    
    override fun onMessageReceived(event: MessageReceivedEvent) {
        // Ignore bot messages
        if (event.author.isBot) return
        
        val content = event.message.contentRaw
        
        // Check if message starts with prefix
        if (!content.startsWith(prefix)) return
        
        // Parse command and args
        val parts = content.substring(prefix.length).trim().split("\\s+".toRegex())
        if (parts.isEmpty()) return
        
        val command = parts[0].lowercase()
        val args = parts.drop(1)
        
        logger.debug("Command received: $command with args: $args")
        
        // Special help command
        if (command == "help") {
            handleHelpCommand(event)
            return
        }
        
        // Try to handle with features
        runBlocking {
            val features = featureLoader.getEnabledFeatures()
            for (feature in features) {
                try {
                    if (feature.handleCommand(event, command, args)) {
                        logger.debug("Command '$command' handled by feature: ${feature.name}")
                        return@runBlocking
                    }
                } catch (e: Exception) {
                    logger.error("Error handling command '$command' in feature ${feature.name}", e)
                    event.channel.sendMessage("❌ Error executing command: ${e.message}").queue()
                    return@runBlocking
                }
            }
            
            // No feature handled the command
            event.channel.sendMessage("❌ Unknown command: `$command`. Use `${prefix}help` for available commands.").queue()
        }
    }
    
    private fun handleHelpCommand(event: MessageReceivedEvent) {
        val features = featureLoader.getEnabledFeatures()
        val helpText = buildString {
            appendLine("🎮 **ChordKnight Bot Commands**")
            appendLine()
            appendLine("**General:**")
            appendLine("`${prefix}help` - Show this help message")
            appendLine("`${prefix}ping` - Test bot responsiveness")
            appendLine()
            
            if (features.any { it.name == "chess" }) {
                appendLine("**Chess:**")
                appendLine("`${prefix}chess start` - Start a chess game")
                appendLine("`${prefix}chess move <from> <to>` - Make a move (e.g., e2 e4)")
                appendLine("`${prefix}chess board` - Show current board")
                appendLine()
            }
            
            if (features.any { it.name == "music" }) {
                appendLine("**Music:**")
                appendLine("`${prefix}play <url|query>` - Play a track")
                appendLine("`${prefix}pause` - Pause playback")
                appendLine("`${prefix}resume` - Resume playback")
                appendLine("`${prefix}skip` - Skip current track")
                appendLine("`${prefix}queue` - Show music queue")
                appendLine("`${prefix}stop` - Stop and clear queue")
                appendLine()
            }
            
            appendLine("**Enabled Features:** ${features.joinToString(", ") { it.name }}")
        }
        
        event.channel.sendMessage(helpText).queue()
    }
}

/**
 * Main entry point
 */
fun main() {
    // Initialize Koin
    startKoin {
        modules(DependencyInjection.getAllModules())
    }
    
    // Start bot
    val app = BotApplication()
    app.start()
}
