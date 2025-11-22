package com.chordknight.bot.state

import com.chordknight.chess.ChessEngine
import com.chordknight.music.GuildMusicPlayer
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.concurrent.ConcurrentHashMap

/**
 * Per-guild state management
 */
data class GuildState(
    val guildId: String,
    val chessGame: ChessEngine? = null,
    var activeFeatures: Set<String> = emptySet(),
    var customSettings: MutableMap<String, String> = mutableMapOf()
)

/**
 * Thread-safe guild state manager
 */
class GuildStateManager {
    private val states = ConcurrentHashMap<String, GuildState>()
    private val mutexes = ConcurrentHashMap<String, Mutex>()
    
    /**
     * Get or create guild state
     */
    fun getState(guildId: String): GuildState {
        return states.getOrPut(guildId) {
            GuildState(guildId)
        }
    }
    
    /**
     * Update guild state with mutex protection
     */
    suspend fun updateState(guildId: String, update: (GuildState) -> GuildState) {
        val mutex = mutexes.getOrPut(guildId) { Mutex() }
        mutex.withLock {
            val currentState = getState(guildId)
            val newState = update(currentState)
            states[guildId] = newState
        }
    }
    
    /**
     * Enable a feature for a guild
     */
    suspend fun enableFeature(guildId: String, featureName: String) {
        updateState(guildId) { state ->
            state.copy(activeFeatures = state.activeFeatures + featureName)
        }
    }
    
    /**
     * Disable a feature for a guild
     */
    suspend fun disableFeature(guildId: String, featureName: String) {
        updateState(guildId) { state ->
            state.copy(activeFeatures = state.activeFeatures - featureName)
        }
    }
    
    /**
     * Check if a feature is enabled
     */
    fun isFeatureEnabled(guildId: String, featureName: String): Boolean {
        return getState(guildId).activeFeatures.contains(featureName)
    }
    
    /**
     * Start a chess game for a guild
     */
    suspend fun startChessGame(guildId: String) {
        updateState(guildId) { state ->
            state.copy(chessGame = ChessEngine())
        }
    }
    
    /**
     * Get chess game for a guild
     */
    fun getChessGame(guildId: String): ChessEngine? {
        return getState(guildId).chessGame
    }
    
    /**
     * Clear guild state
     */
    fun clearState(guildId: String) {
        states.remove(guildId)
        mutexes.remove(guildId)
    }
}
