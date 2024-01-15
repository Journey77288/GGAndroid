import com.google.gradle.Libs

apply(from = "${rootProject.projectDir}/gradle/config/module-core.gradle")

plugins {
    id("com.android.library")
}

android {
    namespace = "io.image.coil"
}

dependencies {
    api(project(":core:service:service-image:image-core"))
    api(Libs.Image.coil)
    api(Libs.Image.coil_base)

    //以下依赖根据项目需求添加
    api(Libs.Image.coil_gif)
    api(Libs.Image.coil_video)
    api(Libs.Image.coil_svg)

    implementation(Libs.Kotlin.core_ktx)
}
