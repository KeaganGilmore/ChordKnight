package com.chordknight.bot.features.impl

import com.chordknight.bot.features.BaseFeature
import com.chordknight.music.LoadResult
import com.chordknight.music.MusicPlayer
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * Music playback feature
 */
class MusicFeature : BaseFeature("music", "Play music from various sources"), KoinComponent {
    private val musicPlayer: MusicPlayer by inject()
    
    override suspend fun handleCommand(
        event: MessageReceivedEvent,
        command: String,
        args: List<String>
    ): Boolean {
        if (!event.isFromGuild) return false
        val guildId = event.guild.id
        
        return when (command) {
            "play" -> {
                if (args.isEmpty()) {
                    event.channel.sendMessage("❌ Usage: !play <url or search query>").queue()
                    return true
                }
                
                val query = args.joinToString(" ")
                event.channel.sendMessage("🔍 Searching for: $query").queue()
                
                when (val result = musicPlayer.loadAndPlay(guildId, query)) {
                    is LoadResult.TrackLoaded -> {
                        event.channel.sendMessage("🎵 Now playing: ${result.track.title}").queue()
                    }
                    is LoadResult.PlaylistLoaded -> {
                        event.channel.sendMessage("🎵 Loaded playlist with ${result.tracks.size} tracks").queue()
                    }
                    is LoadResult.NoMatches -> {
                        event.channel.sendMessage("❌ No results found for: $query").queue()
                    }
                    is LoadResult.LoadFailed -> {
                        event.channel.sendMessage("❌ Failed to load track: ${result.error}").queue()
                    }
                }
                true
            }
            "skip" -> {
                val player = musicPlayer.getGuildPlayer(guildId)
                if (player.skip()) {
                    event.channel.sendMessage("⏭️ Skipped to next track").queue()
                } else {
                    event.channel.sendMessage("❌ No more tracks in queue").queue()
                }
                true
            }
            "pause" -> {
                val player = musicPlayer.getGuildPlayer(guildId)
                player.setPaused(true)
                event.channel.sendMessage("⏸️ Paused").queue()
                true
            }
            "resume" -> {
                val player = musicPlayer.getGuildPlayer(guildId)
                player.setPaused(false)
                event.channel.sendMessage("▶️ Resumed").queue()
                true
            }
            "queue" -> {
                val player = musicPlayer.getGuildPlayer(guildId)
                val currentTrack = player.getCurrentTrack()
                val queue = player.getQueue()
                
                val message = buildString {
                    appendLine("🎵 **Music Queue**")
                    if (currentTrack != null) {
                        appendLine("**Now Playing:** ${currentTrack.title}")
                    }
                    if (queue.isNotEmpty()) {
                        appendLine("\n**Up Next:**")
                        queue.take(10).forEachIndexed { index, track ->
                            appendLine("${index + 1}. ${track.title}")
                        }
                        if (queue.size > 10) {
                            appendLine("... and ${queue.size - 10} more")
                        }
                    } else {
                        appendLine("\nQueue is empty")
                    }
                }
                event.channel.sendMessage(message).queue()
                true
            }
            "stop" -> {
                val player = musicPlayer.getGuildPlayer(guildId)
                player.clearQueue()
                event.channel.sendMessage("⏹️ Stopped and cleared queue").queue()
                true
            }
            else -> false
        }
    }
}
