package io.ganguo.library.ui.adapter;

import android.content.Context;

/**
 * Created by Tony on 4/2/15.
 */
public interface IViewCreator<T> {
    ViewHolder createView(Context context, int position, T item);

    void updateView(ViewHolder viewHolder, int position, T item);
}
