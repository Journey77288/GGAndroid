package io.ganguo.image.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import io.ganguo.GGimage.R;
import io.ganguo.image.adapter.holder.SysImageViewHolder;
import io.ganguo.image.entity.SysImageInfo;
import io.ganguo.library.core.image.GImageLoader;

/**
 * Created by rick on 7/8/15.
 */
public class SysImageAdapter extends RecyclerView.Adapter<SysImageViewHolder> {

    public static final DisplayImageOptions OPTION_LOADING_IMAGE =
            new DisplayImageOptions
                    .Builder()
                    .showImageOnFail(R.drawable.default_image)
                    .showImageOnLoading(R.drawable.default_image)
                    .showImageForEmptyUri(R.drawable.default_image)
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .build();


    //选择了的照片
    private HashSet<SysImageInfo> selectedImages = new HashSet<>();
    private ArrayList<SysImageInfo> allImage = new ArrayList<>();

    private Context mContext;
    private int mMaxSelectedImageCount = 9;


    public SysImageAdapter(Context mContext) {
        super();
        this.mContext = mContext;
    }

    @Override
    public SysImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_sys_image, null);
        return new SysImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SysImageViewHolder vh, final int position) {
        final SysImageInfo item = getItem(position);
        if (selectedImages.contains(item)) {
            vh.checkMarker.setActivated(true);
        } else {
            vh.checkMarker.setActivated(false);
        }
        vh.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!selectedImages.contains(item)) {
                    if (selectedImages.size() >= mMaxSelectedImageCount) {
                        Toast.makeText(mContext, R.string.exceed_requirement, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    vh.checkMarker.setActivated(true);
                    selectImage(position);
                } else {
                    vh.checkMarker.setActivated(false);
                    unSelectImage(position);
                }
            }
        });
        GImageLoader.getInstance().displayImage("file://" + item.getPath(), vh.imageViewAware, OPTION_LOADING_IMAGE);
    }


    @Override
    public int getItemCount() {
        return allImage.size();
    }

    public void selectImage(int position) {
        SysImageInfo item = getItem(position);
        selectedImages.add(item);
    }


    public void unSelectImage(int position) {
        SysImageInfo item = getItem(position);
        selectedImages.remove(item);
    }

    public HashSet<SysImageInfo> getSelectedImages() {
        return selectedImages;
    }

    public void clearSelectedImages() {
        this.selectedImages.clear();
    }

    public void add(SysImageInfo sysImageInfo) {
        allImage.add(sysImageInfo);
        notifyItemInserted(allImage.size());
    }

    public void addAll(List<SysImageInfo> sysImageInfos) {
        allImage.addAll(sysImageInfos);
    }

    public void clear() {
        allImage.clear();
    }

    private SysImageInfo getItem(int position) {
        return allImage.get(position);
    }

    public void setMaxSelectedImageCount(int count) {
        this.mMaxSelectedImageCount = count;
    }

    public ArrayList<SysImageInfo> getAllImage() {
        return allImage;
    }
}
