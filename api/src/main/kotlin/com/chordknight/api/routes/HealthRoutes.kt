package com.chordknight.api.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable
data class HealthResponse(
    val status: String,
    val version: String,
    val timestamp: Long
)

/**
 * Health check routes
 */
fun Route.healthRoutes() {
    get("/health") {
        call.respond(
            HttpStatusCode.OK,
            HealthResponse(
                status = "healthy",
                version = "1.0.0",
                timestamp = System.currentTimeMillis()
            )
        )
    }
    
    get("/") {
        call.respondText("ChordKnight API v1.0.0", ContentType.Text.Plain)
    }
}
