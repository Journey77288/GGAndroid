import com.google.gradle.Libs

apply(from = "${rootProject.projectDir}/gradle/config/module-core.gradle")

plugins {
    id("com.android.library")
}

android {
    namespace = "io.ganguo.line"
}

dependencies {
    api(fileTree(mapOf("dir" to "libs", "include" to listOf("*.aar"))))
    implementation(project(":core:service:service-open:open-core"))
    implementation(project(":core:support:support-factory"))
    implementation(Libs.Android.appcompat)
    implementation(Libs.Android.browser)
    implementation(Libs.Android.fragment)
}
