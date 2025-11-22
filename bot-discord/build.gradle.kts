plugins {
    alias(libs.plugins.kotlin.serialization)
    application
}

application {
    mainClass.set("com.chordknight.discord.MainKt")
}

dependencies {
    implementation(project(":bot-core"))
    implementation(project(":bot-music"))
    implementation(project(":bot-chess"))
    
    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.jdk8)
    implementation(libs.jda)
    implementation(libs.koin.core)
    implementation(libs.slf4j.api)
    implementation(libs.logback.classic)
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "com.chordknight.discord.MainKt"
    }
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
}
