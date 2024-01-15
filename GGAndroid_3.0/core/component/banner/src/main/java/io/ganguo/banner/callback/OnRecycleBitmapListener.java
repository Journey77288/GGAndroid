package io.ganguo.banner.callback;

import android.widget.ImageView;

/**
 * Created by leo on 2017/8/30.
 * 回收ImageView中的Bitmap对象，避免内存泄露
 */
public interface OnRecycleBitmapListener {
    void recycleImageBitmap(ImageView imageView);
}
