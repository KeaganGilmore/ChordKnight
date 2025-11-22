package com.chordknight.api.routes

import com.chordknight.chess.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable
data class NewGameRequest(
    val gameId: String
)

@Serializable
data class MakeMoveRequest(
    val gameId: String,
    val from: String,
    val to: String
)

@Serializable
data class GameStateResponse(
    val gameId: String,
    val currentTurn: String,
    val fen: String
)

// In-memory game storage (in production, use a database)
private val games = mutableMapOf<String, ChessEngine>()

/**
 * Chess API routes
 */
fun Route.chessRoutes() {
    route("/chess") {
        post("/game") {
            val request = call.receive<NewGameRequest>()
            val engine = ChessEngine()
            games[request.gameId] = engine
            
            call.respond(
                HttpStatusCode.Created,
                GameStateResponse(
                    gameId = request.gameId,
                    currentTurn = engine.currentTurn.name,
                    fen = engine.toFen()
                )
            )
        }
        
        get("/game/{gameId}") {
            val gameId = call.parameters["gameId"] ?: return@get call.respond(
                HttpStatusCode.BadRequest,
                mapOf("error" to "Missing gameId")
            )
            
            val engine = games[gameId] ?: return@get call.respond(
                HttpStatusCode.NotFound,
                mapOf("error" to "Game not found")
            )
            
            call.respond(
                GameStateResponse(
                    gameId = gameId,
                    currentTurn = engine.currentTurn.name,
                    fen = engine.toFen()
                )
            )
        }
        
        post("/game/{gameId}/move") {
            val gameId = call.parameters["gameId"] ?: return@post call.respond(
                HttpStatusCode.BadRequest,
                mapOf("error" to "Missing gameId")
            )
            
            val engine = games[gameId] ?: return@post call.respond(
                HttpStatusCode.NotFound,
                mapOf("error" to "Game not found")
            )
            
            val request = call.receive<MakeMoveRequest>()
            
            try {
                val from = Position.fromAlgebraic(request.from)
                val to = Position.fromAlgebraic(request.to)
                val move = ChessMove(from, to)
                
                if (engine.makeMove(move)) {
                    call.respond(
                        GameStateResponse(
                            gameId = gameId,
                            currentTurn = engine.currentTurn.name,
                            fen = engine.toFen()
                        )
                    )
                } else {
                    call.respond(
                        HttpStatusCode.BadRequest,
                        mapOf("error" to "Invalid move")
                    )
                }
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    mapOf("error" to "Invalid move notation: ${e.message}")
                )
            }
        }
        
        delete("/game/{gameId}") {
            val gameId = call.parameters["gameId"] ?: return@delete call.respond(
                HttpStatusCode.BadRequest,
                mapOf("error" to "Missing gameId")
            )
            
            if (games.remove(gameId) != null) {
                call.respond(HttpStatusCode.OK, mapOf("message" to "Game deleted"))
            } else {
                call.respond(HttpStatusCode.NotFound, mapOf("error" to "Game not found"))
            }
        }
    }
}
