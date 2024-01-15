apply(from = "${rootProject.projectDir}/gradle/config/module-core.gradle")

plugins {
    id("com.android.library")
}

android {
    namespace = "io.ganguo.pay.core"
}

dependencies {
    api(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(project(":core:support:support-factory"))
}
