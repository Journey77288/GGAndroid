package io.ganguo.pay.wxpay;

/**
 * Created by Roger on 06/07/2017.
 */

class WXConstants {
    static final int REQUEST_CODE = 666;
    static final String WXPAY_RESULT = "wxpay_result";
    static final String WXPAY_EXTRA = "wxpay_extra";

    static final String CODE_SUCCESS = "code_success";
    static final String CODE_FAIL = "code_fail";
    static final String CODE_CANCEL = "code_cancel";
    static final String CODE_NO_RESPONSE = "code_no_response";
    static final String CODE_NOT_INSTALL = "code_not_install";
    private static String sWeChatAppId = null;

    public static void setWeChatAppId(String wechatAppId) {
        sWeChatAppId = wechatAppId;
    }

    public static String getWeChatAppId() {
        return sWeChatAppId;
    }

}
