import com.google.gradle.Libs

apply(from = "${rootProject.projectDir}/gradle/config/module-core.gradle")

plugins {
    id("com.android.library")
}

android {
    namespace = "io.ganguo.core.update"
}

dependencies {
    implementation(Libs.Network.okhttp)
    implementation(Libs.Network.conscrypt)
    implementation(Libs.Kotlin.core_ktx)
}