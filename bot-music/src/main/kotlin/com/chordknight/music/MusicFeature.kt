package com.chordknight.music

import com.chordknight.core.feature.BaseFeature
import org.slf4j.LoggerFactory

/**
 * Music feature implementation
 */
class MusicFeature(
    private val musicService: MusicService
) : BaseFeature() {
    private val logger = LoggerFactory.getLogger(MusicFeature::class.java)

    override val id: String = "music"
    override val name: String = "Music"

    override suspend fun initialize() {
        logger.info("Initializing Music feature")
        // Initialize Lavalink connection here
    }

    override suspend fun shutdown() {
        logger.info("Shutting down Music feature")
        // Disconnect from Lavalink
    }
}
