package io.ganguo.image.activity;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import io.ganguo.GGimage.R;
import io.ganguo.image.adapter.ItemDecoration.SpacesItemDecoration;
import io.ganguo.image.adapter.SysImageAdapter;
import io.ganguo.image.entity.Folder;
import io.ganguo.image.entity.SysImageInfo;
import io.ganguo.image.widget.FolderPopwin;

/**
 * Created by rick on 7/8/15.
 */
public class MultiImagePickerFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final String MAX_SELECTED_IMAGE_COUNT = "MAX_SELECTED_IMAGE_COUNT";
    public static final String ARGS_PATH = "ARGS_PATH";


    private boolean hasFolderGened = false;


    private ArrayList<Folder> mResultFolder = new ArrayList<>();

    private RecyclerView mRecyclerView;
    private SysImageAdapter mAdapter;

    /**
     * 获取一个新的  MultiImagePickerFragment
     *
     * @param imageCount 最多允许获取多少张照片
     * @return
     */
    public static MultiImagePickerFragment newInstance(int imageCount) {
        MultiImagePickerFragment fragment = new MultiImagePickerFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(MAX_SELECTED_IMAGE_COUNT, imageCount);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        int max = bundle.getInt(MAX_SELECTED_IMAGE_COUNT);

        View view = inflater.inflate(R.layout.fragment_multi_photo_picker, null);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        GridLayoutManager manager = new GridLayoutManager(getActivity(), 3);
        mRecyclerView.setLayoutManager(manager);

        mAdapter = new SysImageAdapter(getActivity());
        mAdapter.setMaxSelectedImageCount(max);
        mRecyclerView.setAdapter(mAdapter);

        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.dp_4);
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(ImagesQuery.TOKEN, null, this);
    }

    public HashSet<SysImageInfo> getSelectedImages() {
        return mAdapter.getSelectedImages();
    }

    public void clearSelectedImage() {
        mAdapter.clearSelectedImages();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public android.support.v4.content.Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (id == ImagesQuery.TOKEN) {
            CursorLoader cursorLoader = new CursorLoader(getActivity(),
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, ImagesQuery.PROJECTION,
                    null, null, ImagesQuery.PROJECTION[3] + " DESC");
            return cursorLoader;
        } else if (id == ImagesQuery.CATEGORY_TOKEN) {
            CursorLoader cursorLoader = new CursorLoader(getActivity(),
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, ImagesQuery.PROJECTION,
                    ImagesQuery.PROJECTION[1] + " like '%" + args.getString(ARGS_PATH) + "%'", null, ImagesQuery.PROJECTION[3] + " DESC");
            return cursorLoader;
        }

        return null;
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<Cursor> loader, Cursor data) {
        if (data != null) {
            List<SysImageInfo> images = new ArrayList<>();
            int count = data.getCount();
            if (count > 0) {
                data.moveToFirst();
                do {
                    String path = data.getString(ImagesQuery.IMAGE_PATH);
                    String name = data.getString(ImagesQuery.IMAGE_NAME);
                    long dateTime = data.getLong(ImagesQuery.IMAGE_ADDED_DATE);
                    SysImageInfo image = new SysImageInfo(name, path, DateFormat.format("yyyy-MM-dd", dateTime).toString());
                    images.add(image);
                    if (!hasFolderGened) {
                        // 获取文件夹名称
                        File imageFile = new File(path);
                        File folderFile = imageFile.getParentFile();
                        Folder folder = new Folder();
                        folder.setName(folderFile.getName());
                        folder.setPath(folderFile.getAbsolutePath());
                        folder.setCover(image);
                        if (!mResultFolder.contains(folder)) {
                            mResultFolder.add(folder);
                        } else {
                            // 更新
                            Folder f = mResultFolder.get(mResultFolder.indexOf(folder));
                            f.increment();
                        }
                    }

                } while (data.moveToNext());

                mAdapter.clear();
                mAdapter.addAll(images);
                mAdapter.notifyDataSetChanged();


//                mFolderAdapter.setData(mResultFolder);
                hasFolderGened = true;

            }
        }
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<Cursor> loader) {

    }

    public void loadAllImage() {
        getLoaderManager().restartLoader(ImagesQuery.TOKEN, null, this);
    }

    public void loadImagesInFolder(int i) {
        Bundle bundle = new Bundle();
        bundle.putString(ARGS_PATH, mResultFolder.get(i).getPath());

        getLoaderManager().restartLoader(ImagesQuery.CATEGORY_TOKEN, bundle, this);
    }

    public void showFolderPopupWindow() {
        FolderPopwin popwin = new FolderPopwin(getActivity());
        popwin.getAdapter().addAll(mResultFolder);
        popwin.getAdapter().notifyDataSetChanged();
        popwin.showAtLocation(getView(), Gravity.BOTTOM, 0, 0);
    }

    private interface ImagesQuery {
        int TOKEN = 0x1;
        int CATEGORY_TOKEN = 0x2;

        String[] PROJECTION = {
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.DATE_ADDED,
                MediaStore.Images.Media.LATITUDE,
                MediaStore.Images.Media.LONGITUDE,};

        int IMAGE_PATH = 1;
        int IMAGE_NAME = 2;
        int IMAGE_ADDED_DATE = 3;
    }
}
