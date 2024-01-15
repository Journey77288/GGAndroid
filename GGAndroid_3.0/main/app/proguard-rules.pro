# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Volumes/Data/develop/tools/android_sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
-keepattributes *Annotation*
-keepclassmembers class ** {
    @com.squareup.otto.Subscribe public *;
    @com.squareup.otto.Produce public *;
}
########### 不优化泛型和反射 ##########
-keepattributes Signature
-keepattributes InnerClasses

######### Serializable Parcelable全部不混淆 #########
-keepnames class * implements java.io.Serializable
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}
-keep class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}

######### webview中的js不混淆 #########
-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}

######### 主程序不能混淆的代码 #########
-dontwarn com.jsrs.app.bean.**
-keep class com.jsrs.app.bean.** { *; }

-dontwarn com.jsrs.app.dto.**
-keep class com.jsrs.app.dto.** { *; }

-dontwarn com.jsrs.app.entity.**
-keep class com.jsrs.app.entity.** { *; }

-dontwarn com.jsrs.app.wxapi.**
-keep class com.jsrs.app.wxapi.** { *; }

-dontwarn com.jsrs.app.BuildConfig
-keep class com.jsrs.app.BuildConfig { *; }

-dontwarn com.jsrs.app.http.HttpResponse
-keep class com.jsrs.app.http.HttpResponse { *; }

-dontwarn com.jsrs.app.http.request.**
-keep class com.jsrs.app.http.request.** { *; }

-dontwarn com.jsrs.app.http.response.**
-keep class com.jsrs.app.http.response.** { *; }

-dontwarn com.jsrs.app.**
-keep class com.jsrs.app.** { *; }


-dontwarn io.ganguo.log.**
-keep class io.ganguo.log.** { *; }
-keep interface io.ganguo.log.** { *; }


-dontwarn org.conscrypt.ConscryptHostnameVerifier.**
-keep class org.conscrypt.ConscryptHostnameVerifier.** { *; }
-dontwarn okhttp3.internal.platform.ConscryptPlatform

#####################################
######### 第三方库或者jar包 ###########
#####################################
-dontwarn android.**
-keep class android.** { *; }

-dontwarn com.android.**
-keep class com.android.** { *; }

-dontwarn android.app.**
-keep class android.app.** { *; }

-dontwarn android.content.**
-keep class android.content.** { *; }

-dontwarn android.support.**
-keep class android.support.** { *; }

-dontwarn android.os.**
-keep class android.os.** { *; }

-dontwarn androidx.databinding.**
-keep class androidx.databinding.** { *; }

-dontwarn junit.**
-keep class junit.** { *; }

-dontwarn javax.**
-keep class javax.** { *; }

-dontwarn java.**
-keep class java.** { *; }

-dontwarn de.**
-keep class de.** { *; }

-dontwarn org.**
-keep class org.** { *; }

-dontwarn uk.**
-keep class uk.** { *; }

-dontwarn bolts.**
-keep class bolts.** { *; }

-dontwarn com.squareup.**
-keep class com.squareup.** { *; }

-dontwarn okio.**
-keep class okio.** { *; }

-dontwarn retrofit2.**
-keep class retrofit2.** { *; }

-dontwarn rt.**
-keep class rt.** { *; }

-dontwarn rx.**
-keep class rx.** { *; }

-dontwarn okhttp3.**
-keep class okhttp3.** { *; }

-dontwarn com.octo.**
-keep class com.octo.** { *; }

-dontwarn u.aly.**
-keep class u.aly.** { *; }

-dontwarn com.baidu.**
-keep class com.baidu.** { *; }

-dontwarn com.facebook.**
-keep class com.facebook.** { *; }

-dontwarn com.facebook.Session
-keep class com.facebook.Session { *; }

-dontwarn com.google.**
-keep class com.google.** { *; }

-dontwarn com.afollestad.**
-keep class com.afollestad.** { *; }

-dontwarn jp.wasabeef.**
-keep class jp.wasabeef.** { *; }

-dontwarn me.imid.**
-keep class me.imid.** { *; }

-dontwarn me.nereo.**
-keep class me.nereo.** { *; }

-dontwarn org.apache.**
-keep class org.apache.** { *; }

-dontwarn uk.co.chrisjenx.**
-keep class uk.co.chrisjenx.** { *; }

-dontwarn uk.co.senab.**
-keep class uk.co.senab.** { *; }

-dontwarn taobe.tec.**
-keep class taobe.tec.** { *; }

-dontwarn me.leolin.**
-keep class me.leolin.** { *; }

-dontwarn org.jsoup.**
-keep class org.jsoup.** { *; }

-dontwarn de.hdodenhof.**
-keep class de.hdodenhof.** { *; }

-dontwarn libcore.icu.**
-keep class libcore.icu.** { *; }

-dontwarn sun.misc.**
-keep class sun.misc.** { *; }

-dontwarn com.parse.**
-keep class com.parse.** { *; }

-dontwarn com.millennialmedia.**
-keep class com.millennialmedia.** { *; }

-dontwarn com.inmobi.**
-keep class com.inmobi.** { *; }

-dontwarn com.flurry.**
-keep class com.flurry.** { *; }

-dontwarn com.umeng.**
-keep class com.umeng.** { *; }

-dontwarn cn.leancloud.**
-keep class cn.leancloud.** { *; }

-dontwarn com.github.**
-keep class com.github.** { *; }

-dontwarn com.loopj.**
-keep class com.loopj.** { *; }

-dontwarn com.mcxiaoke.**
-keep class com.mcxiaoke.** { *; }

-dontwarn com.nostra13.**
-keep class com.nostra13.** { *; }

-dontwarn com.readystatesoftware.**
-keep class com.readystatesoftware.** { *; }

-dontwarn com.kyleduo.**
-keep class com.kyleduo.** { *; }

-dontwarn com.handmark.**
-keep class com.handmark.** { *; }

-dontwarn com.nineoldandroids.**
-keep class com.nineoldandroids.** { *; }

-dontwarn cn.jpush.**
-keep class cn.jpush.** { *; }

-dontwarn cn.sharesdk.**
-keep class cn.sharesdk.** { *; }

-dontwarn com.mob.**
-keep class com.mob.** { *; }

-dontwarn com.zhy.**
-keep class com.zhy.** { *; }

-dontwarn com.qiniu.**
-keep class com.qiniu.** { *; }

-dontwarn com.sina.**
-keep class com.sina.** { *; }

-dontwarn me.zhanghai.**
-keep class me.zhanghai.** { *; }

-dontwarn com.sumsung.**
-keep class com.sumsung.** { *; }

-dontwarn com.alibaba.**
-keep class com.alibaba.** { *; }

-dontwarn com.pgyersdk.**
-keep class com.pgyersdk.** { *; }

-dontwarn java.util.**
-keep class java.util.** { *; }

-dontwarn java.lang.reflect.Constructor
-keep class java.lang.reflect.Constructor

#####################################
######### 支付相关sdk不混淆 ###########
#####################################
-dontwarn com.alipay.**
-keep class com.alipay.** {*;}

-dontwarn  com.ta.utdid2.**
-keep class com.ta.utdid2.** {*;}

-dontwarn  com.ut.device.**
-keep class com.ut.device.** {*;}

-dontwarn  com.tencent.**
-keep class com.tencent.** {*;}

-dontwarn  com.unionpay.**
-keep class com.unionpay.** {*;}

-dontwarn com.baidu.**
-keep class com.baidu.** {*;}

-dontwarn io.ganguo.paysdk.**
-keep class io.ganguo.paysdk.wechat.WXPayDTO {*;}
-keep class io.ganguo.paysdk.alipay.PayResult {*;}


##################################################
######### Parceler library相关代码不混淆 ###########
##################################################
-keep interface org.parceler.Parcel
-keep @org.parceler.Parcel class * { *; }
-keep class **$$Parcelable { *; }


##################################################
######### ARouter library相关代码不混淆 ###########
##################################################
-keep public class com.alibaba.android.arouter.routes.**{*;}
-keep public class com.alibaba.android.arouter.facade.**{*;}
-keep class * implements com.alibaba.android.arouter.facade.template.ISyringe{*;}

# 如果使用了 byType 的方式获取 Service，需添加下面规则，保护接口
-keep interface * implements com.alibaba.android.arouter.facade.template.IProvider

# 如果使用了 单类注入，即不定义接口实现 IProvider，需添加下面规则，保护实现
 -keep class * implements com.alibaba.android.arouter.facade.template.IProvider


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

#####################################
######### 二维码扫描相关不混淆 ###########
#####################################
-dontwarn  io.ganguo.scanner.**
-keep class io.ganguo.scanner.**{*;}


#####################################
######### Retrofit 代码不混淆 ###########
#####################################
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions


#####################################
######### RxJava RxAndroid 代码不混淆 ###########
#####################################
-dontwarn sun.misc.**
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
    long producerIndex;
    long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
}

#####################################
######### Gson 代码不混淆 ###########
#####################################
-keep class com.google.gson.stream.** { *; }
-keepattributes EnclosingMethod
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.**{*;}
-keep class io.ganguo.http.entity.**{*;}


#####################################
######### glide 代码不混淆 ###########
#####################################
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

#####################################
######### Push 代码不混淆 ###########
#####################################
-keep class io.ganguo.push.entity.** { *; }

#####################################
######### avi Loading 代码不混淆 ###########
#####################################
-keep class com.wang.avi.** { *; }
-keep class com.wang.avi.indicators.** { *; }


#####################################
######### 枚举类和接口不混淆 ###########
#####################################
-keepclassmembers enum * {*;}
-keepclassmembers interface * {*;}



#####################################
######### TBS WebView不混淆 ###########
#####################################
#-optimizationpasses 7
#-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
-dontoptimize
-dontusemixedcaseclassnames
-verbose
-dontskipnonpubliclibraryclasses
-dontskipnonpubliclibraryclassmembers
-dontwarn dalvik.**
-dontwarn com.tencent.smtt.**
#-overloadaggressively

# ------------------ Keep LineNumbers and properties ---------------- #
-keepattributes Exceptions,InnerClasses,Signature,Deprecated,SourceFile,LineNumberTable,*Annotation*,EnclosingMethod
# --------------------------------------------------------------------------

# Addidional for x5.sdk classes for apps

-keep class com.tencent.smtt.export.external.**{
    *;
}

-keep class com.tencent.tbs.video.interfaces.IUserStateChangedListener {
	*;
}

-keep class com.tencent.smtt.sdk.CacheManager {
	public *;
}

-keep class com.tencent.smtt.sdk.CookieManager {
	public *;
}

-keep class com.tencent.smtt.sdk.WebHistoryItem {
	public *;
}

-keep class com.tencent.smtt.sdk.WebViewDatabase {
	public *;
}

-keep class com.tencent.smtt.sdk.WebBackForwardList {
	public *;
}

-keep public class com.tencent.smtt.sdk.WebView {
	public <fields>;
	public <methods>;
}

-keep public class com.tencent.smtt.sdk.WebView$HitTestResult {
	public static final <fields>;
	public java.lang.String getExtra();
	public int getType();
}

-keep public class com.tencent.smtt.sdk.WebView$WebViewTransport {
	public <methods>;
}

-keep public class com.tencent.smtt.sdk.WebView$PictureListener {
	public <fields>;
	public <methods>;
}


-keepattributes InnerClasses

-keep public enum com.tencent.smtt.sdk.WebSettings$** {
    *;
}

-keep public enum com.tencent.smtt.sdk.QbSdk$** {
    *;
}

-keep public class com.tencent.smtt.sdk.WebSettings {
    public *;
}


-keepattributes Signature
-keep public class com.tencent.smtt.sdk.ValueCallback {
	public <fields>;
	public <methods>;
}

-keep public class com.tencent.smtt.sdk.WebViewClient {
	public <fields>;
	public <methods>;
}

-keep public class com.tencent.smtt.sdk.DownloadListener {
	public <fields>;
	public <methods>;
}

-keep public class com.tencent.smtt.sdk.WebChromeClient {
	public <fields>;
	public <methods>;
}

-keep public class com.tencent.smtt.sdk.WebChromeClient$FileChooserParams {
	public <fields>;
	public <methods>;
}

-keep class com.tencent.smtt.sdk.SystemWebChromeClient{
	public *;
}
# 1. extension interfaces should be apparent
-keep public class com.tencent.smtt.export.external.extension.interfaces.* {
	public protected *;
}

# 2. interfaces should be apparent
-keep public class com.tencent.smtt.export.external.interfaces.* {
	public protected *;
}

-keep public class com.tencent.smtt.sdk.WebViewCallbackClient {
	public protected *;
}

-keep public class com.tencent.smtt.sdk.WebStorage$QuotaUpdater {
	public <fields>;
	public <methods>;
}

-keep public class com.tencent.smtt.sdk.WebIconDatabase {
	public <fields>;
	public <methods>;
}

-keep public class com.tencent.smtt.sdk.WebStorage {
	public <fields>;
	public <methods>;
}

-keep public class com.tencent.smtt.sdk.DownloadListener {
	public <fields>;
	public <methods>;
}

-keep public class com.tencent.smtt.sdk.QbSdk {
	public <fields>;
	public <methods>;
}

-keep public class com.tencent.smtt.sdk.QbSdk$PreInitCallback {
	public <fields>;
	public <methods>;
}
-keep public class com.tencent.smtt.sdk.CookieSyncManager {
	public <fields>;
	public <methods>;
}

-keep public class com.tencent.smtt.sdk.Tbs* {
	public <fields>;
	public <methods>;
}

-keep public class com.tencent.smtt.utils.LogFileUtils {
	public <fields>;
	public <methods>;
}

-keep public class com.tencent.smtt.utils.TbsLog {
	public <fields>;
	public <methods>;
}

-keep public class com.tencent.smtt.utils.TbsLogClient {
	public <fields>;
	public <methods>;
}

-keep public class com.tencent.smtt.sdk.CookieSyncManager {
	public <fields>;
	public <methods>;
}

# Added for game demos
-keep public class com.tencent.smtt.sdk.TBSGamePlayer {
	public <fields>;
	public <methods>;
}

-keep public class com.tencent.smtt.sdk.TBSGamePlayerClient* {
	public <fields>;
	public <methods>;
}

-keep public class com.tencent.smtt.sdk.TBSGamePlayerClientExtension {
	public <fields>;
	public <methods>;
}

-keep public class com.tencent.smtt.sdk.TBSGamePlayerService* {
	public <fields>;
	public <methods>;
}

-keep public class com.tencent.smtt.utils.Apn {
	public <fields>;
	public <methods>;
}
-keep class com.tencent.smtt.** {
	*;
}
# end


-keep public class com.tencent.smtt.export.external.extension.proxy.ProxyWebViewClientExtension {
	public <fields>;
	public <methods>;
}

-keep class MTT.ThirdAppInfoNew {
	*;
}

-keep class com.tencent.mtt.MttTraceEvent {
	*;
}

# Game related
-keep public class com.tencent.smtt.gamesdk.* {
	public protected *;
}

-keep public class com.tencent.smtt.sdk.TBSGameBooter {
        public <fields>;
        public <methods>;
}

-keep public class com.tencent.smtt.sdk.TBSGameBaseActivity {
	public protected *;
}

-keep public class com.tencent.smtt.sdk.TBSGameBaseActivityProxy {
	public protected *;
}

-keep public class com.tencent.smtt.gamesdk.internal.TBSGameServiceClient {
	public *;
}
#---------------------------------------------------------------------------


#------------------  下方是android平台自带的排除项，这里不要动         ----------------

-keep public class * extends android.app.Activity{
	public <fields>;
	public <methods>;
}
-keep public class * extends android.app.Application{
	public <fields>;
	public <methods>;
}
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keepclasseswithmembers class * {
	public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {
	public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepattributes *Annotation*

-keepclasseswithmembernames class *{
	native <methods>;
}

-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

#------------------  下方是共性的排除项目         ----------------
# 方法名中含有“JNI”字符的，认定是Java Native Interface方法，自动排除
# 方法名中含有“JRI”字符的，认定是Java Reflection Interface方法，自动排除

-keepclasseswithmembers class * {
    ... *JNI*(...);
}

-keepclasseswithmembernames class * {
	... *JRI*(...);
}

-keep class **JNI* {*;}