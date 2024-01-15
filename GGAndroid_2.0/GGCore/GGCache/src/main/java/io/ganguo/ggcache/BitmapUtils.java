package io.ganguo.ggcache;

import android.graphics.Bitmap;

import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.HONEYCOMB_MR1;
import static android.os.Build.VERSION_CODES.KITKAT;

/**
 * bitmap utils
 * Created by Lynn on 2016/12/17.
 */

public class BitmapUtils {
    private BitmapUtils() {
    }

    static int getBitmapByteCount(final Bitmap bitmap) {
        final int result;

        if (SDK_INT >= KITKAT) {
            result = bitmap.getAllocationByteCount();
        } else if (SDK_INT >= HONEYCOMB_MR1) {
            result = bitmap.getByteCount();
        } else {
            result = bitmap.getRowBytes() * bitmap.getHeight();
        }

        if (result < 0) {
            throw new IllegalStateException("Negative size: " + bitmap);
        }
        return result;
    }

}
