import com.google.gradle.Libs

apply(from = "${rootProject.projectDir}/gradle/config/module-core.gradle")

android {
    namespace = "io.ganguo.mvvm"
}

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}
dependencies {
    api(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    api(project(":core:support:support-app"))
    api(project(":core:support:support-rxjava"))
    api(project(":core:support:support-recyclerview"))
    api(project(":core:support:support-lifecycle"))
    api(project(":core:support:support-resources"))
    api(project(":core:support:support-utils"))
    api(project(":core:support:support-rx-activity-result"))

    api(Libs.Android.recyclerview)
    api(Libs.Widget.recyclerview_animators)

    api(Libs.Kotlin.coroutines_core)
    api(Libs.Kotlin.coroutines_android)


}

