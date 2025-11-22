package com.chordknight.core.config

data class Config(
    val discord: DiscordConfig,
    val database: DatabaseConfig,
    val lavalink: LavalinkConfig,
    val api: ApiConfig
) {
    companion object {
        fun fromEnvironment(): Config {
            return Config(
                discord = DiscordConfig(
                    token = getEnv("DISCORD_TOKEN"),
                    applicationId = getEnv("DISCORD_APPLICATION_ID")
                ),
                database = DatabaseConfig(
                    url = getEnv("DATABASE_URL", "jdbc:postgresql://localhost:5432/chordknight"),
                    driver = getEnv("DATABASE_DRIVER", "org.postgresql.Driver"),
                    user = getEnv("DATABASE_USER", "chordknight"),
                    password = getEnv("DATABASE_PASSWORD", "chordknight")
                ),
                lavalink = LavalinkConfig(
                    host = getEnv("LAVALINK_HOST", "localhost"),
                    port = getEnv("LAVALINK_PORT", "2333").toInt(),
                    password = getEnv("LAVALINK_PASSWORD", "youshallnotpass")
                ),
                api = ApiConfig(
                    host = getEnv("API_HOST", "0.0.0.0"),
                    port = getEnv("API_PORT", "8080").toInt(),
                    authSecret = getEnv("API_AUTH_SECRET", "change-me-in-production")
                )
            )
        }

        private fun getEnv(key: String, default: String? = null): String {
            return System.getenv(key) ?: default ?: throw IllegalStateException("Environment variable $key is required but not set")
        }
    }
}

data class DiscordConfig(
    val token: String,
    val applicationId: String
)

data class DatabaseConfig(
    val url: String,
    val driver: String,
    val user: String,
    val password: String
)

data class LavalinkConfig(
    val host: String,
    val port: Int,
    val password: String
)

data class ApiConfig(
    val host: String,
    val port: Int,
    val authSecret: String
)
