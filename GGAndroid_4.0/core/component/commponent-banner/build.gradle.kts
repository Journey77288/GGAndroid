import com.google.gradle.Libs

apply(from = "${rootProject.projectDir}/gradle/config/module-core.gradle")

android {
    namespace = "io.component.banner"
}

plugins {
    id("com.android.library")
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(Libs.Android.recyclerview)
    implementation(Libs.Android.annotations)
    implementation(Libs.Kotlin.stdlib_jdk7)
    implementation(Libs.Android.core)
    implementation(Libs.Android.appcompat)
    implementation(Libs.Android.fragment)
    implementation(Libs.Android.viewpager2)
    implementation(project(":core:support:support-utils"))
}
