import com.google.gradle.Libs

apply(from = "${rootProject.projectDir}/gradle/config/module-core.gradle")

android {
    namespace = "io.commponent.indicator"
}

plugins {
    id("com.android.library")
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(Libs.Android.recyclerview)
    implementation(Libs.Android.core)
    implementation(Libs.Widget.drawabletoolbox)
}
