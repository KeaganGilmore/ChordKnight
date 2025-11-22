package com.chordknight.core.db

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

/**
 * Database table for guild-specific settings
 */
object GuildSettingsTable : Table("guild_settings") {
    val guildId = varchar("guild_id", 20)
    val prefix = varchar("prefix", 10).default("!")
    val musicEnabled = bool("music_enabled").default(true)
    val chessEnabled = bool("chess_enabled").default(true)
    val defaultVolume = integer("default_volume").default(50)
    val language = varchar("language", 10).default("en")

    override val primaryKey = PrimaryKey(guildId)
}

/**
 * Data class representing guild settings
 */
data class GuildSettings(
    val guildId: String,
    val prefix: String = "!",
    val musicEnabled: Boolean = true,
    val chessEnabled: Boolean = true,
    val defaultVolume: Int = 50,
    val language: String = "en"
)

/**
 * Repository for managing guild settings
 */
interface GuildSettingsRepository {
    suspend fun getSettings(guildId: String): GuildSettings
    suspend fun updateSettings(settings: GuildSettings)
    suspend fun deleteSettings(guildId: String)
}

/**
 * Implementation of GuildSettingsRepository using Exposed
 */
class ExposedGuildSettingsRepository : GuildSettingsRepository {
    override suspend fun getSettings(guildId: String): GuildSettings = transaction {
        GuildSettingsTable.select { GuildSettingsTable.guildId eq guildId }
            .map { rowToSettings(it) }
            .firstOrNull() ?: GuildSettings(guildId)
    }

    override suspend fun updateSettings(settings: GuildSettings): Unit = transaction {
        GuildSettingsTable.upsert {
            it[guildId] = settings.guildId
            it[prefix] = settings.prefix
            it[musicEnabled] = settings.musicEnabled
            it[chessEnabled] = settings.chessEnabled
            it[defaultVolume] = settings.defaultVolume
            it[language] = settings.language
        }
        Unit
    }

    override suspend fun deleteSettings(guildId: String): Unit = transaction {
        GuildSettingsTable.deleteWhere { 
            GuildSettingsTable.guildId eq guildId 
        }
        Unit
    }

    private fun rowToSettings(row: ResultRow): GuildSettings = GuildSettings(
        guildId = row[GuildSettingsTable.guildId],
        prefix = row[GuildSettingsTable.prefix],
        musicEnabled = row[GuildSettingsTable.musicEnabled],
        chessEnabled = row[GuildSettingsTable.chessEnabled],
        defaultVolume = row[GuildSettingsTable.defaultVolume],
        language = row[GuildSettingsTable.language]
    )
}
