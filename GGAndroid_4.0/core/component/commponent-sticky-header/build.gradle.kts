import com.google.gradle.Libs

apply(from = "${rootProject.projectDir}/gradle/config/module-core.gradle")

android {
    namespace = "io.ganguo.sticky_header.header"
}

plugins {
    id("com.android.library")
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(Libs.Android.recyclerview)
    implementation(Libs.Android.annotations)
    implementation(project(":core:support:support-resources"))
    implementation(Libs.Font.tinypinyin)
    implementation(Libs.Font.tinypinyin_android)
}
