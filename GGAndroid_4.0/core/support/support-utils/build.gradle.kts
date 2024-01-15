import com.google.gradle.Libs

apply(from = "${rootProject.projectDir}/gradle/config/module-core.gradle")

plugins {
    id("com.android.library")
}

android {
    namespace = "io.ganguo.utils"
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(project(":core:support:support-compat"))
    //kotlin
    implementation(Libs.Kotlin.stdlib_jdk7)
    implementation(Libs.Kotlin.rxjava)
    implementation(Libs.Kotlin.core_ktx)
    api(Libs.Common.utilcode)
    implementation(Libs.Network.gson)
    implementation(Libs.Android.viewpager2)
    implementation(Libs.Widget.shimmerlayout)
    implementation(Libs.Widget.skeleton)
    implementation(Libs.Rx.android)
    implementation(Libs.Video.video_compress)
}

