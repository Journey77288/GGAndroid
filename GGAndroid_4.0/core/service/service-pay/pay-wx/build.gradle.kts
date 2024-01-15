import com.google.gradle.Libs

apply(from = "${rootProject.projectDir}/gradle/config/module-core.gradle")

plugins {
    id("com.android.library")
    kotlin("plugin.serialization")
}

android {
    namespace = "io.ganguo.pay.wxpay"
}

dependencies {
    api(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(project(":core:service:service-pay:pay-core"))
    implementation(project(":core:support:support-factory"))
    implementation(Libs.Open.wechat_sdk)
    implementation(Libs.Kotlin.serialization)
}
