import com.google.gradle.Libs

apply(from = "${rootProject.projectDir}/gradle/config/module-core.gradle")

plugins {
    id("com.android.library")
}

android {
    namespace = "io.ganguo.databinding"
}

dependencies {
    api(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    api(Libs.Android.constrainLayout)
    api(Libs.Android.core)
    api(Libs.Android.recyclerview)
    api(Libs.Android.annotations)
    api(Libs.Widget.drawabletoolbox)

    //kotlin
    api(Libs.Kotlin.stdlib_jdk7)
    api(Libs.Kotlin.rxjava)
    api(Libs.Kotlin.core_ktx)
}


