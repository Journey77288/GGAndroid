package io.ganguo.incubator.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import io.ganguo.incubator.R;
import io.ganguo.incubator.entity.User;
import io.ganguo.library.ui.adapter.ListAdapter;
import io.ganguo.library.ui.adapter.ViewHolder;

/**
 * Created by rick on 4/1/15.
 */
public class DemoListAdapter extends ListAdapter<User> {

    protected DemoListAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    public ViewHolder createView(Context context, int position, User data) {
        return new DataHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_demo, null));
    }

    @Override
    public void updateView(ViewHolder viewHolder, int position, User data) {
        DataHolder dataHolder = (DataHolder) viewHolder;

        dataHolder.tv_name.setText("");
    }

    class DataHolder extends ViewHolder implements View.OnClickListener {
        TextView tv_name;

        public DataHolder(View parent) {
            super(parent);

            tv_name = findViewById(R.id.tv_name);
            tv_name.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            User user = getItem();

            Intent intent = new Intent();
            intent.putExtra("user", user);
            getContext().startActivity(intent);
        }
    }
}
