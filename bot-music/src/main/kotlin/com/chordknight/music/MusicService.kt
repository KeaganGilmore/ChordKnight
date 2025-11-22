package com.chordknight.music

import kotlinx.coroutines.flow.StateFlow

/**
 * Service interface for music playback functionality
 */
interface MusicService {
    /**
     * Connect to a voice channel
     */
    suspend fun joinVoiceChannel(guildId: String, channelId: String)

    /**
     * Disconnect from the current voice channel
     */
    suspend fun leaveVoiceChannel(guildId: String)

    /**
     * Play a track from URL or search query
     */
    suspend fun play(guildId: String, query: String): PlayResult

    /**
     * Pause playback
     */
    suspend fun pause(guildId: String)

    /**
     * Resume playback
     */
    suspend fun resume(guildId: String)

    /**
     * Skip to the next track
     */
    suspend fun skip(guildId: String)

    /**
     * Set volume (0-100)
     */
    suspend fun setVolume(guildId: String, volume: Int)

    /**
     * Seek to position in current track
     */
    suspend fun seek(guildId: String, positionMs: Long)

    /**
     * Get current player state for a guild
     */
    suspend fun getPlayerState(guildId: String): PlayerState?

    /**
     * Get the queue for a guild
     */
    suspend fun getQueue(guildId: String): List<Track>
}

/**
 * Result of a play command
 */
sealed class PlayResult {
    data class Success(val track: Track) : PlayResult()
    data class Playlist(val tracks: List<Track>) : PlayResult()
    data class Error(val message: String) : PlayResult()
}

/**
 * Represents a music track
 */
data class Track(
    val title: String,
    val author: String,
    val url: String,
    val durationMs: Long,
    val artworkUrl: String? = null
)

/**
 * Current state of a guild's music player
 */
data class PlayerState(
    val guildId: String,
    val currentTrack: Track?,
    val queue: List<Track>,
    val isPlaying: Boolean,
    val isPaused: Boolean,
    val volume: Int,
    val positionMs: Long
)

/**
 * Stub implementation of MusicService
 */
class StubMusicService : MusicService {
    private val playerStates = mutableMapOf<String, PlayerState>()

    override suspend fun joinVoiceChannel(guildId: String, channelId: String) {
        // Stub: would connect to Lavalink
    }

    override suspend fun leaveVoiceChannel(guildId: String) {
        playerStates.remove(guildId)
    }

    override suspend fun play(guildId: String, query: String): PlayResult {
        // Stub: would search or load track via Lavalink
        val track = Track(
            title = "Sample Track",
            author = "Sample Artist",
            url = query,
            durationMs = 180000
        )
        return PlayResult.Success(track)
    }

    override suspend fun pause(guildId: String) {
        playerStates[guildId]?.let {
            playerStates[guildId] = it.copy(isPaused = true, isPlaying = false)
        }
    }

    override suspend fun resume(guildId: String) {
        playerStates[guildId]?.let {
            playerStates[guildId] = it.copy(isPaused = false, isPlaying = true)
        }
    }

    override suspend fun skip(guildId: String) {
        // Stub: would skip to next track in queue
    }

    override suspend fun setVolume(guildId: String, volume: Int) {
        playerStates[guildId]?.let {
            playerStates[guildId] = it.copy(volume = volume.coerceIn(0, 100))
        }
    }

    override suspend fun seek(guildId: String, positionMs: Long) {
        playerStates[guildId]?.let {
            playerStates[guildId] = it.copy(positionMs = positionMs)
        }
    }

    override suspend fun getPlayerState(guildId: String): PlayerState? {
        return playerStates[guildId]
    }

    override suspend fun getQueue(guildId: String): List<Track> {
        return playerStates[guildId]?.queue ?: emptyList()
    }
}
