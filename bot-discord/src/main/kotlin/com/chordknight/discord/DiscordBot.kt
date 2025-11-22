package com.chordknight.discord

import com.chordknight.core.config.Config
import com.chordknight.core.feature.Feature
import kotlinx.coroutines.*
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.interactions.commands.build.Commands
import net.dv8tion.jda.api.requests.GatewayIntent
import org.slf4j.LoggerFactory

/**
 * Main Discord bot class that manages JDA and features
 */
class DiscordBot(
    private val config: Config,
    private val features: List<Feature>
) {
    private val logger = LoggerFactory.getLogger(DiscordBot::class.java)
    private lateinit var jda: JDA
    private val scope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    suspend fun start() {
        logger.info("Starting Discord bot...")

        // Initialize all features
        features.forEach { feature ->
            logger.info("Initializing feature: ${feature.name}")
            feature.initialize()
        }

        // Build and start JDA
        jda = JDABuilder.createDefault(config.discord.token)
            .enableIntents(
                GatewayIntent.GUILD_MESSAGES,
                GatewayIntent.GUILD_VOICE_STATES,
                GatewayIntent.MESSAGE_CONTENT
            )
            .setActivity(Activity.listening("/help"))
            .addEventListeners(CommandListener(features))
            .build()

        jda.awaitReady()
        logger.info("Discord bot connected as ${jda.selfUser.name}")

        // Register slash commands
        registerCommands()

        // Start background jobs for all features
        features.forEach { feature ->
            feature.startBackgroundJobs(scope)
        }

        logger.info("Discord bot started successfully")
    }

    private fun registerCommands() {
        logger.info("Registering slash commands...")

        val commands = listOf(
            Commands.slash("ping", "Check if the bot is responsive")
        )

        jda.updateCommands()
            .addCommands(commands)
            .queue(
                { logger.info("Successfully registered ${commands.size} commands") },
                { error -> logger.error("Failed to register commands", error) }
            )
    }

    suspend fun shutdown() {
        logger.info("Shutting down Discord bot...")

        // Shutdown features
        features.forEach { feature ->
            logger.info("Shutting down feature: ${feature.name}")
            feature.shutdown()
        }

        // Cancel background jobs
        scope.cancel()

        // Shutdown JDA
        jda.shutdown()
        if (!jda.awaitShutdown(10, java.util.concurrent.TimeUnit.SECONDS)) {
            logger.warn("JDA did not shut down gracefully, forcing shutdown")
            jda.shutdownNow()
        }

        logger.info("Discord bot shut down successfully")
    }

    fun getJDA(): JDA = jda
}
