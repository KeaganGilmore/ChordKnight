package com.chordknight.api

import com.chordknight.chess.ChessService
import com.chordknight.music.MusicService
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.serialization.Serializable
import org.koin.ktor.ext.inject

fun Route.guildsRoutes() {
    route("/guilds") {
        get {
            // Stub: would return list of guilds the bot is in
            call.respond(
                listOf(
                    GuildInfo("123456789", "Test Server", "https://example.com/icon.png")
                )
            )
        }

        get("/{id}") {
            val guildId = call.parameters["id"]
            call.respond(
                GuildInfo(guildId ?: "unknown", "Test Server", "https://example.com/icon.png")
            )
        }
    }
}

fun Route.musicRoutes() {
    val musicService by inject<MusicService>()

    route("/guilds/{guildId}/music") {
        get("/player") {
            val guildId = call.parameters["guildId"] ?: return@get call.respond(
                mapOf("error" to "Missing guildId")
            )

            val playerState = musicService.getPlayerState(guildId)
            if (playerState != null) {
                call.respond(playerState)
            } else {
                call.respond(mapOf("error" to "No active player"))
            }
        }

        get("/queue") {
            val guildId = call.parameters["guildId"] ?: return@get call.respond(
                mapOf("error" to "Missing guildId")
            )

            val queue = musicService.getQueue(guildId)
            call.respond(mapOf("queue" to queue))
        }
    }
}

fun Route.chessRoutes() {
    val chessService by inject<ChessService>()

    route("/guilds/{guildId}/chess") {
        get("/puzzle") {
            val puzzle = chessService.getDailyPuzzle()
            call.respond(puzzle ?: mapOf("error" to "No puzzle available"))
        }

        get("/opening") {
            val query = call.request.queryParameters["query"] ?: "Italian Game"
            val opening = chessService.searchOpening(query)
            call.respond(opening ?: mapOf("error" to "Opening not found"))
        }
    }
}

fun Route.websocketRoutes() {
    webSocket("/ws") {
        send("Connected to ChordKnight WebSocket")

        for (frame in incoming) {
            if (frame is Frame.Text) {
                val text = frame.readText()
                send("Echo: $text")
            }
        }
    }
}

@Serializable
data class GuildInfo(
    val id: String,
    val name: String,
    val iconUrl: String?
)
