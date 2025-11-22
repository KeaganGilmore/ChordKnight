plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
    application
}

application {
    mainClass.set("com.chordknight.api.ApplicationKt")
}

dependencies {
    implementation(project(":chess"))
    implementation(project(":music"))
    
    implementation(Dependencies.kotlinStdlib)
    implementation(Dependencies.kotlinReflect)
    implementation(Dependencies.coroutinesCore)
    implementation(Dependencies.serializationJson)
    implementation(Dependencies.ktorServerCore)
    implementation(Dependencies.ktorServerNetty)
    implementation(Dependencies.ktorServerCors)
    implementation(Dependencies.ktorServerContentNegotiation)
    implementation(Dependencies.ktorSerializationJson)
    implementation(Dependencies.koinCore)
    implementation(Dependencies.koinKtor)
    implementation(Dependencies.logback)
    implementation(Dependencies.dotenv)
    
    testImplementation(Dependencies.junitApi)
    testImplementation(Dependencies.kotlinTest)
    testRuntimeOnly(Dependencies.junitEngine)
}
