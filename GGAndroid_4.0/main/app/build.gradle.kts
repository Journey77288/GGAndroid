import com.google.gradle.Libs

apply {
    from("${rootProject.projectDir}/gradle/config/app-core.gradle")
    from("flavors.gradle")
}

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    kotlin("plugin.serialization")
}

android {
    namespace = "io.ganguo.incubator"
    /**
     * 资源目录配置
     */
    sourceSets.getByName("main") {
        //xml命名规范：以activity、item、page、dialog等开头，放到对应的模块文件夹下
        res.setSrcDirs(
            listOf(
                "src/main/res/layouts/home",//首页模块 xml文件
                "src/main/res/layouts/mine",//我的模块 xml文件
                "src/main/res/layouts/main",// 通用模块 xml文件
                "src/main/res/layouts",
                "src/main/res",
            )
        )
    }
}

/**
 * 依赖库配置
 */
dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(project(":main:app-core"))
    implementation(project(":core:service:service-http:http-gg"))
    implementation(project(":core:support:support-cache"))
    implementation(project(":core:component:component-single-image-chooser"))


    //leaks 内存检测
    debugApi(Libs.RAM.leak_canary)

    //object box
    kapt(Libs.Database.objectbox_processor)
    releaseImplementation(Libs.Database.objectbox_android)
    debugImplementation(Libs.Database.objectbox_android_objectbrowser)

    api(Libs.Kotlin.stdlib_jdk7)
    api(Libs.Kotlin.rxjava)
    api(Libs.Kotlin.core_ktx)

    api(Libs.Encryption.tink_android)
}