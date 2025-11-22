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
    
    testImplementation(Dependencies.junitApi)
    testImplementation(Dependencies.kotlinTest)
    testRuntimeOnly(Dependencies.junitEngine)
}
