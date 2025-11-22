package com.chordknight.chess

/**
 * Service interface for chess functionality
 */
interface ChessService {
    /**
     * Get the daily chess puzzle
     */
    suspend fun getDailyPuzzle(): ChessPuzzle?

    /**
     * Search for chess opening by name or ECO code
     */
    suspend fun searchOpening(query: String): ChessOpening?
}

/**
 * Represents a chess puzzle
 */
data class ChessPuzzle(
    val id: String,
    val fen: String,
    val moves: List<String>,
    val rating: Int,
    val url: String? = null
)

/**
 * Represents a chess opening
 */
data class ChessOpening(
    val name: String,
    val eco: String,
    val moves: String,
    val description: String? = null
)

/**
 * Stub implementation of ChessService
 */
class StubChessService : ChessService {
    override suspend fun getDailyPuzzle(): ChessPuzzle {
        // Stub: would call chess.com or lichess API
        return ChessPuzzle(
            id = "daily-puzzle-001",
            fen = "r1bqkbnr/pppp1ppp/2n5/4p3/4P3/5N2/PPPP1PPP/RNBQKB1R w KQkq - 2 3",
            moves = listOf("Bb5", "a6", "Bxc6", "dxc6"),
            rating = 1500,
            url = "https://lichess.org/training/daily"
        )
    }

    override suspend fun searchOpening(query: String): ChessOpening {
        // Stub: would search opening database
        return ChessOpening(
            name = "Italian Game",
            eco = "C50",
            moves = "1.e4 e5 2.Nf3 Nc6 3.Bc4",
            description = "One of the oldest recorded chess openings"
        )
    }
}
