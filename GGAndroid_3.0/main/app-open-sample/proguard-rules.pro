# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
 ##################################################
 ######### 支付宝 library相关代码不混淆 ###########
 ##################################################
-keep class com.alipay.android.app.IAlixPay{*;}
-keep class com.alipay.android.app.IAlixPay$Stub{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback$Stub{*;}
-keep class com.alipay.sdk.app.PayTask{ public *;}
-keep class com.alipay.sdk.app.AuthTask{ public *;}
-keep class com.alipay.sdk.app.H5PayCallback {
    <fields>;
    <methods>;
}
-keep class com.alipay.android.phone.mrpc.core.** { *; }
-keep class com.alipay.apmobilesecuritysdk.** { *; }
-keep class com.alipay.mobile.framework.service.annotation.** { *; }
-keep class com.alipay.mobilesecuritysdk.face.** { *; }
-keep class com.alipay.tscenter.biz.rpc.** { *; }
-keep class org.json.alipay.** { *; }
-keep class com.alipay.tscenter.** { *; }
-keep class com.ta.utdid2.** { *;}
-keep class com.ut.device.** { *;}
-keep class com.ut.device.** { *;}
-keep class com.alipay.share.** { *;}
-keepattributes *Annotation*
-keepattributes Signature
-dontwarn android.net.SSLCertificateSocketFactory
-dontwarn android.app.Notification
-dontwarn com.squareup.**
-dontwarn okhttp3.internal.platform.*
-dontwarn okio.**
-dontwarn android.net.**
-keep class android.net.SSLCertificateSocketFactory{*;}

 ##################################################
 ######### 新浪微博 library相关代码不混淆 ###########
 ##################################################
-dontwarn com.sina.weibo.**
-keep class com.sina.weibo.** { *; }
-dontwarn com.sina.weibo.sdk.api.**
-keep class com.sina.weibo.sdk.api.** { *; }

 ##################################################
 ######### 甘果支付 library相关代码不混淆 ###########
 ##################################################
-keep class PayResult{*;}


#####################################
######### 微信支付 sdk相关代码不混淆 ###########
#####################################
-keep class io.ganguo.pay.wxpay.WXPayData{*;}
-dontwarn  com.tencent.**
-keep class com.tencent.** {*;}
-keep class * extends android.app.Dialog