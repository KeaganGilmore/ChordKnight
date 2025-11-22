package com.chordknight.bot.features

import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

/**
 * Base interface for bot features
 */
interface Feature {
    val name: String
    val description: String
    val enabled: Boolean
    
    /**
     * Initialize the feature
     */
    suspend fun initialize()
    
    /**
     * Shutdown the feature
     */
    suspend fun shutdown()
    
    /**
     * Handle a command
     */
    suspend fun handleCommand(event: MessageReceivedEvent, command: String, args: List<String>): Boolean
}

/**
 * Abstract base class for features
 */
abstract class BaseFeature(
    override val name: String,
    override val description: String
) : Feature {
    override var enabled: Boolean = false
        protected set
    
    override suspend fun initialize() {
        enabled = true
    }
    
    override suspend fun shutdown() {
        enabled = false
    }
    
    override suspend fun handleCommand(
        event: MessageReceivedEvent,
        command: String,
        args: List<String>
    ): Boolean = false
}

/**
 * JDA event listener adapter for features
 */
abstract class FeatureListener : ListenerAdapter() {
    abstract val featureName: String
}
