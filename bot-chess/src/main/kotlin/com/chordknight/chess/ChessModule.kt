package com.chordknight.chess

import com.chordknight.core.feature.Feature
import org.koin.dsl.module

val chessModule = module {
    single<ChessService> { StubChessService() }
    single<Feature> { ChessFeature(get()) }
}
