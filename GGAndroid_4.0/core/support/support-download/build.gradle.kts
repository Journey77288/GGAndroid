import com.google.gradle.Libs

apply(from = "${rootProject.projectDir}/gradle/config/module-core.gradle")

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

android {
    namespace = "io.ganguo.download"
}

dependencies {
    api(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    api(project(":core:support:support-utils"))
    implementation(Libs.Android.core)
    implementation(Libs.Android.annotations)

    //utilcode
    implementation(Libs.Common.utilcode)

    //aria
    implementation(Libs.Download.aria)
    kapt(Libs.Download.aria_compile)
}