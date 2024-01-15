import com.google.gradle.Libs

apply(from = "${rootProject.projectDir}/gradle/config/module-core.gradle")

android {
    namespace = "io.ganguo.scanner"
}

plugins {
    id("com.android.library")
}

dependencies {
    api(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(Libs.Android.appcompat)
    implementation(Libs.Widget.google_zxing)
    implementation(Libs.Rx.android)
    implementation(Libs.Rx.java)
    api(Libs.Widget.cameraview)
}
