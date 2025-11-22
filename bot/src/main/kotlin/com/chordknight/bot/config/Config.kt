package com.chordknight.bot.config

import io.github.cdimascio.dotenv.Dotenv
import io.github.cdimascio.dotenv.dotenv

/**
 * Application configuration loaded from environment variables
 */
data class BotConfig(
    val discordToken: String,
    val prefix: String = "!",
    val apiBaseUrl: String = "http://localhost:8080",
    val enableMusic: Boolean = true,
    val enableChess: Boolean = true
)

/**
 * Load configuration from environment
 */
fun loadBotConfig(): BotConfig {
    val dotenv: Dotenv = try {
        dotenv {
            ignoreIfMissing = true
        }
    } catch (e: Exception) {
        // If .env file doesn't exist, use system env vars
        dotenv {
            ignoreIfMissing = true
            systemProperties = true
        }
    }
    
    return BotConfig(
        discordToken = dotenv["DISCORD_TOKEN"] ?: System.getenv("DISCORD_TOKEN") 
            ?: throw IllegalStateException("DISCORD_TOKEN must be set"),
        prefix = dotenv["BOT_PREFIX"] ?: System.getenv("BOT_PREFIX") ?: "!",
        apiBaseUrl = dotenv["API_BASE_URL"] ?: System.getenv("API_BASE_URL") ?: "http://localhost:8080",
        enableMusic = dotenv["ENABLE_MUSIC"]?.toBoolean() ?: true,
        enableChess = dotenv["ENABLE_CHESS"]?.toBoolean() ?: true
    )
}
