import com.google.gradle.Libs

apply(from = "${rootProject.projectDir}/gradle/config/module-core.gradle")

android {
    namespace = "io.ganguo.single"
}

plugins {
    id("com.android.library")
}

dependencies {
    implementation(project(":core:support:support-permission"))
    implementation(project(":core:support:support-utils"))
    implementation(project(":core:support:support-rx-activity-result"))
    api(Libs.Android.core)
    api(Libs.Android.appcompat)
    implementation(Libs.Rx.android)
    implementation(Libs.Rx.java)
}

