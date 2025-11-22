object Versions {
    const val kotlin = "1.9.22"
    const val kotlinxCoroutines = "1.7.3"
    const val kotlinxSerialization = "1.6.2"
    const val ktor = "2.3.7"
    const val jda = "5.0.0-beta.20"
    const val lavaplayerYt = "1.3.78"
    const val lavakord = "6.4.0"
    const val koin = "3.5.3"
    const val logback = "1.4.14"
    const val dotenv = "6.4.1"
    const val junit = "5.10.1"
}

object Dependencies {
    // Kotlin
    const val kotlinStdlib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"
    const val kotlinReflect = "org.jetbrains.kotlin:kotlin-reflect:${Versions.kotlin}"
    
    // Coroutines
    const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlinxCoroutines}"
    const val coroutinesJdk8 = "org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:${Versions.kotlinxCoroutines}"
    
    // Serialization
    const val serializationJson = "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.kotlinxSerialization}"
    
    // Ktor
    const val ktorServerCore = "io.ktor:ktor-server-core:${Versions.ktor}"
    const val ktorServerNetty = "io.ktor:ktor-server-netty:${Versions.ktor}"
    const val ktorServerCors = "io.ktor:ktor-server-cors:${Versions.ktor}"
    const val ktorServerContentNegotiation = "io.ktor:ktor-server-content-negotiation:${Versions.ktor}"
    const val ktorSerializationJson = "io.ktor:ktor-serialization-kotlinx-json:${Versions.ktor}"
    const val ktorClientCore = "io.ktor:ktor-client-core:${Versions.ktor}"
    const val ktorClientCio = "io.ktor:ktor-client-cio:${Versions.ktor}"
    
    // JDA
    const val jda = "net.dv8tion:JDA:${Versions.jda}"
    
    // Lavalink/Music
    const val lavaplayerYt = "dev.arbjerg:lavaplayer:${Versions.lavaplayerYt}"
    const val lavakord = "dev.schlaubi.lavakord:kord:${Versions.lavakord}"
    
    // Dependency Injection
    const val koinCore = "io.insert-koin:koin-core:${Versions.koin}"
    const val koinKtor = "io.insert-koin:koin-ktor:${Versions.koin}"
    
    // Logging
    const val logback = "ch.qos.logback:logback-classic:${Versions.logback}"
    
    // Config
    const val dotenv = "io.github.cdimascio:dotenv-kotlin:${Versions.dotenv}"
    
    // Testing
    const val junitApi = "org.junit.jupiter:junit-jupiter-api:${Versions.junit}"
    const val junitEngine = "org.junit.jupiter:junit-jupiter-engine:${Versions.junit}"
    const val kotlinTest = "org.jetbrains.kotlin:kotlin-test:${Versions.kotlin}"
}
