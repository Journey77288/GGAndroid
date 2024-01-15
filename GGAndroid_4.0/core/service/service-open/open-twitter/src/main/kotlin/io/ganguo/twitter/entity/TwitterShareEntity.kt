package io.ganguo.twitter.entity

import android.net.Uri

/**
 * <pre>
 *     author : leo
 *     time   : 2019/11/05
 *     desc   : Twitter分享
 * </pre>
 * @property [text] 文本内容
 * @property [webPageUrl] 网页链接，可选
 * @property [imageFileUri] 本地图片文件Uri，可选
 */
data class TwitterShareEntity(var text: String) {
    var webPageUrl: String = ""
    var imageFileUri: Uri? = null
}