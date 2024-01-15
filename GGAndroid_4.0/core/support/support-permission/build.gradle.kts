import com.google.gradle.Libs

apply(from = "${rootProject.projectDir}/gradle/config/module-core.gradle")

plugins {
    id("com.android.library")
}

android {
    namespace = "io.ganguo.permission"
}

dependencies {
    api(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    api(project(":core:support:support-utils"))
    api(project(":core:support:support-cache"))
    api(Libs.Permission.permissionx)
    api(Libs.Rx.android)
    api(Libs.Android.fragment)
    api(Libs.Rx.java)
    //kotlin
    api(Libs.Kotlin.stdlib_jdk7)
    api(Libs.Kotlin.rxjava)
    api(Libs.Kotlin.core_ktx)
}

