rootProject.buildFileName = "build.gradle.kts"
include(":core:component:component-scissor")
include(":core:widget:widget-webview")
include(":core:component:commponent-indicator")
include(":main:app-sample")
include(":main:app")
include(":main:app-core")

//支持工具包
include("core:support:support-utils")
include("core:support:support-screen")
include("core:support:support-permission")
include("core:support:support-cache")
include("core:support:support-databinding")
include("core:support:support-resources")
include("core:support:support-rxjava")
include("core:support:support-rxbus")
include("core:support:support-factory")
include("core:support:support-rx-activity-result")
include("core:support:support-version-updating")
include("core:support:support-app")
include("core:support:support-lifecycle")
include("core:support:support-compat")
include("core:support:support-lazy-helper")
include("core:support:support-recyclerview")
include("core:support:support-annotation")
include("core:support:support-download")


//网络请求服务
include("core:service:service-http:http-core")
include("core:service:service-http:http-retrofit")
include("core:service:service-http:http-use")
include("core:service:service-http:http-gg")

//支付服务
include("core:service:service-pay:pay-core")
include("core:service:service-pay:pay-alipay")
include("core:service:service-pay:pay-wx")

//logger打印服务
include("core:service:service-logger:logger-core")
include("core:service:service-logger:logger-ganguo")

//第三方登录分享服务
include("core:service:service-open:open-core")
include("core:service:service-open:open-alipay")
include("core:service:service-open:open-facebook")
include("core:service:service-open:open-google")
include("core:service:service-open:open-line")
include("core:service:service-open:open-qq")
include("core:service:service-open:open-sina")
include("core:service:service-open:open-twitter")
include("core:service:service-open:open-wechat")

//图片加载服务
include("core:service:service-image:image-core")
include("core:service:service-image:image-glide")
include("core:service:service-image:image-coil")

//自定义组件
include("core:component:component-code-scanner")
include("core:component:component-single-image-chooser")
include("core:component:component-side-bar")
include("core:component:commponent-sticky-header")
include("core:component:component-exhibition-state")
include("core:component:component-tab")
include("core:component:commponent-banner")
include("core:component:component-image-picker")


//控件
include("core:widget:widget-layout")
include("core:widget:widget-switch")
include("core:widget:widget-textview")

//mvvm架构
include("core:frame:frame-mvvm")