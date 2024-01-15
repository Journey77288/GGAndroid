import com.google.gradle.Libs

apply(from = "${rootProject.projectDir}/gradle/config/module-core.gradle")

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

android {
    namespace = "io.image.glide"
}

dependencies {
    api(project(":core:service:service-image:image-core"))
    api(Libs.Image.glide)
    kapt(Libs.Image.glide_annotation_processor)
    api(Libs.Image.glide_recyclerview_integration)
    api(Libs.Image.glide_transformations)
}
