import com.google.gradle.Libs

apply(from = "${rootProject.projectDir}/gradle/config/module-core.gradle")

plugins {
    id("com.android.library")
}

android {
    namespace = "io.ganguo.app"
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    api(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    api(Libs.Android.core)
    api(Libs.Android.appcompat)
    api(Libs.Android.annotations)

    // enable multidex
    api(Libs.Android.multidex)

    // test
    androidTestImplementation(Libs.Android.test)
    testImplementation(Libs.Test.junit)

    // kotlin
    api(Libs.Kotlin.stdlib_jdk7)
    api(Libs.Kotlin.rxjava)
    api(Libs.Kotlin.core_ktx)
    api(Libs.Android.fragment)

    // utilcode
    implementation(Libs.Common.utilcode)
}
