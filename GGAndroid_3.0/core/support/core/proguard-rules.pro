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
######### 甘果libs不混淆, 涉及到的东西比较多 #########
-dontwarn io.ganguo.image.**
-keep class io.ganguo.image.** { *; }

-dontwarn io.ganguo.library.**
-keep class io.ganguo.library.** { *; }

-dontwarn io.ganguo.opensdk.**
-keep class io.ganguo.opensdk.** { *; }

-dontwarn io.ganguo.app.gcache.**
-keep class io.ganguo.app.gcache.** { *; }

-dontwarn io.ganguo.utils.**
-keep class io.ganguo.utils.** { *; }

-dontwarn ui.**
-keep class ui.** { *; }

-dontwarn java.lang.annotation.Annotation.**
-keep public class *extends java.lang.annotation.Annotation {}