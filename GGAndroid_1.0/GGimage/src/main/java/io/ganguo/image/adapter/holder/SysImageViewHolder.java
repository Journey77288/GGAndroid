package io.ganguo.image.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;

import io.ganguo.GGimage.R;

/**
 * Created by rick on 7/9/15.
 */
public class SysImageViewHolder extends RecyclerView.ViewHolder {
    public ImageView image;
    public ImageView checkMarker;
    public ImageViewAware imageViewAware;

    public SysImageViewHolder(View convertView) {
        super(convertView);
        image = (ImageView) convertView.findViewById(R.id.image);
        checkMarker = (ImageView) convertView.findViewById(R.id.check_marker);
        imageViewAware = new ImageViewAware(image);
    }
}
