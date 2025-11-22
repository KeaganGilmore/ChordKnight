package com.chordknight.core.di

import com.chordknight.core.config.Config
import com.chordknight.core.db.ExposedGuildSettingsRepository
import com.chordknight.core.db.GuildSettingsRepository
import org.koin.dsl.module

/**
 * Koin module for core dependencies
 */
val coreModule = module {
    single { Config.fromEnvironment() }
    single<GuildSettingsRepository> { ExposedGuildSettingsRepository() }
}
