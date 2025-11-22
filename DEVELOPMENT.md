# Development Guide

## Prerequisites

- **JDK 17 or higher**: Required for Kotlin compilation
- **Gradle**: Included via wrapper (`./gradlew`)
- **Node.js 18+**: Required for the web module
- **Docker** (optional): For running Lavalink music server

## Project Structure

```
ChordKnight/
├── bot/                    # Discord bot module
│   ├── src/main/kotlin/
│   └── build.gradle.kts
├── api/                    # REST API module
│   ├── src/main/kotlin/
│   └── build.gradle.kts
├── music/                  # Music player module
│   ├── src/main/kotlin/
│   └── build.gradle.kts
├── chess/                  # Chess utilities module
│   ├── src/main/kotlin/
│   └── build.gradle.kts
├── web/                    # SvelteKit web UI
│   ├── src/
│   └── package.json
├── buildSrc/              # Shared build configuration
│   └── src/main/kotlin/Dependencies.kt
├── build.gradle.kts       # Root build configuration
└── settings.gradle.kts    # Project structure definition
```

## Building the Project

### Build all modules
```bash
./gradlew build
```

### Build specific module
```bash
./gradlew :chess:build
./gradlew :api:build
./gradlew :bot:build
```

### Clean build
```bash
./gradlew clean build
```

## Running Tests

### Run all tests
```bash
./gradlew test
```

### Run tests for specific module
```bash
./gradlew :chess:test
./gradlew :music:test
```

### Run tests with coverage
```bash
./gradlew test jacocoTestReport
```

## Running the Applications

### API Server
```bash
./gradlew :api:run
```

The API will start on `http://localhost:8080`

### Discord Bot
```bash
# First, set your Discord token in .env
export DISCORD_TOKEN=your_token_here
./gradlew :bot:run
```

### Web UI
```bash
cd web
npm install
npm run dev
```

The web UI will be available at `http://localhost:5173`

## Configuration

### Environment Variables

Create a `.env` file in the root directory:

```env
# Discord Bot
DISCORD_TOKEN=your_discord_bot_token
BOT_PREFIX=!

# API
API_HOST=0.0.0.0
API_PORT=8080
CORS_ALLOWED_HOSTS=*

# Features
ENABLE_MUSIC=true
ENABLE_CHESS=true

# Environment
ENVIRONMENT=development
```

### Module-Specific Configuration

Each module can be configured independently:

- **Bot**: Edit `bot/src/main/kotlin/com/chordknight/bot/config/Config.kt`
- **API**: Edit `api/src/main/kotlin/com/chordknight/api/config/Config.kt`

## Adding Features

### Creating a New Bot Feature

1. Create a new feature class in `bot/src/main/kotlin/com/chordknight/bot/features/impl/`:

```kotlin
package com.chordknight.bot.features.impl

import com.chordknight.bot.features.BaseFeature
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

class MyFeature : BaseFeature("myfeature", "Description of my feature") {
    
    override suspend fun handleCommand(
        event: MessageReceivedEvent,
        command: String,
        args: List<String>
    ): Boolean {
        if (command == "mycommand") {
            event.channel.sendMessage("My response").queue()
            return true
        }
        return false
    }
}
```

2. Register the feature in `FeatureLoader.kt`:

```kotlin
registerFeature(MyFeature())
```

### Adding API Endpoints

1. Create a new route file in `api/src/main/kotlin/com/chordknight/api/routes/`:

```kotlin
package com.chordknight.api.routes

import io.ktor.server.routing.*
import io.ktor.server.response.*

fun Route.myRoutes() {
    route("/myroute") {
        get {
            call.respondText("Hello from my route!")
        }
    }
}
```

2. Register the routes in `Application.kt`:

```kotlin
routing {
    healthRoutes()
    chessRoutes()
    myRoutes()  // Add your routes
}
```

## Dependency Management

Dependencies are managed in `buildSrc/src/main/kotlin/Dependencies.kt`:

```kotlin
object Versions {
    const val myLibrary = "1.0.0"
}

object Dependencies {
    const val myLibrary = "com.example:my-library:${Versions.myLibrary}"
}
```

Then use in module `build.gradle.kts`:

```kotlin
dependencies {
    implementation(Dependencies.myLibrary)
}
```

## Code Style

- Follow [Kotlin Coding Conventions](https://kotlinlang.org/docs/coding-conventions.html)
- Use 4 spaces for indentation
- Maximum line length: 120 characters
- Use meaningful variable and function names

## Testing Guidelines

- Write tests for all public APIs
- Use descriptive test names
- Follow AAA pattern: Arrange, Act, Assert
- Mock external dependencies

Example:

```kotlin
@Test
fun `test that feature does something`() {
    // Arrange
    val feature = MyFeature()
    
    // Act
    val result = feature.doSomething()
    
    // Assert
    assertEquals(expected, result)
}
```

## Debugging

### Enable Debug Logging

Edit `logback.xml` in the module's resources:

```xml
<logger name="com.chordknight" level="DEBUG" />
```

### Remote Debugging

Start with debug enabled:

```bash
./gradlew :api:run --debug-jvm
```

Then attach your IDE debugger to port 5005.

## Common Issues

### Port Already in Use

If port 8080 is already in use:

```bash
export API_PORT=8081
./gradlew :api:run
```

### Discord Bot Token Invalid

Ensure your token is valid and the bot has proper permissions:
- Message Content Intent must be enabled in Discord Developer Portal
- Bot must have `GUILD_MESSAGES` and `MESSAGE_CONTENT` permissions

### Gradle Build Fails

Try cleaning the build:

```bash
./gradlew clean
rm -rf .gradle/
./gradlew build
```

## Contributing

1. Create a feature branch from `main`
2. Make your changes
3. Write tests
4. Run `./gradlew build` to ensure everything compiles
5. Run `./gradlew test` to ensure tests pass
6. Submit a pull request

## Resources

- [Kotlin Documentation](https://kotlinlang.org/docs/home.html)
- [JDA Documentation](https://jda.wiki/)
- [Ktor Documentation](https://ktor.io/docs/)
- [SvelteKit Documentation](https://kit.svelte.dev/)
- [Gradle Documentation](https://docs.gradle.org/)
