import com.google.gradle.Libs

apply(from = "${rootProject.projectDir}/gradle/config/module-core.gradle")

plugins {
    id("com.android.library")
}

android {
    namespace = "io.ganguo.sample.sdk"
}

dependencies {
    api(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(project(":core:support:support-factory"))
    implementation(Libs.Rx.android)
    implementation(Libs.Rx.java)
    implementation(Libs.Kotlin.core_ktx)
    implementation(Libs.Android.fragment)
}
