package com.chordknight.api.config

import io.github.cdimascio.dotenv.Dotenv
import io.github.cdimascio.dotenv.dotenv

/**
 * API configuration
 */
data class ApiConfig(
    val host: String = "0.0.0.0",
    val port: Int = 8080,
    val corsAllowedHosts: List<String> = listOf("*"),
    val environment: String = "development"
)

/**
 * Load API configuration from environment
 */
fun loadApiConfig(): ApiConfig {
    val dotenv: Dotenv = try {
        dotenv {
            ignoreIfMissing = true
        }
    } catch (e: Exception) {
        dotenv {
            ignoreIfMissing = true
            systemProperties = true
        }
    }
    
    return ApiConfig(
        host = dotenv["API_HOST"] ?: System.getenv("API_HOST") ?: "0.0.0.0",
        port = dotenv["API_PORT"]?.toIntOrNull() ?: System.getenv("API_PORT")?.toIntOrNull() ?: 8080,
        corsAllowedHosts = dotenv["CORS_ALLOWED_HOSTS"]?.split(",") 
            ?: System.getenv("CORS_ALLOWED_HOSTS")?.split(",") 
            ?: listOf("*"),
        environment = dotenv["ENVIRONMENT"] ?: System.getenv("ENVIRONMENT") ?: "development"
    )
}
