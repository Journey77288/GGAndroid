import com.google.gradle.Libs

apply(from = "${rootProject.projectDir}/gradle/config/module-core.gradle")

android {
    namespace = "io.ganguo.http2"
}

plugins {
    id("com.android.library")
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    //可视化调试工具，使用方式：chrome://inspect
    api(Libs.Network.stetho)
    api(Libs.Network.squareup_okio)
}
