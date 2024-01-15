package io.ganguo.image.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nostra13.universalimageloader.core.DisplayImageOptions;

import java.util.ArrayList;
import java.util.Collection;

import io.ganguo.GGimage.R;
import io.ganguo.image.adapter.holder.FolderViewHolder;
import io.ganguo.image.entity.Folder;
import io.ganguo.library.core.image.GImageLoader;

/**
 * Created by rick on 7/22/15.
 */
public class FolderAdapter extends RecyclerView.Adapter<FolderViewHolder> {
    public static final DisplayImageOptions OPTION_LOADING_IMAGE =
            new DisplayImageOptions
                    .Builder()
                    .showImageOnFail(R.drawable.default_image)
                    .showImageOnLoading(R.drawable.default_image)
                    .showImageForEmptyUri(R.drawable.default_image)
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .build();

    private Context mContext;
    private ArrayList<Folder> mList = new ArrayList<>();

    public FolderAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public FolderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FolderViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_image_folder, null));
    }

    @Override
    public void onBindViewHolder(final FolderViewHolder vh, int position) {
        Folder item = mList.get(position);

        vh.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (vh.checkMarker.getVisibility() == View.VISIBLE) {
                    vh.checkMarker.setVisibility(View.GONE);
                }else {
                    vh.checkMarker.setVisibility(View.VISIBLE);
                }
            }
        });
        GImageLoader.getInstance().displayImage("file://" + item.getCover().getPath(), vh.imageViewAware, OPTION_LOADING_IMAGE);

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public boolean add(Folder object) {
        return mList.add(object);
    }

    public boolean addAll(Collection<? extends Folder> collection) {
        return mList.addAll(collection);
    }

    public void clear() {
        mList.clear();
    }

    public Folder get(int index) {
        return mList.get(index);
    }

    public Folder remove(int index) {
        return mList.remove(index);
    }

    public boolean remove(Object object) {
        return mList.remove(object);
    }
}
