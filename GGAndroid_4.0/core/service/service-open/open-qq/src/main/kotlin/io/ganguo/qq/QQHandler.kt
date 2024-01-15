package io.ganguo.qq

import android.content.Context
import io.ganguo.sample.sdk.annotation.OpenChannel
import io.ganguo.sample.sdk.exception.OpenServiceException
import io.ganguo.qq.entity.QQUserEntity
import io.ganguo.sample.sdk.OpenHelper
import org.json.JSONException
import org.json.JSONObject

/**
 * <pre>
 *     author : leo
 *     time   : 2019/10/12
 *     desc   :  QQ处理工具类
 * </pre>
 */
class QQHandler {
    companion object {
        private const val PACKAGE_QQ = "com.tencent.mobileqq"

        /**
         * 检查QQ相关配置参数是否有异常
         * @param context 上下文
         * @throws
         */
        @JvmStatic
        fun checkException(context: Context): OpenServiceException? {
            return if (QQConstants.QQ_APP_ID.isNullOrEmpty()) {
                val msg = "Please initialize QQ sdk in Application!!!"
                val errorCode = QQConstants.ErrorCode.QQ_APP_ID_NOT_CONFIGURED
                OpenServiceException(OpenChannel.QQ, errorCode, msg)
            } else if (!OpenHelper.isAppInstalled(context, PACKAGE_QQ)) {
                val msg = "Please initialize QQ sdk in Application!!!"
                val errorCode = QQConstants.ErrorCode.QQ_NOT_INSTALLED
                OpenServiceException(OpenChannel.QQ, errorCode, msg)
            } else {
                null
            }
        }


        /**
         * JSONObject to QQUserEntity
         *
         * @param o
         * @return [QQUserEntity]
         */
        @JvmStatic
        fun asQQUserEntity(o: Any?): QQUserEntity? {
            if (null == o || o !is JSONObject) {
                return null
            }
            if (o.length() == 0) {
                return null
            }
            var entity: QQUserEntity? = null
            try {
                val openId = o.getString(QQConstants.OPENID)
                val accessToken = o.getString(QQConstants.ACCESS_TOKEN)
                val expiresIn = o.getString(QQConstants.EXPIRES_IN)
                entity = QQUserEntity(openId, accessToken, expiresIn)
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            return entity
        }

    }
}
