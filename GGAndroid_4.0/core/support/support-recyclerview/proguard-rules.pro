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

#下面代码中的路径配置,你要修改成与你相对应的路径

#引入依赖包rt.jar（jdk路径）(注意：如在makeJar的时候提示指定了两次，可以将其注释掉)
#-libraryjars 'C:\Program Files\Java\jdk1.8.0_101\jre\lib\rt.jar'

#引入依赖包android.jar(android SDK路径)(注意：如在makeJar的时候提示指定了两次，可以将其注释掉)
#-libraryjars 'C:\Android_Develop_Tools\sdk\platforms\android-23\android.jar'

#如果用到Appcompat包，需要引入(注意：如在makeJar的时候提示指定了两次，可以将其注释掉)
#-libraryjars 'D:\AndroidStudioProjects\MyApplication\mylibrary\build\intermediates\exploded-aar\com.android.support\appcompat-v7\23.4.0\jars\classes.jar'
#-libraryjars 'D:\AndroidStudioProjects\MyApplication\mylibrary\build\intermediates\exploded-aar\com.android.support\support-v4\23.4.0\jars\classes.jar'

#忽略警告
-ignorewarnings
#noinspection ShrinkerUnresolvedReference

#不要压缩(这个必须，因为开启混淆的时候 默认 会把没有被调用的代码 全都排除掉)
-dontshrink

#保护泛型 如果混淆报错建议关掉
#-keepattributes Signature

#保持BuildConfig不被混淆(因为混淆之后就无法在导出jar时排除该类)
-keep class com.ywj.mylibrary.BuildConfig{
    public *;
}
#保持特定类不被混淆
-keep class com.ywj.mylibrary.Hehe{
    public *;
}

#保持jar包里的类不被混淆
-keep class com.google.gson.**

##########################################################################默认配置
# 代码混淆压缩比，在0~7之间，默认为5，一般不做修改
-optimizationpasses 5

# 混合时不使用大小写混合，混合后的类名为小写
-dontusemixedcaseclassnames

# 指定不去忽略非公共库的类
-dontskipnonpubliclibraryclasses

# 这句话能够使我们的项目混淆后产生映射文件
# 包含有类名->混淆后类名的映射关系
-verbose

# 指定不去忽略非公共库的类成员
-dontskipnonpubliclibraryclassmembers

# 不做预校验，preverify是proguard的四个步骤之一，Android不需要preverify，去掉这一步能够加快混淆速度。
-dontpreverify

# 保留Annotation不混淆
-keepattributes *Annotation*,InnerClasses

# 避免混淆泛型
-keepattributes Signature

# 抛出异常时保留代码行号
-keepattributes SourceFile,LineNumberTable

# 指定混淆是采用的算法，后面的参数是一个过滤器
# 这个过滤器是谷歌推荐的算法，一般不做更改
-optimizations !code/simplification/cast,!field/*,!class/merging/*


#############################################
#
# Android开发中一些需要保留的公共部分
#
#############################################

-keepattributes *Annotation*
-keep public class com.droidyue.com.widget.* { *; }
-keepclassmembers class * {
    @com.squareup.otto.Subscribe.* public *;
    @com.squareup.otto.Produce.* public *;
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
}

######### webview中的js不混淆 #########
-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}

######### 主程序不能混淆的代码 #########
-dontwarn io.ganguo.log.**
-keep class io.ganguo.log.* { *; }
-keep interface io.ganguo.log.* { *; }


-dontwarn org.conscrypt.ConscryptHostnameVerifier.**
-keep class org.conscrypt.ConscryptHostnameVerifier.* { *; }
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

-dontwarn rx.**
-keep class rx.** { *; }

-dontwarn okhttp3.**
-keep class okhttp3.** { *; }

-dontwarn com.octo.**
-keep class com.octo.** { *; }

-dontwarn u.aly.**
-keep class u.aly.** { *; }


-dontwarn com.facebook.**
-keep class com.facebook.** { *; }

-dontwarn com.facebook.Session
-keep class com.facebook.Session.** { *; }

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
-keep class uk.co.chrisjenx.**{ *; }

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


-dontwarn java.lang.reflect.Constructor.**
#noinspection ShrinkerInnerClassSeparator
-keep class java.lang.reflect.Constructor.**{}

#####################################
######## GsyPlayer sdk不混淆 #########
#####################################
-keep class com.shuyu.gsyvideoplayer.video.** { *; }
-dontwarn com.shuyu.gsyvideoplayer.video.**
-keep class com.shuyu.gsyvideoplayer.video.base.** { *; }
-dontwarn com.shuyu.gsyvideoplayer.video.base.**
-keep class com.shuyu.gsyvideoplayer.utils.** { *; }
-dontwarn com.shuyu.gsyvideoplayer.utils.**
-keep class tv.danmaku.ijk.** { *; }
-dontwarn tv.danmaku.ijk.**

-keep public class * extends android.view.View{
    *** get*();
    void set*(***);
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}


##################################################
######### Parceler library相关代码不混淆 ###########
##################################################
-keep interface org.parceler.Parcel.**
-keep @org.parceler.Parcel.** class * { *; }
-keep class *$$Parcelable { *; }


 -keepattributes *Annotation*
 -keepattributes Signature
 -dontwarn android.net.SSLCertificateSocketFactory
 -dontwarn android.app.Notification
 -dontwarn com.squareup.**
 -dontwarn okhttp3.internal.platform.**
 -dontwarn okio.**
 -dontwarn android.net.**
 -keep class android.net.SSLCertificateSocketFactory{*;}




#####################################
######### 二维码扫描相关不混淆 ###########
#####################################
-dontwarn  io.ganguo.scanner.**
-keep class io.ganguo.scanner.** {*;}


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
-keepclassmembers class rx.internal.util.unsafe.**ArrayQueue*Field* {
    long producerIndex;
    long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef.* {
    rx.internal.util.atomic.LinkedQueueNode.* producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef.* {
    rx.internal.util.atomic.LinkedQueueNode.* consumerNode;
}

#####################################
######### Gson 代码不混淆 ###########
#####################################
-keep class com.google.gson.stream.** { *; }
-keepattributes EnclosingMethod
-keep class sun.misc.Unsafe.** { *; }
-keep class com.google.gson.** {*;}
-keep class io.ganguo.http.gg.response.**{*;}


#####################################
######### glide 代码不混淆 ###########
#####################################
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep class * extends com.bumptech.glide.module.AppGlideModule {
 <init>(...);
}
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
-keep class com.bumptech.glide.load.data.ParcelFileDescriptorRewinder$InternalRewinder* {
  *** rewind();
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
-keepclassmembers enum ** {*;}
-keepclassmembers interface ** {*;}



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
    public static *[] values();
    public static * valueOf(java.lang.String);
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
}

#------------------  下方是共性的排除项目         ----------------
# 方法名中含有“JNI”字符的，认定是Java Native Interface方法，自动排除
# 方法名中含有“JRI”字符的，认定是Java Reflection Interface方法，自动排除

-keepclasseswithmembers class * {
     *JNI*(...);
}

-keepclasseswithmembernames class * {
	 *JRI*(...);
}

-keep class *JNI* {*;}

-dontwarn com.oneplus.mall.**
-keep class com.oneplus.mall.http.response.** { *; }
-keep class com.oneplus.mall.http.request.** { *; }
-keep class com.oneplus.mall.entity.** { *; }
-keep class com.oneplus.mall.util.account.style.** { *; }
-keep class com.oneplus.mall.util.account.style.theme.** { *; }
-keep class com.oneplus.mall.http.error.Errors.** { *; }


-dontwarn androidx.**
-keep class androidx.** { *; }
-keep class androidx.recyclerview.widget.** { *; }
-keep class androidx.annotation.** { *; }
-keep class androidx.databinding.** { *; }
-keep class androidx.lifecycle.** { *; }
-keep class io.support.recyclerview.manager.** { *; }

-dontwarn io.ganguo.lifecycle.**
-keep class io.ganguo.lifecycle.** { *; }
-keep class io.ganguo.lifecycle.helper.**{*;}
-keep class io.ganguo.lifecycle.observer.*{*;}

-dontwarn com.airbnb.lottie.**
-keep class com.airbnb.lottie.** { *; }

-dontwarn io.ganguo.lifecycle.**
-keep class io.ganguo.lifecycle.** { *; }
-keep class io.ganguo.lifecycle.helper.**{*;}
-keep class io.ganguo.lifecycle.observer.**{*;}

-dontwarn io.reactivex.rxjava3.**
-keep class io.reactivex.rxjava3.** { *; }


#------------------  JavascriptInterface相关类不混淆  ----------------
# 方法名中含有“JNI”字符的，认定是Java Native Interface方法，自动排除
# 方法名中含有“JRI”字符的，认定是Java Reflection Interface方法，自动排除

#保留annotation， 例如 @JavascriptInterface 等 annotation
-keepattributes *Annotation*
#保留跟 javascript相关的属性
-keepattributes JavascriptInterface
#保留跟 RawRes相关的属性
-keepattributes RawRes
#保留JavascriptInterface中的方法
-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}
-keep class com.oneplus.mall.util.jsbridge.OnePlusClientUserJS { *; }
-keepclassmembers public class com.oneplus.mall.util.jsbridge.OnePlusClientUserJS {
   <fields>;
   <methods>;
   public *;
   private *;
}

-keep class com.oneplus.mall.util.jsbridge.OnePlusClientUiJS { *; }
-keepclassmembers public class com.oneplus.mall.util.jsbridge.OnePlusClientUiJS {
   <fields>;
   <methods>;
   public *;
   private *;
}

-keep class com.oneplus.mall.util.jsbridge.OnePlusClientBaseJS { *; }
-keepclassmembers public class com.oneplus.mall.util.jsbridge.OnePlusClientBaseJS {
   <fields>;
   <methods>;
   public *;
   private *;
}

-keep class com.oneplus.mall.util.jsbridge.OnePlusClientStoreJS { *; }
-keepclassmembers public class com.oneplus.mall.util.jsbridge.OnePlusClientStoreJS {
   <fields>;
   <methods>;
   public *;
   private *;
}

-keep class com.oneplus.mall.util.jsbridge.OnePlusClientDeviceJS { *; }
-keepclassmembers public class com.oneplus.mall.util.jsbridge.OnePlusClientDeviceJS {
   <fields>;
   <methods>;
   public *;
   private *;
}

-keep class com.oneplus.mall.util.jsbridge.OnePlusClientErrorJS { *; }
-keepclassmembers public class com.oneplus.mall.util.jsbridge.OnePlusClientErrorJS {
   <fields>;
   <methods>;
   public *;
   private *;
}

-keep class com.oneplus.mall.util.ToastHelperKt { *; }


-dontwarn kotlin.**
-keep class kotlin.** { *; }

#####################################
######### 会员Sdk 代码不混淆 ##########
#####################################
-dontwarn com.oneplus.membership.sdk.**
-keep class com.oneplus.membership.sdk.** { *; }


#####################################
######### keyStore数据不混淆 ##########
#####################################
-dontwarn android.security.**
-keep class android.security.keystore.** { *; }
