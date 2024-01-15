import com.google.gradle.Libs

apply(from = "${rootProject.projectDir}/gradle/config/module-core.gradle")

plugins {
    id("com.android.library")
}

android {
    namespace = "io.ganguo.lifecycle"
}

dependencies {
    api(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    api(Libs.Android.lifecycle_extensions)
    api(Libs.Android.lifecycle_runtime)
    api(Libs.Android.core)
    api(Libs.Android.appcompat)

    //kotlin
    api(Libs.Kotlin.stdlib_jdk7)
    api(Libs.Kotlin.rxjava)
    api(Libs.Kotlin.core_ktx)
}

