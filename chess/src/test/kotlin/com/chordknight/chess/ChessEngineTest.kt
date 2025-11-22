package com.chordknight.chess

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class ChessEngineTest {
    
    @Test
    fun `test initial board setup`() {
        val engine = ChessEngine()
        
        // Check white pieces
        val whitePawn = engine.getPiece(Position(0, 1))
        assertNotNull(whitePawn)
        assertEquals(PieceType.PAWN, whitePawn.type)
        assertEquals(PieceColor.WHITE, whitePawn.color)
        
        val whiteRook = engine.getPiece(Position(0, 0))
        assertNotNull(whiteRook)
        assertEquals(PieceType.ROOK, whiteRook.type)
        assertEquals(PieceColor.WHITE, whiteRook.color)
        
        // Check black pieces
        val blackPawn = engine.getPiece(Position(0, 6))
        assertNotNull(blackPawn)
        assertEquals(PieceType.PAWN, blackPawn.type)
        assertEquals(PieceColor.BLACK, blackPawn.color)
    }
    
    @Test
    fun `test initial turn is white`() {
        val engine = ChessEngine()
        assertEquals(PieceColor.WHITE, engine.currentTurn)
    }
    
    @Test
    fun `test algebraic notation conversion`() {
        val pos = Position.fromAlgebraic("e4")
        assertEquals(4, pos.file)
        assertEquals(3, pos.rank)
        assertEquals("e4", pos.toAlgebraic())
    }
    
    @Test
    fun `test make move changes turn`() {
        val engine = ChessEngine()
        val move = ChessMove(Position(4, 1), Position(4, 3))
        
        assertTrue(engine.makeMove(move))
        assertEquals(PieceColor.BLACK, engine.currentTurn)
    }
    
    @Test
    fun `test reset board`() {
        val engine = ChessEngine()
        engine.resetBoard()
        
        assertEquals(PieceColor.WHITE, engine.currentTurn)
        assertNotNull(engine.getPiece(Position(0, 0)))
    }
}
