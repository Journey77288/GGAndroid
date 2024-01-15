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
    namespace = "io.ganguo.sample"
    sourceSets.getByName("main") {
        //xml命名规范：以activity、item、page、dialog等开头，放到对应的模块文件夹下
        res.setSrcDirs(
            listOf(
                "src/main/res/layouts/image",//我的模块 xml文件
                "src/main/res/layouts/widget",//我的模块 xml文件
                "src/main/res/layouts/support",//支持
                "src/main/res/layouts/component",//组件
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
    implementation(project(":core:support:support-permission"))
    implementation(project(":core:component:component-single-image-chooser"))

    //support模块
    implementation(project(":core:support:support-cache"))
    implementation(project(":core:support:support-rxbus"))
    implementation(project(":core:support:support-version-updating"))
    implementation(project(":core:support:support-download"))

    //自有封装component模块
    implementation(project(":core:component:component-single-image-chooser"))
    implementation(project(":core:component:component-image-picker"))
    implementation(project(":core:component:component-scissor"))
    implementation(project(":core:component:component-code-scanner"))
    implementation(project(":core:component:commponent-sticky-header"))
    implementation(project(":core:component:component-side-bar"))

    //自有封装widget模块
    implementation(project(":core:widget:widget-switch"))
    implementation(project(":core:widget:widget-layout"))

    //自有封装service模块
    implementation(project(":core:service:service-http:http-gg"))
    implementation(project(":core:service:service-image:image-coil"))
    implementation(project(":core:service:service-image:image-glide"))
    implementation(project(":core:service:service-http:http-gg"))
    implementation(project(":core:service:service-pay:pay-alipay"))
    implementation(project(":core:service:service-pay:pay-core"))
    implementation(project(":core:service:service-pay:pay-wx"))

    //open sdk 模块（support-factory和open-core 模块为基础依赖，必须引用）
    implementation(project(":core:support:support-factory"))
    implementation(project(":core:service:service-open:open-core"))

    //facebook sdk 模块
    implementation(project(":core:service:service-open:open-facebook"))

    //sina sdk
    implementation(project(":core:service:service-open:open-sina"))

    //alipay sdk
    implementation(project(":core:service:service-open:open-alipay"))

    //wechat sdk 模块
    implementation(project(":core:service:service-open:open-wechat"))
    implementation(Libs.Open.wechat_sdk)

    implementation(project(":core:service:service-open:open-twitter"))
    implementation(project(":core:service:service-open:open-google"))
    implementation(project(":core:service:service-open:open-line"))
    implementation(project(":core:service:service-open:open-qq"))

    //leaks 内存检测
    debugApi(Libs.RAM.leak_canary)

    //object box
    kapt(Libs.Database.objectbox_processor)
    releaseImplementation(Libs.Database.objectbox_android)
    debugImplementation(Libs.Database.objectbox_android_objectbrowser)

    implementation(Libs.Image.compressor)

    //aria 模块，内部集成的aria库需要在外部再次引入
    implementation(Libs.Android.annotations)
    implementation(Libs.Download.aria)
    kapt(Libs.Download.aria_compile)

    api(Libs.Kotlin.serialization)
    api(Libs.Kotlin.stdlib_jdk7)
    api(Libs.Kotlin.rxjava)
    api(Libs.Kotlin.core_ktx)
    api(Libs.Font.spanny)

    implementation(Libs.Widget.shimmerlayout)
    implementation(Libs.Widget.skeleton)

    api(Libs.Encryption.tink_android)

    configurations{
        all {
            exclude("androidx.lifecycle", "lifecycle-viewmodel-ktx")
        }
    }
}