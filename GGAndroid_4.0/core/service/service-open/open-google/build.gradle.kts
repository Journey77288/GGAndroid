import com.google.gradle.Libs

apply(from = "${rootProject.projectDir}/gradle/config/module-core.gradle")

plugins {
    id("com.android.library")
}

android {
    namespace = "io.ganguo.google"
}

dependencies {
    api(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(Libs.Open.google_play_services_auth)
    implementation(project(":core:support:support-factory"))
    implementation(project(":core:service:service-open:open-core"))
    implementation(Libs.Android.fragment)
}
