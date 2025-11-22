package com.chordknight.chess

import kotlinx.serialization.Serializable

/**
 * Chess piece types
 */
enum class PieceType {
    PAWN, KNIGHT, BISHOP, ROOK, QUEEN, KING
}

/**
 * Chess piece colors
 */
enum class PieceColor {
    WHITE, BLACK
}

/**
 * Represents a chess piece
 */
@Serializable
data class ChessPiece(
    val type: PieceType,
    val color: PieceColor
)

/**
 * Represents a position on the chess board
 */
@Serializable
data class Position(
    val file: Int, // 0-7 (a-h)
    val rank: Int  // 0-7 (1-8)
) {
    fun toAlgebraic(): String = "${'a' + file}${rank + 1}"
    
    companion object {
        fun fromAlgebraic(notation: String): Position {
            require(notation.length == 2) { "Invalid algebraic notation" }
            val file = notation[0] - 'a'
            val rank = notation[1].digitToInt() - 1
            require(file in 0..7 && rank in 0..7) { "Position out of bounds" }
            return Position(file, rank)
        }
    }
}

/**
 * Represents a chess move
 */
@Serializable
data class ChessMove(
    val from: Position,
    val to: Position,
    val promotion: PieceType? = null
)

/**
 * Main chess engine for move validation and game state
 */
class ChessEngine {
    private val board = mutableMapOf<Position, ChessPiece>()
    var currentTurn = PieceColor.WHITE
        private set
    
    init {
        resetBoard()
    }
    
    /**
     * Reset the board to starting position
     */
    fun resetBoard() {
        board.clear()
        // Setup initial position
        setupInitialPosition()
        currentTurn = PieceColor.WHITE
    }
    
    private fun setupInitialPosition() {
        // White pieces
        for (file in 0..7) {
            board[Position(file, 1)] = ChessPiece(PieceType.PAWN, PieceColor.WHITE)
        }
        board[Position(0, 0)] = ChessPiece(PieceType.ROOK, PieceColor.WHITE)
        board[Position(1, 0)] = ChessPiece(PieceType.KNIGHT, PieceColor.WHITE)
        board[Position(2, 0)] = ChessPiece(PieceType.BISHOP, PieceColor.WHITE)
        board[Position(3, 0)] = ChessPiece(PieceType.QUEEN, PieceColor.WHITE)
        board[Position(4, 0)] = ChessPiece(PieceType.KING, PieceColor.WHITE)
        board[Position(5, 0)] = ChessPiece(PieceType.BISHOP, PieceColor.WHITE)
        board[Position(6, 0)] = ChessPiece(PieceType.KNIGHT, PieceColor.WHITE)
        board[Position(7, 0)] = ChessPiece(PieceType.ROOK, PieceColor.WHITE)
        
        // Black pieces
        for (file in 0..7) {
            board[Position(file, 6)] = ChessPiece(PieceType.PAWN, PieceColor.BLACK)
        }
        board[Position(0, 7)] = ChessPiece(PieceType.ROOK, PieceColor.BLACK)
        board[Position(1, 7)] = ChessPiece(PieceType.KNIGHT, PieceColor.BLACK)
        board[Position(2, 7)] = ChessPiece(PieceType.BISHOP, PieceColor.BLACK)
        board[Position(3, 7)] = ChessPiece(PieceType.QUEEN, PieceColor.BLACK)
        board[Position(4, 7)] = ChessPiece(PieceType.KING, PieceColor.BLACK)
        board[Position(5, 7)] = ChessPiece(PieceType.BISHOP, PieceColor.BLACK)
        board[Position(6, 7)] = ChessPiece(PieceType.KNIGHT, PieceColor.BLACK)
        board[Position(7, 7)] = ChessPiece(PieceType.ROOK, PieceColor.BLACK)
    }
    
    /**
     * Get piece at position
     */
    fun getPiece(position: Position): ChessPiece? = board[position]
    
    /**
     * Validate and make a move
     */
    fun makeMove(move: ChessMove): Boolean {
        val piece = board[move.from] ?: return false
        
        // Check if it's the correct player's turn
        if (piece.color != currentTurn) return false
        
        // Basic move validation (simplified)
        if (!isValidMove(move, piece)) return false
        
        // Execute move
        board.remove(move.from)
        val finalPiece = if (move.promotion != null && piece.type == PieceType.PAWN) {
            ChessPiece(move.promotion, piece.color)
        } else {
            piece
        }
        board[move.to] = finalPiece
        
        // Switch turn
        currentTurn = if (currentTurn == PieceColor.WHITE) PieceColor.BLACK else PieceColor.WHITE
        
        return true
    }
    
    /**
     * Basic move validation (stub - would need full chess rules)
     */
    private fun isValidMove(move: ChessMove, piece: ChessPiece): Boolean {
        // Simplified validation - just check destination is in bounds
        return move.to.file in 0..7 && move.to.rank in 0..7
    }
    
    /**
     * Get all pieces on the board
     */
    fun getAllPieces(): Map<Position, ChessPiece> = board.toMap()
    
    /**
     * Get FEN string representation
     */
    fun toFen(): String {
        // Simplified FEN generation
        return "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"
    }
}
