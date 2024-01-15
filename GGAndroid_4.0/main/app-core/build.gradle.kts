import com.google.gradle.Libs

plugins {
    id("com.didi.dokit")
    id("com.android.library")
    kotlin("android")
    kotlin("plugin.serialization")
}

apply(from = "${rootProject.projectDir}/gradle/config/module-core.gradle")

android {
    namespace = "io.ganguo.core"
    /**
     * 资源目录配置
     */
    sourceSets.getByName("main") {
        //xml命名规范：按控件级别命名。(此处规范仅针对app-core)
        res.setSrcDirs(
            listOf(
                "src/main/res/layouts/widget",//单个控件
                "src/main/res/layouts/frame",//页面基础架构
                "src/main/res/layouts/component",//组件
                "src/main/res/layouts/container",//viewGroup布局
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
    api(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    api(project(":core:frame:frame-mvvm"))

    api(project(":core:component:component-exhibition-state"))
    api(project(":core:component:commponent-banner"))
    api(project(":core:component:commponent-indicator"))
    api(project(":core:component:component-tab"))

    api(project(":core:service:service-http:http-core"))
    api(project(":core:service:service-logger:logger-ganguo"))
    api(project(":core:service:service-image:image-glide"))
    api(project(":core:service:service-image:image-coil"))
    api(project(":core:service:service-http:http-retrofit"))

    api(project(":core:support:support-databinding"))
    api(project(":core:support:support-cache"))
    api(project(":core:support:support-compat"))
    api(project(":core:support:support-permission"))
    api(project(":core:support:support-screen"))
    api(project(":core:support:support-app"))
    api(project(":core:support:support-rxjava"))
    api(project(":core:support:support-lifecycle"))
    api(project(":core:support:support-resources"))
    api(project(":core:support:support-utils"))
    api(project(":core:support:support-lazy-helper"))
    api(project(":core:support:support-annotation"))

    api(project(":core:widget:widget-layout"))
    api(project(":core:widget:widget-webview"))


    api(Libs.Android.recyclerview)
    api(Libs.Widget.recyclerview_animators)
    api(Libs.Kotlin.coroutines_core)
    api(Libs.Kotlin.coroutines_android)
    api(Libs.Kotlin.serialization)
    api(Libs.Common.utilcode)
    api(Libs.Android.viewpager2)
    api(Libs.Android.cardview)

    debugApi(Libs.Debug.dokit)
    releaseApi(Libs.Debug.dokit_no_op)
}


