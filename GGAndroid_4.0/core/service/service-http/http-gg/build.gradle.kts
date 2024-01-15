apply(from = "${rootProject.projectDir}/gradle/config/module-core.gradle")

android {
    namespace = "io.ganguo.http.gg"
}

plugins {
    id("com.android.library")
    kotlin("plugin.serialization")
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    api(project(":core:service:service-http:http-use"))
    implementation(com.google.gradle.Libs.Kotlin.serialization)
}
