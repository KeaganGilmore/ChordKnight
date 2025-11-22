# ChordKnight Quick Start Guide

Get ChordKnight up and running in 5 minutes!

## Prerequisites

- **JDK 17+**: [Download here](https://adoptium.net/)
- **Node.js 18+**: [Download here](https://nodejs.org/)
- **Discord Bot Token** (optional): [Create one here](https://discord.com/developers/applications)

## Quick Setup

### 1. Clone and Build

```bash
# Clone the repository
git clone https://github.com/KeaganGilmore/ChordKnight.git
cd ChordKnight

# Build all modules
./gradlew build
```

### 2. Configure Environment

```bash
# Copy example env file
cp .env.example .env

# Edit .env and add your Discord token (optional)
# nano .env  # or use your favorite editor
```

### 3. Start the API Server

```bash
# Terminal 1 - Start API
./gradlew :api:run
```

The API will be available at http://localhost:8080

Test it:
```bash
curl http://localhost:8080/health
```

### 4. Start the Web UI

```bash
# Terminal 2 - Start Web UI
cd web
npm install
npm run dev
```

Open http://localhost:5173 in your browser

### 5. Start the Discord Bot (Optional)

If you have a Discord token:

```bash
# Terminal 3 - Start Bot
export DISCORD_TOKEN=your_token_here
./gradlew :bot:run
```

## Testing the API

### Create a Chess Game

```bash
curl -X POST http://localhost:8080/chess/game \
  -H "Content-Type: application/json" \
  -d '{"gameId": "test-game"}'
```

### Make a Move

```bash
curl -X POST http://localhost:8080/chess/game/test-game/move \
  -H "Content-Type: application/json" \
  -d '{"gameId": "test-game", "from": "e2", "to": "e4"}'
```

### Get Game State

```bash
curl http://localhost:8080/chess/game/test-game
```

## Using the Discord Bot

Once the bot is running and invited to your server:

### Basic Commands

```
!help          - Show all available commands
!ping          - Test bot responsiveness
```

### Chess Commands

```
!chess start               - Start a chess game
!chess move e2 e4         - Make a move
!chess board              - Show current board
```

### Music Commands

```
!play <url or query>      - Play music
!pause                    - Pause playback
!resume                   - Resume playback
!skip                     - Skip current track
!queue                    - Show queue
!stop                     - Stop and clear queue
```

## Project Structure

```
ChordKnight/
├── api/           # REST API (Ktor)
├── bot/           # Discord Bot (JDA)
├── chess/         # Chess Engine
├── music/         # Music Player
└── web/           # Web UI (SvelteKit)
```

## Running Tests

```bash
# Run all tests
./gradlew test

# Run specific module tests
./gradlew :chess:test
./gradlew :music:test
```

## Common Issues

### Port 8080 Already in Use

Change the port:
```bash
export API_PORT=8081
./gradlew :api:run
```

### Discord Bot Won't Start

Make sure:
1. `DISCORD_TOKEN` is set in `.env` or environment
2. Token is valid
3. Bot has proper permissions in Discord Developer Portal

### Build Fails

Try cleaning:
```bash
./gradlew clean build
```

## Next Steps

- Read [ARCHITECTURE.md](ARCHITECTURE.md) for detailed architecture
- Read [DEVELOPMENT.md](DEVELOPMENT.md) for development guidelines
- Read [API.md](API.md) for API documentation
- Explore the code in each module

## Getting Help

- Open an issue on GitHub
- Check the documentation files
- Review existing issues and discussions

## Features

✅ **Chess Engine** - Play chess with move validation  
✅ **Music Player** - Stream music (stub implementation)  
✅ **Discord Bot** - Full-featured bot with command system  
✅ **REST API** - RESTful API for integrations  
✅ **Web UI** - Modern SvelteKit interface  
✅ **Dynamic Features** - Load/unload features dynamically  
✅ **Per-Guild State** - Separate state per Discord server  
✅ **Dependency Injection** - Clean architecture with Koin  
✅ **Coroutines** - Async operations throughout  

## License

MIT License - See LICENSE file for details
