package io.ganguo.library.ui.dialog;

import android.content.Context;
import android.util.Log;

import org.junit.Test;
import org.robolectric.shadows.ShadowDialog;

import io.ganguo.gtest.TestDes;
import io.ganguo.library.GGLibraryTestBase;

import static org.robolectric.Shadows.shadowOf;

/**
 * 单元测试 BaseDialog
 * Created by Lynn on 10/28/16.
 */

public class BaseDialogTest extends GGLibraryTestBase {
    @Test
    @TestDes("test BaseDialog")
    public void testBaseDialog() {
        //TODO 待完善
        TestDialog dialog = new TestDialog(getApplicationContext());
        dialog.setCancelable(true);
        asFalse(dialog.isShowing());

        final ShadowDialog shadowDialog = shadowOf(dialog);
        asTrue(shadowDialog.isCancelable());
        shadowDialog.show();
        asFalse(shadowDialog.hasBeenDismissed());
        shadowDialog.dismiss();
        asTrue(shadowDialog.hasBeenDismissed());
    }

    class TestDialog extends BaseDialog {
        private String TAG = TestDialog.class.getSimpleName();

        public TestDialog(Context context) {
            super(context);
        }

        @Override
        public void beforeInitView() {
            Log.d(TAG, "beforeInitView");
        }

        @Override
        public void initView() {
            Log.d(TAG, "initView");
        }

        @Override
        public void initListener() {
            Log.d(TAG, "initListener");
        }

        @Override
        public void initData() {
            Log.d(TAG, "initData");
        }

        @Override
        protected void onStart() {
            //TODO UIHelper调用了不存在的方法,
            //TODO static 方法无法通过正常手段mock掉, 测试时暂时注释掉onStart
//            super.onStart();
        }
    }
}
