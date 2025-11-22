package com.chordknight.bot.features.impl

import com.chordknight.bot.features.BaseFeature
import com.chordknight.bot.state.GuildStateManager
import com.chordknight.chess.ChessMove
import com.chordknight.chess.Position
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * Chess game feature
 */
class ChessFeature : BaseFeature("chess", "Play chess games in Discord"), KoinComponent {
    private val stateManager: GuildStateManager by inject()
    
    override suspend fun handleCommand(
        event: MessageReceivedEvent,
        command: String,
        args: List<String>
    ): Boolean {
        if (!event.isFromGuild) return false
        val guildId = event.guild.id
        
        return when (command) {
            "chess" -> {
                when (args.firstOrNull()) {
                    "start" -> {
                        stateManager.startChessGame(guildId)
                        event.channel.sendMessage("♟️ Chess game started! Use `!chess move <from> <to>` to make moves.").queue()
                        true
                    }
                    "move" -> {
                        if (args.size < 3) {
                            event.channel.sendMessage("❌ Usage: !chess move <from> <to> (e.g., !chess move e2 e4)").queue()
                            return true
                        }
                        
                        val engine = stateManager.getChessGame(guildId)
                        if (engine == null) {
                            event.channel.sendMessage("❌ No active chess game. Use `!chess start` to begin.").queue()
                            return true
                        }
                        
                        try {
                            val from = Position.fromAlgebraic(args[1])
                            val to = Position.fromAlgebraic(args[2])
                            val move = ChessMove(from, to)
                            
                            if (engine.makeMove(move)) {
                                event.channel.sendMessage("✅ Move made: ${args[1]} → ${args[2]}").queue()
                            } else {
                                event.channel.sendMessage("❌ Invalid move!").queue()
                            }
                        } catch (e: Exception) {
                            event.channel.sendMessage("❌ Invalid move notation. Use algebraic notation (e.g., e2 e4)").queue()
                        }
                        true
                    }
                    "board" -> {
                        val engine = stateManager.getChessGame(guildId)
                        if (engine == null) {
                            event.channel.sendMessage("❌ No active chess game.").queue()
                        } else {
                            event.channel.sendMessage("♟️ Board: ${engine.toFen()}").queue()
                        }
                        true
                    }
                    else -> {
                        event.channel.sendMessage("♟️ Chess commands: `start`, `move <from> <to>`, `board`").queue()
                        true
                    }
                }
            }
            else -> false
        }
    }
}
