import com.google.gradle.Libs

apply(from = "${rootProject.projectDir}/gradle/config/module-core.gradle")

plugins {
    id("com.android.library")
}

android {
    namespace = "io.ganguo.appcompat"
}

dependencies {
    api(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(Libs.Android.core)
    implementation(Libs.Android.annotations)

    //kotlin
    implementation(Libs.Kotlin.stdlib_jdk7)
    implementation(Libs.Kotlin.rxjava)
    implementation(Libs.Kotlin.core_ktx)
}

