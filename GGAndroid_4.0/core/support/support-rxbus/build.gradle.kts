import com.google.gradle.Libs

apply(from = "${rootProject.projectDir}/gradle/config/module-core.gradle")

plugins {
    id("com.android.library")
}

android {
    namespace = "io.ganguo.rxbus"
}

dependencies {
    api(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    api(Libs.Android.annotations)
    api(Libs.Rx.android)
    api(Libs.Rx.java)
    api(Libs.Android.core)
}