# ChordKnight

ChordKnight is a modular, event-driven Discord bot for chess servers with music playback capabilities. Built with Kotlin, JDA, and Ktor, it provides a dynamic feature system that makes it easy to add new functionality without modifying core code.

## Features

- 🎵 **Music Playback**: Powered by Lavalink with per-guild queue management
- ♟️ **Chess Utilities**: Daily puzzles, opening lookup, and chess server tools
- 🔌 **Modular Architecture**: Easy to add/remove features dynamically
- 🌐 **REST API**: Full-featured Ktor API with WebSocket support
- 💻 **Web Dashboard**: SvelteKit UI for managing bot settings and viewing music queues
- ⚙️ **Guild Configuration**: Per-guild settings for features, prefixes, and preferences
- 🐳 **Docker Support**: Complete Docker Compose setup for easy deployment

## Architecture

ChordKnight is organized as a Gradle multi-module monorepo:

```
chordknight/
├── bot-core/          # Common domain logic, config, database, DI
├── bot-discord/       # JDA Discord bot implementation
├── bot-music/         # Lavalink music service abstraction
├── bot-chess/         # Chess features and utilities
├── api-server/        # Ktor REST/WebSocket API
└── web/               # SvelteKit + Tailwind web dashboard
```

### Key Components

- **Feature System**: Pluggable `Feature` interface for modular functionality
- **Guild Context**: Per-guild configuration and state management
- **Dependency Injection**: Koin-based DI for clean module separation
- **Database**: PostgreSQL with Exposed ORM for persistent settings

## Prerequisites

- **Java 21** or higher
- **Gradle 8.5** or higher
- **PostgreSQL 16** (or use Docker Compose)
- **Node.js 18+** and **npm** (for web dashboard)
- **Discord Bot Token** (from [Discord Developer Portal](https://discord.com/developers/applications))

## Quick Start

### 1. Clone and Configure

```bash
git clone https://github.com/KeaganGilmore/ChordKnight.git
cd ChordKnight

# Copy sample environment file
cp sample.env .env

# Edit .env with your Discord bot token and other settings
nano .env
```

### 2. Run with Docker Compose (Recommended)

```bash
# Start all services (PostgreSQL, Lavalink, API, Bot)
docker-compose up -d

# View logs
docker-compose logs -f bot-discord
```

### 3. Run Locally (Development)

```bash
# Start PostgreSQL
docker-compose up -d postgres

# Start Lavalink
docker-compose up -d lavalink

# Build the project
./gradlew build

# Run the Discord bot
./gradlew :bot-discord:run

# In another terminal, run the API server
./gradlew :api-server:run
```

### 4. Set Up Web Dashboard

```bash
cd web
npm install
npm run dev
```

The web dashboard will be available at `http://localhost:5173`.

## Configuration

All configuration is done through environment variables. See `sample.env` for all available options:

| Variable | Description | Default |
|----------|-------------|---------|
| `DISCORD_TOKEN` | Your Discord bot token | **Required** |
| `DISCORD_APPLICATION_ID` | Your Discord application ID | **Required** |
| `DATABASE_URL` | PostgreSQL connection URL | `jdbc:postgresql://localhost:5432/chordknight` |
| `LAVALINK_HOST` | Lavalink server host | `localhost` |
| `LAVALINK_PORT` | Lavalink server port | `2333` |
| `API_PORT` | API server port | `8080` |

## Available Commands

### Discord Bot Commands

- `/ping` - Check bot responsiveness and latency

More commands will be added as features are implemented.

## Adding a New Feature

ChordKnight's modular architecture makes it easy to add new features:

### 1. Create a Feature Module

```bash
mkdir bot-myfeature/src/main/kotlin/com/chordknight/myfeature
```

### 2. Add to `settings.gradle.kts`

```kotlin
include("bot-myfeature")
```

### 3. Create Feature Implementation

```kotlin
// bot-myfeature/src/main/kotlin/com/chordknight/myfeature/MyFeature.kt
package com.chordknight.myfeature

import com.chordknight.core.feature.BaseFeature

class MyFeature : BaseFeature() {
    override val id = "myfeature"
    override val name = "My Feature"
    
    override suspend fun initialize() {
        // Initialize your feature
    }
}
```

### 4. Create Koin Module

```kotlin
// bot-myfeature/src/main/kotlin/com/chordknight/myfeature/MyModule.kt
package com.chordknight.myfeature

import com.chordknight.core.feature.Feature
import org.koin.dsl.module

val myFeatureModule = module {
    single<Feature> { MyFeature() }
}
```

### 5. Register in Main

```kotlin
// bot-discord/src/main/kotlin/com/chordknight/discord/Main.kt
startKoin {
    modules(coreModule, musicModule, chessModule, myFeatureModule)
}
```

That's it! Your feature will now be automatically discovered and initialized.

## Development

### Building

```bash
# Build all modules
./gradlew build

# Build specific module
./gradlew :bot-discord:build

# Create executable JARs
./gradlew :bot-discord:jar
./gradlew :api-server:jar
```

### Testing

```bash
# Run all tests
./gradlew test

# Run specific module tests
./gradlew :bot-core:test
```

### Code Style

This project uses Kotlin coding conventions. Format your code with:

```bash
./gradlew ktlintFormat
```

## Project Structure

```
ChordKnight/
├── bot-core/                   # Core domain logic
│   └── src/main/kotlin/com/chordknight/core/
│       ├── config/            # Configuration management
│       ├── feature/           # Feature interface
│       ├── db/                # Database models & repositories
│       └── di/                # Dependency injection modules
├── bot-discord/               # Discord bot
│   └── src/main/kotlin/com/chordknight/discord/
│       ├── DiscordBot.kt      # Main bot class
│       ├── CommandListener.kt # Command routing
│       ├── GuildContext.kt    # Guild state management
│       └── Main.kt            # Entry point
├── bot-music/                 # Music feature
│   └── src/main/kotlin/com/chordknight/music/
│       ├── MusicService.kt    # Service interface
│       └── MusicFeature.kt    # Feature implementation
├── bot-chess/                 # Chess feature
│   └── src/main/kotlin/com/chordknight/chess/
│       ├── ChessService.kt    # Service interface
│       └── ChessFeature.kt    # Feature implementation
├── api-server/                # REST API
│   └── src/main/kotlin/com/chordknight/api/
│       ├── Main.kt            # Ktor server setup
│       └── Routes.kt          # API routes
├── web/                       # Web dashboard (SvelteKit)
├── docker-compose.yml         # Docker services
├── lavalink.yml              # Lavalink configuration
└── sample.env                # Environment template
```

## API Endpoints

The API server exposes the following endpoints:

### Health Check
- `GET /health` - Server health status

### Guilds
- `GET /api/guilds` - List all guilds
- `GET /api/guilds/{id}` - Get guild details

### Music
- `GET /api/guilds/{id}/music/player` - Get player state
- `GET /api/guilds/{id}/music/queue` - Get current queue

### Chess
- `GET /api/guilds/{id}/chess/puzzle` - Get daily puzzle
- `GET /api/guilds/{id}/chess/opening?query=Italian` - Search opening

### WebSocket
- `WS /api/ws` - WebSocket for live updates

## Technology Stack

- **Language**: Kotlin 1.9.21
- **Build Tool**: Gradle 8.5 (Kotlin DSL)
- **Discord Library**: JDA 5.0.0-beta.18
- **Web Framework**: Ktor 2.3.7
- **Database**: PostgreSQL 16 + Exposed ORM
- **Music**: Lavalink 4.0.4
- **DI**: Koin 3.5.3
- **Frontend**: SvelteKit + TypeScript + Tailwind CSS
- **Deployment**: Docker + Docker Compose

## Contributing

Contributions are welcome! Please follow these guidelines:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Support

For questions or issues:
- Open an issue on GitHub
- Join our Discord server (coming soon)

## Roadmap

- [ ] Full Lavalink integration with queue management
- [ ] Chess.com and Lichess API integration
- [ ] Web dashboard UI implementation
- [ ] User authentication for web dashboard
- [ ] Advanced music controls (seek, volume, filters)
- [ ] Chess tournament management
- [ ] Admin commands and moderation tools
- [ ] Slash command auto-registration per guild
- [ ] Metrics and monitoring dashboard

---

Built with ❤️ by the ChordKnight team
