package com.chordknight.api.config

import com.chordknight.chess.ChessEngine
import com.chordknight.music.MusicPlayer
import org.koin.dsl.module

/**
 * API dependency injection
 */
object ApiDependencyInjection {
    
    val apiModule = module {
        single { loadApiConfig() }
        single { MusicPlayer() }
        // Chess engines are created per-request/session
    }
    
    fun getAllModules() = listOf(apiModule)
}
