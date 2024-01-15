package com.ganguo.banner.callback;

import android.widget.ImageView;

/**
 * Created by leo on 2017/8/28.
 * 清理图片缓存,防止OOM，如果是使用ViewModel，需要实现该接口，返回对应的ImageView对象
 */
public interface OnRecycleImageListener {
    ImageView getRecycleImage();
}

