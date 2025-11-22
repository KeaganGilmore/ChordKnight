# ChordKnight Architecture

## Overview

ChordKnight is a Kotlin-based monorepo that combines chess utilities, music playback, and Discord bot functionality with a modern web interface.

## Project Structure

```
ChordKnight/
├── bot/           # Discord bot (JDA)
├── api/           # REST API (Ktor)
├── music/         # Music player (Lavalink client)
├── chess/         # Chess engine utilities
├── web/           # Web UI (SvelteKit)
└── buildSrc/      # Shared build configuration
```

## Modules

### Bot Module (JDA)
- **Purpose**: Discord bot with command handling
- **Key Features**:
  - Dynamic feature loading
  - Per-guild state management
  - Coroutines-based async operations
  - Dependency injection with Koin
- **Main Components**:
  - `BotApplication`: Entry point
  - `FeatureLoader`: Dynamic feature management
  - `GuildStateManager`: Per-guild state tracking
  - Features: `PingFeature`, `ChessFeature`, `MusicFeature`

### API Module (Ktor)
- **Purpose**: REST API for external integrations
- **Key Features**:
  - RESTful endpoints
  - JSON serialization
  - CORS support
  - Environment-based configuration
- **Endpoints**:
  - `/health`: Health check
  - `/chess/*`: Chess game management

### Music Module (Lavalink Client)
- **Purpose**: Audio playback and music streaming
- **Key Features**:
  - Lavaplayer integration
  - Queue management
  - Guild-specific players
  - Async track loading
- **Main Components**:
  - `MusicPlayer`: Main music manager
  - `GuildMusicPlayer`: Per-guild player instance

### Chess Module
- **Purpose**: Chess game logic and utilities
- **Key Features**:
  - Move validation
  - Board state management
  - Algebraic notation support
  - FEN representation
- **Main Components**:
  - `ChessEngine`: Core chess logic
  - `ChessMove`, `Position`: Data models

### Web Module (SvelteKit)
- **Purpose**: Modern web interface
- **Key Features**:
  - Responsive UI
  - API integration
  - Real-time status monitoring
- **Main Components**:
  - `+page.svelte`: Landing page
  - API client integration

## Design Patterns

### Dependency Injection (Koin)
All modules use Koin for dependency injection, providing:
- Loose coupling
- Easy testing
- Configuration management
- Singleton services

### Feature System
The bot uses a dynamic feature loading system:
- Features implement the `Feature` interface
- `FeatureLoader` manages feature lifecycle
- Features can be enabled/disabled per configuration
- Async initialization and shutdown

### State Management
Per-guild state is managed through:
- `GuildStateManager` with concurrent access control
- Mutex-protected updates
- Immutable state objects
- Thread-safe collections

### Configuration
Environment-based configuration using:
- `.env` files (dotenv-kotlin)
- Environment variables
- Type-safe configuration classes
- Default values for development

## Coroutines
All async operations use Kotlin coroutines:
- Suspend functions for I/O operations
- Structured concurrency
- Cancellation support
- Integration with JDA and Ktor

## Build System

### Gradle (Kotlin DSL)
- Multi-module project
- Shared dependency management in `buildSrc`
- Version catalogs in `Dependencies.kt`
- Kotlin compilation with JVM target 17

## Running the Project

### Prerequisites
- JDK 17+
- Node.js 18+ (for web module)
- Discord bot token (for bot module)

### Bot Module
```bash
./gradlew :bot:run
```

### API Module
```bash
./gradlew :api:run
```

### Web Module
```bash
cd web
npm install
npm run dev
```

## Environment Variables

See `.env.example` for all available configuration options.

## Testing

Each module includes its own tests:
```bash
./gradlew test
```

## Future Enhancements

- Database integration for persistent state
- WebSocket support for real-time updates
- Additional chess features (AI opponents, analysis)
- Advanced music features (playlists, favorites)
- User authentication and authorization
- Metrics and monitoring
