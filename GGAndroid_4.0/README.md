# GGAndroid_4.0代码说明文档
*   项目名称： GGAndroid_4.0
*   项目背景：该项目是由甘果Android部门，长时间积淀的技术基础架构，欢迎大家提issue和建议，为该项目添砖加瓦。

### 一、开发环境配置及架构说明
*   修订日期：2020年10月20日
*   开发环境：macOS
*   开发工具：Android Studio 3.6
*   开发语言：Kotlin
*   项目架构：MVVM with Data Binding and RxJava

### 二、注意事项
因项目部分依赖需要翻墙才能编译，请编译项目前配好网络代理


### 三、MVVM简介
`Model-View-ViewModel` MVVM有助于将图形用户界面的开发与业务逻辑或后端逻辑（数据模型）的开发分离开来，它可以将数据模型、视图行为抽象分离，我们将UI层中的业务逻辑单独抽离出来。该架构引入了ViewModel模块,它负责暴露数据对象模型并且处理由任何逻辑引起的UI变化。

MVVM 由三个核心模块组成，每一个模块都是独立的并肩负其职的：

*   `Model` - 数据模型，包含业务数据和验证逻辑
*   `View` - 屏幕所展现的UI、布局等（Activity、Fragment、Xml）
*   `ViewModel` - 在View和Model之间扮演胶水的作用，处理每一个视图逻辑（UI变化）和交互逻辑

![](https://upload.wikimedia.org/wikipedia/commons/8/87/MVVMPattern.png)


### 四、MVVM 相关类说明

*   `BaseViewModel`：ViewModel 基类，生命周期与 Activity/Fragment 同步。注意 ViewModel 绑定的 View，需用 `ViewModelHelper` 绑定相关 View ，且在 onAttach() 后才绑定完成，因此需注意在 View 绑定完成之前 getContext() 或 getView()都是返回 null。
*   `ViewModelHelper`：ViewModel辅助类，主要用于ViewModel与View绑定，可通过 ViewModelHelper 将 ViewDataBinding/ViewGroup/Dialog 的布局和生命周期绑定到 ViewModel，或用 Context 初始化 ViewModel，然后手动处理 View 绑定逻辑。
*   `VMLifecycleHelper`：ViewModel生命周期辅助类，每个 ViewModel 里都有一个VMLifecycleHelper对象，用于同步 ViewModel 的生命周期。ViewModel 中的 onDestroy 方法仅销毁当前 ViewModel。若想销毁一个 ViewModel 下的所有与它绑定的子 ViewModel，需在 ViewModel 中调用`getLifecycleHelper().onDestroy()`。
*   `ViewModelActivity`：封装了同步生命周期/转发 ActivityResult 等方法的 Activity 基类，所有 Activity 都需继承它。
*   `ViewModelFragment`：封装了同步生命周期、转发 ActivityResult 和管理 Fragment 的方法，所有 Fragment 都需继承它。

<br>
### 五、Data Binding
[DataBinding官方文档](https://developer.android.com/topic/libraries/data-binding/index.html#expression_language)

`Data Binding` 是一个数据绑定框架，上面 MVVM 框架中介绍到 ViewModel 和 View 是通过数据绑定的方式连接在一起，其中数据绑定的逻辑是通过 Data binding 将 ViewModel 中的数据绑定到 View 层，可实现双向的交互，使得 View 和 ViewModel 之间的耦合程度降低。当 ViewModel 需要通知界面更新时，只需直接更新原数据，界面即可接收到数据改变后的状态，将维护界面状态的成本降到最低。


### 六、RxJava
[RxJava](https://www.gitbook.com/book/mcxiaoke/rxdocs/details) 是一个在 Java VM 上使用可观测的序列来组成异步的、基于事件的程序的库。RxJava 的异步实现，是通过一种扩展的观察者模式来实现的。


### 七、工程目录结构
#### 项目总体目录结构

```
├── build.gradle
├── core
│   ├── component  组件模块
│   │   ├── component-banner  轮播图
│   │   ├── component-indicator  ViewPager指示器组件
│   │   ├── component-sticky-header  列表粘性头部
│   │   ├── component-code-scanner  扫码
│   │   ├── component-exhibition-state  页面状态管理
│   │   ├── component-image-picker  图片多选
│   │   ├── component-scissor  图片裁剪
│   │   ├── component-side-bar  列表索引侧栏组件
│   │   ├── component-single-image-chooser  图片单选
│   │   └── component-tab  Tab栏组件
│   ├── frame
│   │   └── frame-mvvm  mvvm框架核心代码
│   ├── service  服务模块
│   │   ├── service-http 网络请求服务
│   │   ├── service-image 图片加载服务
│   │   ├── service-logger 日志打印
│   │   ├── service-open 第三方分享/登录
│   │   └── service-pay 第三方支付
│   ├── support 支持包模块
│   │   ├── support-annotation  自定义注解包
│   │   ├── support-app  app基础支持包
│   │   ├── support-cache  缓存工具包
│   │   ├── support-compat 兼容包
│   │   ├── support-databinding databindg工具包
│   │   ├── support-download 文件下载工具包
│   │   ├── support-factory
│   │   ├── support-lazy-helper 懒加载工具包
│   │   ├── support-lifecycle  页面生命周期管理工具包
│   │   ├── support-permission 权限请求工具包
│   │   ├── support-recyclerview  adapter 二次封装，方便多样式实现
│   │   ├── support-resources  资源操作工具包
│   │   ├── support-rx-activity-result  基于RxJava封装，ActivityResult工具包
│   │   ├── support-rxbus  RxBus工具（替代EventBus）
│   │   ├── support-rxjava RxJava支持工具包
│   │   ├── support-screen 屏幕适配工具包
│   │   ├── support-utils  常用工具包
│   │   └── support-version-updating 版本更新工具包
│   └── widget
│       ├── widget-layout  自定义ViewGroup
│       ├── widget-switch  自定义SwitchButton
│       ├── widget-textview  自定义TextView
│       └── widget-webview  自定义WebView
├── gradle
│   ├── config
│       ├── app-core.gradle  AppModule通用配置
│       ├── environment.gradle   LibraryModule通用配置
│       ├── keystore 打包签名文件配置
│       └── module-core.gradle 通用核心基础Module配置
├── buildSrc
│   └── src
|       └── com
|           └── google
|               └── gradle
|                   └── DependenceManager.kt  依赖库集中管理配置
├── main
│   ├── app  应用源码Module
│   └── app-core 基于frame-mvvm封装，复用及核心框架模块
└── settings.gradle Module配置文件

```


#### 应用源码目录结构

```
├── build.gradle  基本依赖配置
├── flavors.gradle  多渠道与环境参数配置
├── objectbox-models  数据库模型（自动生成，不用管）
├── proguard-rules.pro  代码混淆配置文件
└── src
    ├── main
    │   ├── AndroidManifest.xml
    │   ├── kotlin
    │   │   └── com
    │   │       └── oneplus
    │   │           ├── mall
    │   │           │   ├── AppEnvironment.kt  对应gradle文件中的基本环境配置
    │   │           │   ├── adapter
    │   │           │   ├── bean  基本常量
    │   │           │   │   └── analytics
    │   │           │   ├── callback 回调接口
    │   │           │   ├── context  
    │   │           │   │   ├── AppContext.kt
    │   │           │   │   └── initializer 依赖库/模块初始化
    │   │           │   ├── database  数据库表
    │   │           │   ├── databinding databinding工具包
    │   │           │   ├── decoration  recyclerView分割线
    │   │           │   ├── entity  基本实体类
    │   │           │   ├── enums  枚举包
    │   │           │   │   ├── MemberLevel.kt
    │   │           │   │   ├── MessageStyle.kt
    │   │           │   │   └── TopicModuleType.kt
    │   │           │   ├── helper  辅助包
    │   │           │   │   ├── AppVersionHelper.kt
    │   │           │   │   ├── MessageHelper.kt
    │   │           │   │   ├── OnePlusLoadingHelper.kt
    │   │           │   │   ├── ScrollCalculatorHelper.kt
    │   │           │   │   ├── UserFromH5Helper.kt
    │   │           │   │   └── security
    │   │           │   ├── http  网络请求包
    │   │           │   │   ├── api
    │   │           │   │   ├── domain
    │   │           │   │   ├── error
    │   │           │   │   ├── helper
    │   │           │   │   ├── interceptor 拦截器
    │   │           │   │   ├── processor 自定义网络请求相关数据处理器
    │   │           │   │   ├── request  网络请求实体类
    │   │           │   │   └── response 接口数据实体类
    │   │           │   ├── layoutManager
    │   │           │   │   ├── MaxHeightGridLayoutManager.kt
    │   │           │   │   └── MaxHeightLinearLayoutManager.kt
    │   │           │   ├── lifecycler
    │   │           │   │   └── AppActiveTimeObserver.kt
    │   │           │   ├── receiver 
    │   │           │   │   ├── MemberSdkReceiver.kt  会员sdk广播接收
    │   │           │   │   └── NotificationReceiver.kt 消息推送广播接收
    │   │           │   ├── repository
    │   │           │   │   └── LocalUser.kt  用户本地信息工具类
    │   │           │   ├── service
    │   │           │   │   └── PushService.kt  推送服务
    │   │           │   ├── spanSizeLookup
    │   │           │   │   └── StoreSpanSizeLookup.kt
    │   │           │   ├── timer
    │   │           │   │   └── StoreSuprpriseCountDownTimer.kt
    │   │           │   ├── util  常用工具包
    │   │           │   │   ├── account  账号模块工具包
    │   │           │   │   ├── cache  缓存模块工具包
    │   │           │   │   ├── font  字体工具包
    │   │           │   │   ├── jsbridge  H5与原生接口实现，工具包
    │   │           │   │   └── rx   RxJava工具包
    │   │           │   ├── view  View层（Activity/Dialog/Fragment..）
    │   │           │   │   ├── account 账号模块
    │   │           │   │   ├── discover 话题模块
    │   │           │   │   ├── store 商城模块
    │   │           │   │   └── support  服务与支持模块
    │   │           │   ├── viewmodel model层
    │   │           │   │   ├── account 账号模块
    │   │           │   │   ├── discover 话题模块
    │   │           │   │   ├── store 商城模块
    │   │           │   │   └── support 服务于支持模块
    │   │           │   └── widget 自定义控件
    │   │           └── siren
    │   │               └── alita
    │   │                   └── OnePlusAlitaStore.java  该接口用于获取so包中的密钥及其他敏感数据
    │   └── res
    │       ├── font  字体，暂时只放了一加字体
    │       ├── layout
    │       │   ├── account 账号模块布局
    │       │   ├── discover 话题模块布局
    │       │   ├── store 商城模块布局
    │       │   ├── support 服务与支持模块布局
    │       ├── raw
    │       │   ├── loading_anim.json loading动画文件
    │       │   ├── tab_account_anim.json account tab栏icon文件
    │       │   ├── tab_discover_anim.json discover tab栏icon文件
    │       │   ├── tab_store_anim.json store tab栏icon文件
    │       │   └── tab_support_anim.json support tab栏icon文件
    │       ├── value-v26
    │       ├── values
    │       ├── values-v26
    │       ├── values-v27
    │       └── xml
    │           └── network_security_config.xml   网络请求证书配置
    └── official
        └── google-services.json  上线环境，谷歌平台应用对应配置文件


```


### 八、部署 App 相关的 Gradle 命令
> 各环境配置请查看：app -> flavors.gradle文件
> gradlew 为 mac 环境命令，gradlew.bat 为 Windows 环境下的命令，可在项目根目录找到对应文件。
打包后的 Apk 文件将位于 `.../app/build/outputs/apk` 目录下

#### 常用命令
*   `./gradlew clean` 清除项目 gradle 缓存和 gradle output 文件，建议每次打包前执行一次清除缓存操作
*   `./gradlew assembleChannelNameRelease` 打包指定 ChannelName 的 Release 包
*   `./gradlew assembleChannelNameDebug` 打包指定 ChannelName 的 Debug 包
*   `./gradlew clean assembleMobileAppReleaseChannels` 美团打包工具命令，此命令将把 Channel 定义的所有渠道全部打包
*   `./gradlew :app:createMapping` 生成 Bugly 所有渠道包的上传混淆规则命令，对应文件将生成在项目根目录下的 mapping 文件夹

