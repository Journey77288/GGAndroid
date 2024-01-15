package com.google.gradle

/**
 * 库版本
 */
object Versions {
    const val retrofit_version = "2.9.0"
    const val okhttp_version = "4.6.0"
    const val junit_version = "4.12"
    const val robolectric_version = "4.3.1"
    const val espresso_version = "3.0.2"
    const val mockito = "2.21.0"
    const val dokit_version = "3.5.0.1"
    const val sina_version = "12.5.0"
    const val wechat_version = "6.8.0"
    const val facebook_version = "13.0.0"
    const val glide_version = "4.16.0"
    const val coil_version = "2.5.0"
    const val smart_refresh = "2.0.3"
    const val kotlin_coroutines = "1.7.3"
    const val objectbox_version = "3.2.1"
    const val tinypinyin_version = "2.0.3"
    const val aria_version = "3.8.16"
}

/**
 * 依赖库配置管理
 */
object Libs {
    /**
     * AndroidX 基础库
     */
    object Android {
        const val v4 = "androidx.legacy:legacy-support-v4:1.0.0"
        const val appcompat = "androidx.appcompat:appcompat:1.4.1"
        const val core = "androidx.core:core:1.12.0"
        const val fragment = "androidx.fragment:fragment-ktx:1.4.1"
        const val browser = "androidx.browser:browser:1.4.0"
        const val recyclerview = "androidx.recyclerview:recyclerview:1.2.1"
        const val recyclerview_selection = "androidx.recyclerview:recyclerview-selection:1.1.0"
        const val annotations = "androidx.annotation:annotation:1.3.0"
        const val cardview = "androidx.cardview:cardview:1.0.0"
        const val constrainLayout = "androidx.constraintlayout:constraintlayout:2.1.3"
        const val multidex = "androidx.multidex:multidex:2.0.1"
        const val lifecycle_extensions = "androidx.lifecycle:lifecycle-extensions:2.2.0"
        const val lifecycle_runtime = "androidx.lifecycle:lifecycle-runtime:2.2.0"
        const val material_design = "com.google.android.material:material:1.5.0"
        const val viewpager2 = "androidx.viewpager2:viewpager2:1.0.0"
        const val webkit = "androidx.webkit:webkit:1.4.0"
        const val security_crypto = "androidx.security:security-crypto:1.0.0"
        const val test = "androidx.test:runner:1.4.0"
    }

    /**
     * 内存相关库
     */
    object RAM {
        /**
         * LeakCanary内存泄漏检测库
         */
        const val leak_canary = "com.squareup.leakcanary:leakcanary-android:2.8.1"
    }

    /**
     * 网络请求库
     */
    object Network {
        const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit_version}"
        const val retrofit_converter_kotlin = "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:1.0.0"
        const val retrofit_adapter_rxjava = "com.squareup.retrofit2:adapter-rxjava3:${Versions.retrofit_version}"
        const val okhttp_logging = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp_version}"
        const val okhttpprofiler = "com.itkacher.okhttpprofiler:okhttpprofiler:1.0.7"
        const val okhttp = "com.squareup.okhttp3:okhttp:${Versions.okhttp_version}"
        const val gson = "com.google.code.gson:gson:2.10.1"
        const val stetho = "com.facebook.stetho:stetho:1.5.1"
        const val stetho_okhttp = "com.facebook.stetho:stetho-okhttp3:1.5.1"
        const val squareup_okio = "com.squareup.okio:okio:2.4.1"
        const val chucker = "com.github.ChuckerTeam.Chucker:library:3.5.2"
        const val chucker_library_no_op = "com.github.ChuckerTeam.Chucker:library-no-op:3.5.2"
        const val conscrypt = "org.conscrypt:conscrypt-android:2.4.0"
    }

    /**
     * 控件库
     */
    object Widget {
        const val systembartint = "com.readystatesoftware.systembartint:systembartint:1.0.4"
        const val rounded_image_view = "com.makeramen:roundedimageview:2.3.0"
        const val gpuimage_library = "jp.co.cyberagent.android.gpuimage:gpuimage-library:1.4.1"
        const val shimmerlayout = "io.supercharge:shimmerlayout:2.1.0"
        const val google_zxing = "com.google.zxing:core:3.4.1"
        const val recyclerview_animators = "jp.wasabeef:recyclerview-animators:2.3.0"
        const val scale_imageview = "com.davemorrissey.labs:subsampling-scale-image-view-androidx:3.10.0"
        const val photoview = "com.github.chrisbanes:PhotoView:2.3.0"
        const val html_textview = "org.sufficientlysecure:html-textview:3.6"
        const val pick_view = "com.contrarywind:Android-PickerView:4.1.9"
        const val cameraview = "com.otaliastudios:cameraview:2.6.2"// 相机控件
        const val drawabletoolbox = "com.github.duanhong169:drawabletoolbox:1.0.7"// Drawable库
        const val skeleton = "com.ethanhua:skeleton:1.1.2"// 骨架图
    }

    /**
     * 下拉刷新库
     */
    object SmartRefresh {
        const val layout = "com.scwang.smart:refresh-layout-kernel:${Versions.smart_refresh}"// 核心必须依赖
        const val header_classics = "com.scwang.smart:refresh-header-classics:${Versions.smart_refresh}"// 经典刷新头
        const val header_material = "com.scwang.smart:refresh-header-material:${Versions.smart_refresh}"// 谷歌刷新头
        const val footer_classics = "com.scwang.smart:refresh-footer-classics:${Versions.smart_refresh}"// 经典加载
    }


    /**
     * 图片加载库
     */
    object Image {
        const val picasso = "com.squareup.picasso:picasso:2.8"
        const val picasso_transformations = "jp.wasabeef:picasso-transformations:2.4.0"
        const val glide = "com.github.bumptech.glide:glide:${Versions.glide_version}"
        const val glide_annotation_processor = "com.github.bumptech.glide:compiler:${Versions.glide_version}"
        const val glide_recyclerview_integration = "com.github.bumptech.glide:recyclerview-integration:${Versions.glide_version}@aar"
        const val glide_transformations = "jp.wasabeef:glide-transformations:4.3.0"
        const val coil = "io.coil-kt:coil:${Versions.coil_version}"
        const val coil_base = "io.coil-kt:coil-base:${Versions.coil_version}"
        const val coil_video = "io.coil-kt:coil-video:${Versions.coil_version}"
        const val coil_svg = "io.coil-kt:coil-svg:${Versions.coil_version}"
        const val coil_gif = "io.coil-kt:coil-gif:${Versions.coil_version}"
        const val compressor = "id.zelory:compressor:3.0.1"
    }

    /**
     * 视频库
     */
    object Video {
        const val video_compress = "com.github.yellowcath:VideoProcessor:2.4.2"// 视频压缩
    }

    /**
     * 数据库
     */
    object Database {
        const val objectbox_processor = "io.objectbox:objectbox-processor:${Versions.objectbox_version}"
        const val objectbox_kotlin = "io.objectbox:objectbox-kotlin:${Versions.objectbox_version}"
        const val objectbox_android_objectbrowser = "io.objectbox:objectbox-android-objectbrowser:${Versions.objectbox_version}"
        const val objectbox_android = "io.objectbox:objectbox-android:${Versions.objectbox_version}"
        const val objectbox_rxjava = "io.objectbox:objectbox-rxjava:${Versions.objectbox_version}"
        const val mmkv = "com.tencent:mmkv:1.2.14"
    }


    /**
     * 测试库
     */
    object Test {
        const val junit = "junit:junit:${Versions.junit_version}"
        const val mockito = "org.mockito:mockito-core:${Versions.mockito}"
        const val robolectric = "org.robolectric:robolectric:${Versions.robolectric_version}"
        const val espresso = "com.android.support.test.espresso:espresso-core:${Versions.espresso_version}"
    }


    /**
     * 调试库
     */
    object Debug {
        // 多功能调试库
        const val dokit = "io.github.didi.dokit:dokitx:${Versions.dokit_version}"
        const val dokit_no_op = "io.github.didi.dokit:dokitx-no-op:${Versions.dokit_version}"
    }


    /**
     * 第三方库
     */
    object Open {
        const val sina_sdk = "io.github.sinaweibosdk:core:${Versions.sina_version}@aar"
        const val wechat_sdk = "com.tencent.mm.opensdk:wechat-sdk-android-without-mta:${Versions.wechat_version}"
        const val facebook_core = "com.facebook.android:facebook-core:${Versions.facebook_version}"
        const val facebook_login = "com.facebook.android:facebook-login:${Versions.facebook_version}"
        const val facebook_share = "com.facebook.android:facebook-share:${Versions.facebook_version}"
        const val twitter_sdk = "com.twitter.sdk.android:twitter-core:3.3.0@aar"
        const val tweet_composer = "com.twitter.sdk.android:tweet-composer:3.3.0@aar"
        const val tweet_ui = "com.twitter.sdk.android:tweet-ui:3.3.0@aar"
        const val google_play_services_auth = "com.google.android.gms:play-services-auth:19.2.0"
    }


    /**
     * RxJava相关库
     */
    object Rx {
        const val java = "io.reactivex.rxjava3:rxjava:3.1.5"
        const val android = "io.reactivex.rxjava3:rxandroid:3.0.0"
        const val java3_bridge = "com.github.akarnokd:rxjava3-bridge:3.0.2"
    }


    /**
     * Kotlin库
     */
    object Kotlin {
        const val serialization = "org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.1"
        const val coroutines_core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlin_coroutines}"
        const val coroutines_android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.kotlin_coroutines}"
        const val stdlib_jdk7 = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.9.21"
        const val rxjava = "io.reactivex.rxjava3:rxkotlin:3.0.1"
        const val core_ktx = "androidx.core:core-ktx:1.12.0"
    }


    /**
     * 权限请求库
     */
    object Permission {
        const val permissionx = "com.guolindev.permissionx:permissionx:1.7.1"
    }


    /**
     * 推送库
     */
    object Push {
        //极光推送
        const val jiguang_jpush = "cn.jiguang.sdk:jpush:3.3.4"
        const val jiguang_jcore = "cn.jiguang.sdk:jcore:2.1.0"
    }


    /**
     * 异常日志库
     */
    object Exception {
        const val bugly = "com.tencent.bugly:crashreport:4.1.9"
    }


    /**
     * 文字库
     */
    object Font {
        const val spanny = "com.binaryfork:spanny:1.0.4"
        const val tinypinyin = "com.github.promeg:tinypinyin:${Versions.tinypinyin_version}"
        const val tinypinyin_android = "com.github.promeg:tinypinyin-lexicons-android-cncity:${Versions.tinypinyin_version}"
    }


    /**
     * 多语言库
     */
    object Language {
        const val lingver = "com.github.YarikSOffice:lingver:1.3.0"// 多语言处理
    }


    /**
     * 第三方WebView库
     */
    object WebView {
        const val tbs_sdk = "com.tencent.tbs.tbssdk:sdk:43903"
    }


    /**
     * 工具类库
     */
    object Common {
        const val utilcode = "com.blankj:utilcodex:1.31.1"
    }


    /**
     * 安全加密库
     */
    object Encryption {
        const val tink_android = "com.google.crypto.tink:tink-android:1.4.0"
    }

    /**
     * 下载库
     */
    object Download {
        const val aria = "me.laoyuyu.aria:core:${Versions.aria_version}"
        const val aria_compile = "me.laoyuyu.aria:compiler:${Versions.aria_version}"
        const val aria_ftp = "me.laoyuyu.aria:ftp:${Versions.aria_version}"
        const val aria_sftp = "me.laoyuyu.aria:sftp:${Versions.aria_version}"
        const val aria_m3u8 = "me.laoyuyu.aria:m3u8:${Versions.aria_version}"
    }
}