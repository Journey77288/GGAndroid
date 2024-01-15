package io.ganguo.alipay

import io.ganguo.alipay.AliPayConstants.ResultCode.AUTH_RESULT_CODE_FROZEN_ACCOUNT
import io.ganguo.alipay.AliPayConstants.ResultCode.AUTH_RESULT_CODE_SUCCESS
import io.ganguo.alipay.AliPayConstants.ResultCode.AUTH_RESULT_CODE_SYSTEM_EXCEPTION
import io.ganguo.alipay.AliPayConstants.Status.AUTH_STATUS_CANCEL
import io.ganguo.alipay.AliPayConstants.Status.AUTH_STATUS_NETWORK_ERROR
import io.ganguo.alipay.AliPayConstants.Status.AUTH_STATUS_SUCCESS

/**
 * <pre>
 *     author : leo
 *     time   : 2019/10/30
 *     desc   : 支付宝登录/分享相关常量、参数
 * </pre>
 */
class AliPayConstants {


    /**
     * @property [ALI_PAY_APP_ID] 支付宝开放平台appId
     * @property [ALI_PAY_SDK_IS_INIT] 支付宝SDK是否初始化
     */
    companion object {
        @JvmStatic
        var ALI_PAY_APP_ID = ""
        var ALI_PAY_SDK_IS_INIT = false
    }


    /**
     * @property AUTH_STATUS_CANCEL 授权取消
     * @property AUTH_STATUS_SUCCESS 业务处理成功
     * @property AUTH_STATUS_NETWORK_ERROR 网路连接出错
     */
    object Status {
        const val AUTH_STATUS_SUCCESS = "9000"
        const val AUTH_STATUS_CANCEL = "6001"
        const val AUTH_STATUS_NETWORK_ERROR = "6002"
        const val AUTH_STATUS_SYSTEM_EXCEPTION = "4000"
    }

    /**
     * @property AUTH_RESULT_CODE_SUCCESS 请求处理成功
     * @property AUTH_RESULT_CODE_FROZEN_ACCOUNT 账户已冻结
     * @property AUTH_RESULT_CODE_SYSTEM_EXCEPTION 系统异常
     */
    object ResultCode {
        const val AUTH_RESULT_CODE_SUCCESS = "200"
        const val AUTH_RESULT_CODE_FROZEN_ACCOUNT = "1005"
        const val AUTH_RESULT_CODE_SYSTEM_EXCEPTION = "202"
    }

    /**
     * @property [ALIPAY_NOT_INSTALLED] 支付宝没有安装
     * @property [ALIPAY_SDK_NOT_INIT] 支付宝SDK没有初始化
     */
    object ErrorCode {
        const val ALIPAY_NOT_INSTALLED = -1
        const val ALIPAY_SDK_NOT_INIT = -2
    }
}