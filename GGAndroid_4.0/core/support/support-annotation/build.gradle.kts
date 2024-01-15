import com.google.gradle.Libs

apply(from = "${rootProject.projectDir}/gradle/config/module-core.gradle")

plugins {
    id("com.android.library")
}

android {
    namespace = "io.ganguo.annotation"
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    api(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    api(Libs.Android.core)
    api(Libs.Android.annotations)
}
