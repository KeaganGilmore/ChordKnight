package com.chordknight.bot.features

import com.chordknight.bot.config.BotConfig
import com.chordknight.bot.features.impl.ChessFeature
import com.chordknight.bot.features.impl.MusicFeature
import com.chordknight.bot.features.impl.PingFeature
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.slf4j.LoggerFactory

/**
 * Dynamic feature loader
 */
class FeatureLoader(
    private val config: BotConfig
) : KoinComponent {
    private val logger = LoggerFactory.getLogger(FeatureLoader::class.java)
    private val features = mutableMapOf<String, Feature>()
    
    /**
     * Load all enabled features
     */
    suspend fun loadFeatures() = coroutineScope {
        logger.info("Loading features...")
        
        // Core features
        registerFeature(PingFeature())
        
        // Optional features based on config
        if (config.enableChess) {
            registerFeature(ChessFeature())
        }
        
        if (config.enableMusic) {
            registerFeature(MusicFeature())
        }
        
        // Initialize all features
        features.values.forEach { feature ->
            launch {
                try {
                    feature.initialize()
                    logger.info("Initialized feature: ${feature.name}")
                } catch (e: Exception) {
                    logger.error("Failed to initialize feature: ${feature.name}", e)
                }
            }
        }
    }
    
    /**
     * Register a feature
     */
    private fun registerFeature(feature: Feature) {
        features[feature.name] = feature
        logger.debug("Registered feature: ${feature.name}")
    }
    
    /**
     * Get a feature by name
     */
    fun getFeature(name: String): Feature? = features[name]
    
    /**
     * Get all features
     */
    fun getAllFeatures(): List<Feature> = features.values.toList()
    
    /**
     * Get enabled features
     */
    fun getEnabledFeatures(): List<Feature> = features.values.filter { it.enabled }
    
    /**
     * Shutdown all features
     */
    suspend fun shutdownFeatures() = coroutineScope {
        logger.info("Shutting down features...")
        features.values.forEach { feature ->
            launch {
                try {
                    feature.shutdown()
                    logger.info("Shutdown feature: ${feature.name}")
                } catch (e: Exception) {
                    logger.error("Failed to shutdown feature: ${feature.name}", e)
                }
            }
        }
    }
}
