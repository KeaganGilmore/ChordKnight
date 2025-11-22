plugins {
    alias(libs.plugins.kotlin.serialization)
    application
}

application {
    mainClass.set("com.chordknight.api.MainKt")
}

dependencies {
    implementation(project(":bot-core"))
    implementation(project(":bot-music"))
    implementation(project(":bot-chess"))
    
    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.netty)
    implementation(libs.ktor.server.websockets)
    implementation(libs.ktor.server.content.negotiation)
    implementation(libs.ktor.server.auth)
    implementation(libs.ktor.server.sessions)
    implementation(libs.ktor.server.cors)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.koin.core)
    implementation(libs.koin.ktor)
    implementation(libs.slf4j.api)
    implementation(libs.logback.classic)
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "com.chordknight.api.MainKt"
    }
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
}
