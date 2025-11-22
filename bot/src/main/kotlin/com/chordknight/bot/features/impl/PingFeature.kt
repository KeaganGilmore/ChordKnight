package com.chordknight.bot.features.impl

import com.chordknight.bot.features.BaseFeature
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

/**
 * Simple ping/pong feature for testing
 */
class PingFeature : BaseFeature("ping", "Responds to ping commands") {
    
    override suspend fun handleCommand(
        event: MessageReceivedEvent,
        command: String,
        args: List<String>
    ): Boolean {
        if (command == "ping") {
            event.channel.sendMessage("🏓 Pong!").queue()
            return true
        }
        return false
    }
}
