import com.google.gradle.Libs

apply(from = "${rootProject.projectDir}/gradle/config/module-core.gradle")

plugins {
    id("com.android.library")
}

android {
    namespace = "io.ganguo.sina"
}

dependencies {
    api(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar", "*.aar"))))
    implementation(Libs.Open.sina_sdk)
    implementation(project(":core:service:service-open:open-core"))
    implementation(project(":core:support:support-factory"))
    implementation(Libs.Android.fragment)
}

