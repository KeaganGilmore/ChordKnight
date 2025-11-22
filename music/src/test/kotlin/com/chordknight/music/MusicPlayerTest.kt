package com.chordknight.music

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class MusicPlayerTest {
    
    @Test
    fun `test create guild player`() {
        val musicPlayer = MusicPlayer()
        val guildPlayer = musicPlayer.getGuildPlayer("test-guild")
        
        assertNotNull(guildPlayer)
    }
    
    @Test
    fun `test load and play track`() = runBlocking {
        val musicPlayer = MusicPlayer()
        val result = musicPlayer.loadAndPlay("test-guild", "https://example.com/track.mp3")
        
        assertTrue(result is LoadResult.TrackLoaded)
        if (result is LoadResult.TrackLoaded) {
            assertNotNull(result.track.title)
        }
    }
    
    @Test
    fun `test guild player queue`() {
        val guildPlayer = GuildMusicPlayer("test-guild")
        val track = Track("1", "Test Track", "https://example.com/track.mp3")
        
        guildPlayer.queue(track)
        
        val currentTrack = guildPlayer.getCurrentTrack()
        assertNotNull(currentTrack)
        assertEquals("Test Track", currentTrack.title)
    }
    
    @Test
    fun `test guild player skip`() {
        val guildPlayer = GuildMusicPlayer("test-guild")
        val track1 = Track("1", "Track 1", "https://example.com/1.mp3")
        val track2 = Track("2", "Track 2", "https://example.com/2.mp3")
        
        guildPlayer.queue(track1)
        guildPlayer.queue(track2)
        
        assertTrue(guildPlayer.skip())
        assertEquals("Track 2", guildPlayer.getCurrentTrack()?.title)
    }
    
    @Test
    fun `test guild player pause and resume`() {
        val guildPlayer = GuildMusicPlayer("test-guild")
        
        guildPlayer.setPaused(true)
        assertTrue(guildPlayer.isPaused())
        
        guildPlayer.setPaused(false)
        assertTrue(!guildPlayer.isPaused())
    }
}
