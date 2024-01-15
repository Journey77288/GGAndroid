import com.google.gradle.Libs

apply(from = "${rootProject.projectDir}/gradle/config/module-core.gradle")

plugins {
    id("com.android.library")
}

android {
    namespace = "io.image"
}

dependencies {
    implementation(project(":core:support:support-resources"))
    implementation(Libs.Android.annotations)
    implementation(Libs.Android.recyclerview)
}
