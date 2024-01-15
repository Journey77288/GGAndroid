import com.google.gradle.Libs

apply(from = "${rootProject.projectDir}/gradle/config/module-core.gradle")

plugins {
    id("com.android.library")
}

android {
    namespace = "io.ganguo.layout"
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(Libs.Android.annotations)
    implementation(Libs.Android.v4)
    implementation(Libs.Android.appcompat)

    api(Libs.SmartRefresh.layout)
    api(Libs.SmartRefresh.header_classics)
    api(Libs.SmartRefresh.header_material)
    api(Libs.SmartRefresh.footer_classics)
}
