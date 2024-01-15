package io.ganguo.incubator.ui.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Created by Tony on 3/4/15.
 */
public class DemoAdapter extends BaseAdapter {
    private Activity mContext;
    private LayoutInflater mLayoutInflater;

    public DemoAdapter(Activity context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // create holder
        DataHolder dataHolder;
        if (convertView == null || convertView.getTag() == null) {
//            convertView = mLayoutInflater.inflate(R.layout.item_xxx, null);

            dataHolder = new DataHolder(convertView);
            convertView.setTag(dataHolder);
        } else {
            dataHolder = (DataHolder) convertView.getTag();
        }
        // refresh item
        dataHolder.refreshData(position);
        return convertView;
    }

    private class DataHolder {

        public DataHolder(View parent) {
            initView(parent);
        }

        public void initView(View parent) {

        }

        public void refreshData(int position) {

        }
    }

}
