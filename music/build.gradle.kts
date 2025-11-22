plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
}

dependencies {
    implementation(Dependencies.kotlinStdlib)
    implementation(Dependencies.kotlinReflect)
    implementation(Dependencies.coroutinesCore)
    implementation(Dependencies.serializationJson)
    implementation(Dependencies.koinCore)
    implementation(Dependencies.logback)
    // Note: Lavalink/Lavaplayer dependencies can be added when needed
    // implementation(Dependencies.lavaplayerYt)
    implementation(Dependencies.ktorClientCore)
    implementation(Dependencies.ktorClientCio)
    
    testImplementation(Dependencies.junitApi)
    testImplementation(Dependencies.kotlinTest)
    testRuntimeOnly(Dependencies.junitEngine)
}
