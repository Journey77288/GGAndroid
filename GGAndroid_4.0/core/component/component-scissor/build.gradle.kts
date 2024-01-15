import com.google.gradle.Libs

apply(from = "${rootProject.projectDir}/gradle/config/module-core.gradle")

android {
    namespace = "com.example.commponent_scissor"
}

plugins {
    id("com.android.library")
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(Libs.Android.v4)
    implementation(Libs.Android.appcompat)
    implementation(Libs.Android.annotations)
    implementation(Libs.Image.glide)
    implementation(Libs.Image.picasso)
    implementation(Libs.Image.coil)
    implementation(Libs.Image.coil_base)
}