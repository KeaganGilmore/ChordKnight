package com.chordknight.discord

import com.chordknight.core.feature.Feature
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import org.slf4j.LoggerFactory

/**
 * Listener that routes Discord interactions to registered features
 */
class CommandListener(private val features: List<Feature>) : ListenerAdapter() {
    private val logger = LoggerFactory.getLogger(CommandListener::class.java)
    private val scope = CoroutineScope(Dispatchers.Default)

    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) {
        scope.launch {
            try {
                when (event.name) {
                    "ping" -> handlePing(event)
                    else -> {
                        logger.warn("Unknown command: ${event.name}")
                        event.reply("Unknown command!").setEphemeral(true).queue()
                    }
                }
            } catch (e: Exception) {
                logger.error("Error handling command: ${event.name}", e)
                event.reply("An error occurred while processing your command.")
                    .setEphemeral(true)
                    .queue()
            }
        }
    }

    private fun handlePing(event: SlashCommandInteractionEvent) {
        val gatewayPing = event.jda.gatewayPing
        event.reply("🏓 Pong! Gateway ping: ${gatewayPing}ms").queue()
    }
}
