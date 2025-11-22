package com.chordknight.chess

import com.chordknight.core.feature.BaseFeature
import org.slf4j.LoggerFactory

/**
 * Chess feature implementation
 */
class ChessFeature(
    private val chessService: ChessService
) : BaseFeature() {
    private val logger = LoggerFactory.getLogger(ChessFeature::class.java)

    override val id: String = "chess"
    override val name: String = "Chess"

    override suspend fun initialize() {
        logger.info("Initializing Chess feature")
    }

    override suspend fun shutdown() {
        logger.info("Shutting down Chess feature")
    }
}
