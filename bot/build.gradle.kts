plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
    application
}

application {
    mainClass.set("com.chordknight.bot.BotApplicationKt")
}

dependencies {
    implementation(project(":chess"))
    implementation(project(":music"))
    
    implementation(Dependencies.kotlinStdlib)
    implementation(Dependencies.kotlinReflect)
    implementation(Dependencies.coroutinesCore)
    implementation(Dependencies.serializationJson)
    implementation(Dependencies.jda)
    implementation(Dependencies.koinCore)
    implementation(Dependencies.logback)
    implementation(Dependencies.dotenv)
    
    testImplementation(Dependencies.junitApi)
    testImplementation(Dependencies.kotlinTest)
    testRuntimeOnly(Dependencies.junitEngine)
}
