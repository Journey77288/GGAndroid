package io.ganguo.pay.alipay

import android.util.Log
import io.ganguo.pay.core.PayOrderInfo

/**
 * <pre>
 *     author : leo
 *     time   : 2019/10/11
 *     desc   : 支付订单数据
 * </pre>
 * @property resultStatus the resultStatus
 * @property result the result
 * @property memo the memo
 */
data class AliPayOrderInfo(var rawResult: String) : PayOrderInfo() {
    var resultStatus: Int = 0
    var result: String? = null
    var memo: String? = null

    init {
        val resultParams = rawResult
                .replace("{", "")
                .replace("}", "")
                .split(",".toRegex())
                .dropLastWhile { it.isEmpty() }
                .toTypedArray()
        for (resultParam in resultParams) {
            val resultTrim = resultParam.trim { it <= ' ' }

            if (resultTrim.startsWith("resultStatus")) {
                val status = gatValue(resultTrim, "resultStatus")
                if (status.isNotEmpty()) {
                    resultStatus = status.toInt()
                } else {
                    Log.e("AliPayOrderInfo", "AliPayOrderInfo resultStatus is Empty")
                }
            }
            if (resultTrim.startsWith("result")) {
                result = gatValue(resultTrim, "result")
            }
            if (resultTrim.startsWith("memo")) {
                memo = gatValue(resultTrim, "memo")
            }
        }
    }

    private fun gatValue(content: String, key: String): String {
        val prefix = "$key="
        return content.substring(content.indexOf(prefix) + prefix.length, content.length)
    }

    override fun toString(): String {
        return ("resultStatus={" + resultStatus + "};memo={" + memo
                + "};result={" + result + "}")
    }
}