package io.ganguo.pay.wxpay;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import io.ganguo.pay.core.PayResult;

/**
 * Created by Roger on 06/07/2017.
 */

public class WXPayActivity extends Activity implements IWXAPIEventHandler {

    private IWXAPI api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, WXConstants.getWeChatAppId());
        boolean handled = api.handleIntent(getIntent(), this);
        if (!handled) {
            finish();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        if (api != null) {
            api.handleIntent(intent, this);
        }
    }

    @Override
    public void onReq(BaseReq baseReq) {
    }

    @Override
    public void onResp(BaseResp baseResp) {
        PayResult payResult = new PayResult();
        payResult.type = "wxpay";
        if (baseResp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            int errCode = baseResp.errCode;
            payResult.code = String.valueOf(errCode);
            payResult.message = baseResp.errStr;
            switch (errCode) {
                case BaseResp.ErrCode.ERR_OK:
                    getIntent().putExtra(WXConstants.WXPAY_RESULT, WXConstants.CODE_SUCCESS);
                    getIntent().putExtra(WXConstants.WXPAY_EXTRA, payResult);
                    setResult(WXConstants.REQUEST_CODE, getIntent());
                    break;
                case BaseResp.ErrCode.ERR_COMM:
                    getIntent().putExtra(WXConstants.WXPAY_RESULT, WXConstants.CODE_FAIL);
                    getIntent().putExtra(WXConstants.WXPAY_EXTRA, payResult);
                    setResult(WXConstants.REQUEST_CODE, getIntent());
                    break;
                case BaseResp.ErrCode.ERR_USER_CANCEL:
                    getIntent().putExtra(WXConstants.WXPAY_RESULT, WXConstants.CODE_CANCEL);
                    getIntent().putExtra(WXConstants.WXPAY_EXTRA, payResult);
                    setResult(WXConstants.REQUEST_CODE, getIntent());
                    break;
                default:
                    getIntent().putExtra(WXConstants.WXPAY_RESULT, WXConstants.CODE_NO_RESPONSE);
                    getIntent().putExtra(WXConstants.WXPAY_EXTRA, payResult);
                    setResult(WXConstants.REQUEST_CODE, getIntent());
                    break;
            }
        } else {
            payResult.code = String.valueOf("-1");
            payResult.message = "wxpay no response";
            getIntent().putExtra(WXConstants.WXPAY_RESULT, WXConstants.CODE_NO_RESPONSE);
            getIntent().putExtra(WXConstants.WXPAY_EXTRA, payResult);
            setResult(WXConstants.REQUEST_CODE, getIntent());
        }
        finish();
    }
}
