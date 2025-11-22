package com.chordknight.core.db

import com.chordknight.core.config.DatabaseConfig
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.slf4j.LoggerFactory

/**
 * Manages database connection and initialization
 */
object DatabaseManager {
    private val logger = LoggerFactory.getLogger(DatabaseManager::class.java)

    fun initialize(config: DatabaseConfig) {
        logger.info("Connecting to database: ${config.url}")
        Database.connect(
            url = config.url,
            driver = config.driver,
            user = config.user,
            password = config.password
        )

        transaction {
            logger.info("Creating database tables if they don't exist")
            SchemaUtils.create(GuildSettingsTable)
        }

        logger.info("Database initialized successfully")
    }
}
