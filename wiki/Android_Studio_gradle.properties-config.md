# gradle.properties 文件配置
涉及到本地开发环境，该文件没有纳入git版本管理控制，各成员根据需求进行配置。

## 前言
gradle.properties文件，可以对Android Studio项目，gradle编译环境，进行各种配置，合理配置有利于提高项目编译、运行效率。

#### 1、拉取新项目后，检查项目根目录下是否有gradle.properties文件，没有则自行新建
![image](uploads/5d94e1047c974a1cd3df1d02fff250fb/image.png)

#### 2、根据项目需求、电脑软硬件环境，进行合理配置。

```

#####
# Android Studio编译基本配置，可以根据自己的需求进行开启/关闭
#databinding
android.enableExperimentalFeatureDatabinding = false
androidx.databinding.enableV2=true
#Aapt2
android.enableAapt2=true
#新增编译器D8
android.enableD8=true
#配置Android Studio内存，默认注解，请根据电脑配置开启
#org.gradle.jvmargs=-Xmx8000m -XX:MaxPermSize=512m -XX:+HeapDumpOnOutOfMemoryError -Dfile.encoding=UTF-8
#守护进程
org.gradle.daemon=true
#并行编译
org.gradle.parallel=true
#开启缓存
android.enableBuildCache=true
#启用并设置 构建缓存 的目录 (使用 `./gradlew cleanBuildCache` 指令清除cache内容)
android.buildCacheDir=buildCacheDir/
#启用gradle缓存
org.gradle.caching=true
#是否开启孵化模式
org.gradle.configureondemand=false
#初次打开时，不连接google进行更新
disable.android.first.run=true
#迁移到Android X
android.useAndroidX=true
android.enableJetifier=true
#设置代理
#systemProp.http.proxyHost=www.somehost.org
#systemProp.http.proxyPort=8080
#systemProp.https.proxyHost=www.somehost.org
#systemProp.https.proxyPort=8080

```

