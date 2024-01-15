package io.ganguo.core.update.model

/**
 * <pre>
 *     @author : zoyen
 *     time   : 2020/05/28
 *     desc   : apk 版本信息数据
 * </pre>
 *
 * @property [title] 更新提示标题
 * @property [describe] 更新说明
 * @property [versionCode] 版本号
 * @property [versionName] 版本名称
 * @property [link] 下载链接
 * @property [md5] 加密MD5值
 * @property [isForce] 是否强制更新
 */
data class AppVersionModel(
        val title: String = "",
        val describe: String = "",
        val versionName: String = "",
        val versionCode: Int = 0,
        val link: String = "",
        val md5: String = "",
        val isForce: Boolean = false
)
