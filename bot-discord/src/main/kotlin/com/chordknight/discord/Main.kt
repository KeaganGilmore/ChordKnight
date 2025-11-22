package com.chordknight.discord

import com.chordknight.chess.chessModule
import com.chordknight.core.config.Config
import com.chordknight.core.db.DatabaseManager
import com.chordknight.core.di.coreModule
import com.chordknight.core.feature.Feature
import com.chordknight.music.musicModule
import kotlinx.coroutines.runBlocking
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.slf4j.LoggerFactory

private val logger = LoggerFactory.getLogger("Main")

fun main() = runBlocking {
    logger.info("Starting ChordKnight Discord Bot...")

    // Initialize Koin DI
    val koinApp = startKoin {
        modules(coreModule, musicModule, chessModule, discordModule)
    }

    val config = koinApp.koin.get<Config>()

    // Initialize database
    DatabaseManager.initialize(config.database)

    // Get all features
    val features = koinApp.koin.getAll<Feature>()
    logger.info("Loaded ${features.size} features: ${features.joinToString { it.name }}")

    // Create and start bot
    val bot = DiscordBot(config, features)

    // Add shutdown hook
    Runtime.getRuntime().addShutdownHook(Thread {
        runBlocking {
            logger.info("Shutdown hook triggered")
            bot.shutdown()
            stopKoin()
        }
    })

    // Start bot
    bot.start()

    logger.info("ChordKnight Discord Bot is now running. Press Ctrl+C to stop.")
}

val discordModule = module {
    // Features will be registered here by other modules
}
