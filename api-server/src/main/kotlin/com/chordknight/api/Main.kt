package com.chordknight.api

import com.chordknight.chess.chessModule
import com.chordknight.core.config.Config
import com.chordknight.core.db.DatabaseManager
import com.chordknight.core.di.coreModule
import com.chordknight.music.musicModule
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.ktor.ext.inject
import org.slf4j.LoggerFactory
import java.time.Duration

private val logger = LoggerFactory.getLogger("ApiServer")

fun main() {
    logger.info("Starting ChordKnight API Server...")

    // Initialize Koin DI
    val koinApp = startKoin {
        modules(coreModule, musicModule, chessModule)
    }

    val config = koinApp.koin.get<Config>()

    // Initialize database
    DatabaseManager.initialize(config.database)

    // Start Ktor server
    embeddedServer(Netty, port = config.api.port, host = config.api.host) {
        configurePlugins()
        configureRouting()
    }.start(wait = true)
}

fun Application.configurePlugins() {
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
        })
    }

    install(CORS) {
        anyHost()
        allowHeader("Content-Type")
    }

    install(WebSockets) {
        pingPeriod = Duration.ofSeconds(15)
        timeout = Duration.ofSeconds(15)
        maxFrameSize = Long.MAX_VALUE
        masking = false
    }
}

fun Application.configureRouting() {
    routing {
        get("/health") {
            call.respond(mapOf("status" to "healthy"))
        }

        route("/api") {
            guildsRoutes()
            musicRoutes()
            chessRoutes()
            websocketRoutes()
        }
    }
}
