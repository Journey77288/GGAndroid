package io.ganguo.library.ui.adapter.v7;

import android.view.View;

/**
 * Created by rick on 11/30/15.
 */
public interface Command {
    void execute(BaseAdapter adapter, View view, int position);
}
