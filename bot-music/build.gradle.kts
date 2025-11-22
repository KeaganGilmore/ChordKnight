plugins {
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    implementation(project(":bot-core"))
    
    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.koin.core)
    implementation(libs.slf4j.api)
    // Lavalink client will be added when available
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.cio)
    implementation(libs.ktor.client.websockets)
}
