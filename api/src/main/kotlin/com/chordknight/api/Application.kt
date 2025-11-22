package com.chordknight.api

import com.chordknight.api.config.ApiConfig
import com.chordknight.api.config.ApiDependencyInjection
import com.chordknight.api.routes.chessRoutes
import com.chordknight.api.routes.healthRoutes
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json
import org.koin.ktor.ext.inject
import org.koin.ktor.plugin.Koin
import org.slf4j.LoggerFactory

/**
 * Main API application
 */
fun main() {
    val logger = LoggerFactory.getLogger("Application")
    logger.info("Starting ChordKnight API...")
    
    embeddedServer(
        Netty,
        port = System.getenv("API_PORT")?.toIntOrNull() ?: 8080,
        host = "0.0.0.0",
        module = Application::module
    ).start(wait = true)
}

/**
 * Application module configuration
 */
fun Application.module() {
    // Install Koin
    install(Koin) {
        modules(ApiDependencyInjection.getAllModules())
    }
    
    val config: ApiConfig by inject()
    
    // Install plugins
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
        })
    }
    
    install(CORS) {
        config.corsAllowedHosts.forEach { host ->
            if (host == "*") {
                anyHost()
            } else {
                allowHost(host)
            }
        }
        allowHeader("Content-Type")
        allowMethod(io.ktor.http.HttpMethod.Get)
        allowMethod(io.ktor.http.HttpMethod.Post)
        allowMethod(io.ktor.http.HttpMethod.Put)
        allowMethod(io.ktor.http.HttpMethod.Delete)
        allowMethod(io.ktor.http.HttpMethod.Options)
    }
    
    // Configure routing
    routing {
        healthRoutes()
        chessRoutes()
    }
    
    log.info("ChordKnight API started on ${config.host}:${config.port}")
}
