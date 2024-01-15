apply(from = "${rootProject.projectDir}/gradle/config/module-core.gradle")

plugins {
    id("com.android.library")
}

android {
    namespace = "io.ganguo.pay.alipay"
}

dependencies {
    api(fileTree(mapOf("dir" to "libs", "include" to listOf("*.aar"))))
    implementation(project(":core:service:service-pay:pay-core"))
    implementation(project(":core:support:support-factory"))
}
