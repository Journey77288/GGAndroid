import com.google.gradle.Libs

apply(from = "${rootProject.projectDir}/gradle/config/module-core.gradle")

plugins {
    id("com.android.library")
}

android {
    namespace = "io.ganguo.picker"

    /**
     * 配置viewBinding
     */
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(project(":core:support:support-permission"))
    implementation(project(":core:support:support-rx-activity-result"))
    implementation(Libs.Android.appcompat)
    implementation(Libs.Common.utilcode)
    implementation(Libs.Kotlin.stdlib_jdk7)
    implementation(Libs.Kotlin.core_ktx)
    implementation(Libs.Android.material_design)
    implementation(Libs.Widget.scale_imageview)
    implementation(Libs.Widget.photoview)
    testImplementation(Libs.Test.junit)
    androidTestImplementation("androidx.test.ext:junit:1.1.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")
}
