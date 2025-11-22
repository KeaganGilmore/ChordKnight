# ChordKnight

ChordKnight blends chess utilities, server tools, and a modern music system behind a smooth, zero-command UI.

## 🏗️ Architecture

This is a **Kotlin monorepo** built with Gradle, consisting of 5 modules:

- **bot**: Discord bot using JDA with dynamic feature loading
- **api**: REST API built with Ktor
- **music**: Music player with Lavalink client integration
- **chess**: Chess game engine and utilities
- **web**: SvelteKit-based web UI

See [ARCHITECTURE.md](ARCHITECTURE.md) for detailed architecture documentation.

## 🚀 Quick Start

### Prerequisites

- JDK 17 or higher
- Node.js 18+ (for web module)
- Discord bot token (optional, for bot module)

### Configuration

1. Copy `.env.example` to `.env`:
```bash
cp .env.example .env
```

2. Edit `.env` and add your Discord bot token and other configuration

### Running Modules

#### Bot Module
```bash
./gradlew :bot:run
```

#### API Module
```bash
./gradlew :api:run
```

The API will be available at http://localhost:8080

#### Web Module
```bash
cd web
npm install
npm run dev
```

The web UI will be available at http://localhost:5173

## 🛠️ Development

### Build All Modules
```bash
./gradlew build
```

### Run Tests
```bash
./gradlew test
```

### Clean Build
```bash
./gradlew clean build
```

## 📦 Key Features

### Dynamic Feature Loading
The bot supports dynamic feature loading with per-guild configuration:
- Features can be enabled/disabled via configuration
- Each guild maintains its own state
- Coroutines-based async operations

### Dependency Injection
All modules use Koin for dependency injection, providing:
- Loose coupling between components
- Easy testing and mocking
- Configuration management

### Environment-Based Config
Configuration is loaded from environment variables or `.env` files:
- Type-safe configuration classes
- Default values for development
- Production-ready setup

### Per-Guild State
The bot maintains separate state for each Discord guild:
- Thread-safe state management
- Chess games per guild
- Music queues per guild
- Custom settings per guild

## 🎮 Bot Commands

- `!help` - Show available commands
- `!ping` - Test bot responsiveness
- `!chess start` - Start a chess game
- `!chess move <from> <to>` - Make a chess move
- `!chess board` - Show current board
- `!play <url|query>` - Play music
- `!pause` / `!resume` - Control playback
- `!skip` - Skip current track
- `!queue` - Show music queue
- `!stop` - Stop and clear queue

## 📚 Tech Stack

### Backend
- **Kotlin** 1.9.22
- **JDA** 5.0.0-beta.20 (Discord bot)
- **Ktor** 2.3.7 (REST API)
- **Lavaplayer** 1.3.78 (Music)
- **Koin** 3.5.3 (Dependency Injection)
- **Gradle** 8.5 (Build System)

### Frontend
- **SvelteKit** 2.0
- **TypeScript**
- **Vite**

## 📄 License

MIT
