import com.google.gradle.Libs

apply(from = "${rootProject.projectDir}/gradle/config/module-core.gradle")

plugins {
    id("com.android.library")
    kotlin("plugin.serialization")
}

android {
    namespace = "io.ganguo.rxjava"
}

dependencies {
    api(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    api(Libs.Kotlin.serialization)
    api(Libs.Android.annotations)
    api(Libs.Rx.android)
    api(Libs.Rx.java)
}
