# Contributing to ChordKnight

Thank you for your interest in contributing to ChordKnight! This guide will help you get started with development.

## Project Structure

```
ChordKnight/
├── bot-core/               # Core domain logic, config, DB, DI
│   ├── config/            # Configuration management
│   ├── db/                # Database models and repositories
│   ├── di/                # Dependency injection modules
│   └── feature/           # Feature interface definition
├── bot-discord/           # Discord bot implementation (JDA)
├── bot-music/             # Music feature (Lavalink integration)
├── bot-chess/             # Chess feature (puzzles, openings)
├── api-server/            # Ktor REST/WebSocket API
└── web/                   # SvelteKit web dashboard
```

## Development Setup

### Prerequisites

- **Java 17+**: `java -version`
- **Gradle 8.5+**: Included via wrapper
- **Node.js 18+**: For web dashboard
- **PostgreSQL 16**: Or use Docker Compose
- **Discord Bot Token**: From [Discord Developer Portal](https://discord.com/developers/applications)

### Quick Start

1. **Clone the repository**
   ```bash
   git clone https://github.com/KeaganGilmore/ChordKnight.git
   cd ChordKnight
   ```

2. **Set up environment**
   ```bash
   cp sample.env .env
   # Edit .env with your Discord bot token
   ```

3. **Start dependencies with Docker**
   ```bash
   docker-compose up -d postgres lavalink
   ```

4. **Build the project**
   ```bash
   ./gradlew build
   ```

5. **Run the bot**
   ```bash
   ./gradlew :bot-discord:run
   ```

6. **Run the API server** (in another terminal)
   ```bash
   ./gradlew :api-server:run
   ```

7. **Run the web dashboard** (in another terminal)
   ```bash
   cd web
   npm install
   npm run dev
   ```

## Adding a New Feature

ChordKnight's modular architecture makes it easy to add new features. Follow these steps:

### 1. Create a New Module

```bash
mkdir -p bot-myfeature/src/main/kotlin/com/chordknight/myfeature
```

### 2. Add to settings.gradle.kts

```kotlin
include("bot-myfeature")
```

### 3. Create build.gradle.kts

```kotlin
// bot-myfeature/build.gradle.kts
plugins {
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    implementation(project(":bot-core"))
    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.koin.core)
    implementation(libs.slf4j.api)
}
```

### 4. Implement Feature Interface

```kotlin
// bot-myfeature/src/main/kotlin/com/chordknight/myfeature/MyFeature.kt
package com.chordknight.myfeature

import com.chordknight.core.feature.BaseFeature
import org.slf4j.LoggerFactory

class MyFeature : BaseFeature() {
    private val logger = LoggerFactory.getLogger(MyFeature::class.java)

    override val id = "myfeature"
    override val name = "My Feature"

    override suspend fun initialize() {
        logger.info("Initializing My Feature")
        // Add initialization logic here
    }

    override suspend fun shutdown() {
        logger.info("Shutting down My Feature")
        // Add cleanup logic here
    }

    override suspend fun isEnabledForGuild(guildId: String): Boolean {
        // Check if feature is enabled for this guild
        return true
    }
}
```

### 5. Create Koin Module

```kotlin
// bot-myfeature/src/main/kotlin/com/chordknight/myfeature/MyModule.kt
package com.chordknight.myfeature

import com.chordknight.core.feature.Feature
import org.koin.dsl.module

val myFeatureModule = module {
    single<Feature> { MyFeature() }
}
```

### 6. Register in Main

```kotlin
// bot-discord/src/main/kotlin/com/chordknight/discord/Main.kt
import com.chordknight.myfeature.myFeatureModule

// In main():
val koinApp = startKoin {
    modules(coreModule, musicModule, chessModule, myFeatureModule)
}
```

### 7. Add Discord Commands (Optional)

If your feature needs Discord commands, update `CommandListener.kt`:

```kotlin
override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) {
    scope.launch {
        try {
            when (event.name) {
                "ping" -> handlePing(event)
                "mycommand" -> handleMyCommand(event)
                // ...
            }
        } catch (e: Exception) {
            logger.error("Error handling command: ${event.name}", e)
        }
    }
}
```

And register the command in `DiscordBot.kt`:

```kotlin
private fun registerCommands() {
    val commands = listOf(
        Commands.slash("ping", "Check if the bot is responsive"),
        Commands.slash("mycommand", "Description of my command")
    )
    // ...
}
```

### 8. Add API Routes (Optional)

If your feature needs API endpoints, create routes in `api-server`:

```kotlin
// api-server/src/main/kotlin/com/chordknight/api/MyFeatureRoutes.kt
fun Route.myFeatureRoutes() {
    route("/guilds/{guildId}/myfeature") {
        get {
            val guildId = call.parameters["guildId"]
            // Fetch and return data
            call.respond(mapOf("data" to "value"))
        }
    }
}
```

Register in `Main.kt`:

```kotlin
routing {
    // ...
    myFeatureRoutes()
}
```

## Code Style

- Follow Kotlin coding conventions
- Use meaningful variable and function names
- Add documentation comments for public APIs
- Keep functions small and focused
- Use coroutines for async operations

## Testing

Run tests with:

```bash
./gradlew test
```

Write tests in `src/test/kotlin/` following existing patterns.

## Building

### Build All Modules

```bash
./gradlew build
```

### Build Specific Module

```bash
./gradlew :bot-discord:build
```

### Create Executable JARs

```bash
./gradlew :bot-discord:jar
./gradlew :api-server:jar
```

JARs will be in `build/libs/` of each module.

## Docker

### Build Docker Images

```bash
docker-compose build
```

### Run All Services

```bash
docker-compose up -d
```

### View Logs

```bash
docker-compose logs -f bot-discord
```

## Common Tasks

### Add a New Dependency

1. Add to `gradle/libs.versions.toml`:
   ```toml
   [versions]
   mylib = "1.0.0"
   
   [libraries]
   mylib = { module = "com.example:mylib", version.ref = "mylib" }
   ```

2. Use in module `build.gradle.kts`:
   ```kotlin
   dependencies {
       implementation(libs.mylib)
   }
   ```

### Update Database Schema

1. Modify table definition in `bot-core/src/main/kotlin/com/chordknight/core/db/`
2. Exposed will auto-create tables on startup (development only)
3. For production, use migrations

### Debug the Bot

Run with remote debugging:

```bash
./gradlew :bot-discord:run --debug-jvm
```

Then attach your IDE debugger to port 5005.

## Pull Request Guidelines

1. **Fork** the repository
2. **Create a branch** for your feature: `git checkout -b feature/my-feature`
3. **Make your changes** following the code style
4. **Test** your changes thoroughly
5. **Commit** with clear messages: `git commit -m "Add feature: description"`
6. **Push** to your fork: `git push origin feature/my-feature`
7. **Open a Pull Request** with a clear description

### PR Checklist

- [ ] Code follows project style guidelines
- [ ] New features have tests
- [ ] Documentation is updated
- [ ] Build passes (`./gradlew build`)
- [ ] No new warnings
- [ ] Feature is modular and doesn't break existing code

## Need Help?

- Check the [README.md](README.md) for setup instructions
- Review existing features for examples
- Open an issue for questions or bugs
- Join our Discord server (coming soon)

## License

By contributing to ChordKnight, you agree that your contributions will be licensed under the MIT License.
