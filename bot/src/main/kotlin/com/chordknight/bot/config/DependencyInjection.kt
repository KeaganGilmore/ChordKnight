package com.chordknight.bot.config

import com.chordknight.bot.features.FeatureLoader
import com.chordknight.bot.state.GuildStateManager
import com.chordknight.music.MusicPlayer
import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * Koin dependency injection modules
 */
object DependencyInjection {
    
    /**
     * Bot module with core dependencies
     */
    val botModule: Module = module {
        // Configuration
        single { loadBotConfig() }
        
        // State management
        single { GuildStateManager() }
        
        // Music player
        single { MusicPlayer() }
        
        // Feature loader
        single { FeatureLoader(get()) }
    }
    
    /**
     * Get all modules
     */
    fun getAllModules(): List<Module> = listOf(botModule)
}
