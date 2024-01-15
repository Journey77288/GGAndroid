import com.google.gradle.Libs

apply(from = "${rootProject.projectDir}/gradle/config/module-core.gradle")

android {
    namespace = "io.ganguo.state"
}

plugins {
    id("com.android.library")
}

dependencies {
    implementation(Libs.Android.core)
    testImplementation("junit:junit:4.12")
}

