import com.google.gradle.Libs

apply(from = "${rootProject.projectDir}/gradle/config/module-core.gradle")

plugins {
    id("com.android.library")
}

android {
    namespace = "io.support.recyclerview"
}

dependencies {
    api(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(Libs.Android.recyclerview)
    implementation(Libs.Android.recyclerview_selection)
    implementation(Libs.Widget.recyclerview_animators) {
        exclude("androidx.recyclerview", "recyclerview")
    }
    //kotlin
    implementation(Libs.Kotlin.stdlib_jdk7)
    implementation(Libs.Kotlin.rxjava)
    implementation(Libs.Kotlin.core_ktx)
}

