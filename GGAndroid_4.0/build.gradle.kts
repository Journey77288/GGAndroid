val properties = rootProject.properties["local"]
buildscript {

    val kotlinVersion = "1.9.21"
    val objectBoxVersion = "3.2.1"
    val buildToolsVersion = "8.1.0"
    val dokitVersion = "3.5.0.1"
    repositories {
        // 国内maven库，优化墙内访问google maven速度
        maven { setUrl("https://maven.aliyun.com/repository/google") }
        maven { setUrl("https://maven.aliyun.com/repository/public") }
        maven { setUrl("https://maven.aliyun.com/repository/gradle-plugin") }

        google()
        mavenCentral()
        mavenLocal()

        maven { setUrl("https://www.jitpack.io") }
        maven { setUrl("https://plugins.gradle.org/m2/") }
        maven { setUrl("https://mvnrepository.com/artifact/org.robolectric/robolectric") }
        maven { setUrl("https://repo.maven.apache.org/maven2/") }
        maven { setUrl("https://kotlin.bintray.com/kotlinx") }
    }
    dependencies {
        classpath("com.android.tools.build:gradle:$buildToolsVersion")
        classpath("io.objectbox:objectbox-gradle-plugin:$objectBoxVersion")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
        classpath("org.jetbrains.kotlin:kotlin-serialization:$kotlinVersion")
        classpath("io.github.didi.dokit:dokitx-plugin:$dokitVersion")
    }

}

allprojects {
    repositories {
        // 国内maven库，优化墙内访问google maven速度
        maven { setUrl("https://maven.aliyun.com/repository/google") }
        maven { setUrl("https://maven.aliyun.com/repository/public") }
        maven { setUrl("https://maven.aliyun.com/repository/gradle-plugin") }

        google()
        mavenCentral()
        mavenLocal()

        maven { setUrl("https://plugins.gradle.org/m2/") }
        maven { setUrl("https://www.jitpack.io") }
        maven { setUrl("${properties["sdk.dir"]}/extras/android/m2repository/") }
        maven { setUrl("${properties["sdk.dir"]}/extras/google/m2repository/") }
        maven { setUrl("https://mvnrepository.com/artifact/org.robolectric/robolectric") }
        maven { setUrl("https://repo.maven.apache.org/maven2/") }
    }
}

/**
 * 编译项目时，执行清除Project的output文件
 */
tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

/**
 * 查看依赖树任务命令
 * 终端执行：./gradlew allDeps 即可查看全部依赖关系
 */
subprojects {
    tasks.register("allDeps", DependencyReportTask::class) {}
}