package com.chordknight.core.feature

import kotlinx.coroutines.CoroutineScope

/**
 * Represents a pluggable feature/module in the ChordKnight bot.
 * Features can provide Discord commands, API routes, and background jobs.
 */
interface Feature {
    /**
     * Unique identifier for this feature (e.g., "music", "chess", "admin")
     */
    val id: String

    /**
     * Human-readable name of the feature
     */
    val name: String

    /**
     * Initialize the feature. Called during bot startup.
     */
    suspend fun initialize()

    /**
     * Shutdown the feature. Called during bot shutdown.
     */
    suspend fun shutdown()

    /**
     * Check if this feature is enabled for a specific guild.
     * @param guildId The Discord guild ID
     * @return true if the feature is enabled for this guild
     */
    suspend fun isEnabledForGuild(guildId: String): Boolean

    /**
     * Start any background jobs for this feature.
     * @param scope Coroutine scope for the background jobs
     */
    fun startBackgroundJobs(scope: CoroutineScope) {
        // Default: no background jobs
    }
}

/**
 * Base implementation of Feature with common functionality
 */
abstract class BaseFeature : Feature {
    override suspend fun initialize() {
        // Default: no initialization needed
    }

    override suspend fun shutdown() {
        // Default: no cleanup needed
    }

    override suspend fun isEnabledForGuild(guildId: String): Boolean {
        // Default: enabled for all guilds
        return true
    }
}
