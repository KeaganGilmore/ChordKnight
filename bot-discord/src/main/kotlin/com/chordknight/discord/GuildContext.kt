package com.chordknight.discord

import com.chordknight.core.db.GuildSettings

/**
 * Encapsulates guild-specific context and configuration
 */
data class GuildContext(
    val guildId: String,
    val settings: GuildSettings
) {
    val prefix: String
        get() = settings.prefix

    val musicEnabled: Boolean
        get() = settings.musicEnabled

    val chessEnabled: Boolean
        get() = settings.chessEnabled

    val defaultVolume: Int
        get() = settings.defaultVolume

    val language: String
        get() = settings.language
}
