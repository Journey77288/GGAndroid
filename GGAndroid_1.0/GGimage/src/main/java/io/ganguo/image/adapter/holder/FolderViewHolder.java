package io.ganguo.image.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;

import io.ganguo.GGimage.R;

/**
 * Created by rick on 7/21/15.
 */
public class FolderViewHolder extends RecyclerView.ViewHolder {
    public ImageView checkMarker;
    public ImageView image;
    public ImageViewAware imageViewAware;

    public FolderViewHolder(View itemView) {
        super(itemView);
        image = (ImageView) itemView.findViewById(R.id.image);
        imageViewAware = new ImageViewAware(image);
        checkMarker = (ImageView) itemView.findViewById(R.id.indicator);
    }
}
