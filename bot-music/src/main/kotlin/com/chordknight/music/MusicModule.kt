package com.chordknight.music

import com.chordknight.core.feature.Feature
import org.koin.dsl.module

val musicModule = module {
    single<MusicService> { StubMusicService() }
    single<Feature> { MusicFeature(get()) }
}
