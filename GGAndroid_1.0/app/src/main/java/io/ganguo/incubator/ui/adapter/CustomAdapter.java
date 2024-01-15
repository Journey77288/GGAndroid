package io.ganguo.incubator.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.TextView;

import io.ganguo.incubator.R;
import io.ganguo.incubator.entity.User;
import io.ganguo.library.ui.adapter.ListAdapter;
import io.ganguo.library.ui.adapter.ViewHolder;

/**
 * Created by Tony on 4/2/15.
 */
public class CustomAdapter extends ListAdapter<User> {

    protected CustomAdapter(Context context) {
        super(context);
    }

    @Override
    public ViewHolder createView(Context context, int position, User data) {
        return new ViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_demo, null));
    }

    @Override
    public void updateView(ViewHolder viewHolder, int position, User data) {
        TextView tv_name = viewHolder.findViewById(R.id.tv_name);

        tv_name.setText(getItem(position) + "");
    }
}
