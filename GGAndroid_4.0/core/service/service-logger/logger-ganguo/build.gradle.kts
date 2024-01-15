import com.google.gradle.Libs

apply(from = "${rootProject.projectDir}/gradle/config/module-core.gradle")

plugins {
    id("com.android.library")
}

android {
    namespace = "io.ganguo.log.gg"
}

dependencies {
    api(project(":core:service:service-logger:logger-core"))
    testImplementation(Libs.Test.junit)
    testImplementation(Libs.Test.mockito)
    testImplementation(Libs.Test.robolectric)
    testImplementation(Libs.Android.test)
}