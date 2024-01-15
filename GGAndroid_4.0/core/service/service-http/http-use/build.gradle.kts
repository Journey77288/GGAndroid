import com.google.gradle.Libs

apply(from = "${rootProject.projectDir}/gradle/config/module-core.gradle")

android {
    namespace = "io.ganguo.http.use"
}

plugins {
    id("com.android.library")
    kotlin("plugin.serialization")
}

dependencies {
    api(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    api(project(":core:service:service-http:http-core"))
    api(project(":core:support:support-rxjava"))
    api(Libs.Kotlin.serialization)
}
