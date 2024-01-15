import com.google.gradle.Libs

apply(from = "${rootProject.projectDir}/gradle/config/module-core.gradle")

android {
    namespace = "io.ganguo.http.retrofit"
}

plugins {
    id("com.android.library")
    kotlin("plugin.serialization")
}


dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    api(project(":core:service:service-http:http-core"))
    implementation(project(":core:support:support-utils"))
    implementation(project(":core:support:support-resources"))

    api(Libs.Kotlin.serialization)
    api(Libs.Network.retrofit_converter_kotlin)
    api(Libs.Network.retrofit_adapter_rxjava)
    api(Libs.Network.okhttp)
    api(Libs.Network.conscrypt)
    api(Libs.Rx.android)
    api(Libs.Network.retrofit)
    api(Libs.Rx.java)
    api(Libs.Network.okhttp_logging)

    //Android Studio 抓包调试
    api(Libs.Network.okhttpprofiler)

    //可视化调试工具，使用方式：chrome://inspect
    api(Libs.Network.stetho_okhttp)

    //手机端抓包调试
    debugApi(Libs.Network.chucker)
    releaseApi(Libs.Network.chucker_library_no_op)
}
