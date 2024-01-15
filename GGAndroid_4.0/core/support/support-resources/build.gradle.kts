import com.google.gradle.Libs

apply(from = "${rootProject.projectDir}/gradle/config/module-core.gradle")

plugins {
    id("com.android.library")
    kotlin("plugin.serialization")
}

android {
    namespace = "io.ganguo.resources"
}

dependencies {
    api(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    api(Libs.Android.core)
    api(Libs.Android.appcompat)
    api(Libs.Android.v4)
    implementation(Libs.Android.annotations)
    implementation(Libs.Language.lingver)

    //kotlin
    implementation(Libs.Kotlin.serialization)
    implementation(Libs.Kotlin.stdlib_jdk7)
    implementation(Libs.Kotlin.rxjava)
    implementation(Libs.Kotlin.core_ktx)

}


