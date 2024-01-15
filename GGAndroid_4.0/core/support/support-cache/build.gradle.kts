import com.google.gradle.Libs

apply(from = "${rootProject.projectDir}/gradle/config/module-core.gradle")

plugins {
    id("com.android.library")
    kotlin("plugin.serialization")
}

android {
    namespace = "io.ganguo.cache"
}

dependencies {
    api(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(Libs.Android.annotations)
    implementation(Libs.Android.security_crypto)

    //kotlin
    implementation(Libs.Kotlin.serialization)
    implementation(Libs.Kotlin.stdlib_jdk7)
    implementation(Libs.Kotlin.rxjava)
    implementation(Libs.Kotlin.core_ktx)

    //object box
    api(Libs.Database.objectbox_kotlin)
    api(Libs.Database.objectbox_rxjava)
    api(Libs.Rx.java3_bridge)

    //mmkv
    api(Libs.Database.mmkv)
}