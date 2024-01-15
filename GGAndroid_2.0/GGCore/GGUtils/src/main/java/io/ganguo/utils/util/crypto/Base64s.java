package io.ganguo.utils.util.crypto;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import io.ganguo.utils.bean.Globals;
import io.ganguo.utils.util.Strings;

/**
 * Base64 相关工具类
 * <p>
 * Created by hulkyao on 23/6/2017.
 */
public final class Base64s {

    private Base64s() {
        throw new Error(Globals.ERROR_MSG_UTILS_CONSTRUCTOR);
    }

    public static Bitmap toBitmap(String base64) {
        if (base64 != null && Strings.isNotEmpty(base64)) {
            byte[] decodedString = Base64.decode(base64, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        }
        return null;
    }
}
