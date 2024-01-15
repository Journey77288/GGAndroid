package io.ganguo.image.widget;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import io.ganguo.GGimage.R;
import io.ganguo.image.adapter.FolderAdapter;
import io.ganguo.image.adapter.ItemDecoration.SpacesItemDecoration;
import io.ganguo.library.util.AndroidUtils;

/**
 * Created by rick on 7/21/15.
 */
public class FolderPopwin extends PopupWindow {
    private Activity mContext;
    private final LayoutInflater mInflater;


    private RecyclerView mRecyclerView;
    private FolderAdapter mAdapter;

    public FolderPopwin(Context context) {
        super(context);
        mContext = (Activity) context;
        mInflater = LayoutInflater.from(mContext);
        View view = mInflater.inflate(R.layout.popwin_folder, null);
        bindView(view);

        setContentView(view);
        setFocusable(true);
        setTouchable(true);
        setOutsideTouchable(false);

        setWidth(AndroidUtils.getScreenWidth(mContext));
        setHeight((int) mContext.getResources().getDimension(R.dimen.dp_204));


    }

    private void bindView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        GridLayoutManager manager = new GridLayoutManager(mContext, 3);

        mRecyclerView.setLayoutManager(manager);
        mAdapter = new FolderAdapter(mContext);
        mRecyclerView.setAdapter(mAdapter);
        int spacingInPixels = mContext.getResources().getDimensionPixelSize(R.dimen.dp_4);
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff, int gravity) {
        super.showAsDropDown(anchor, xoff, yoff, gravity);
        View container = (View) getContentView().getParent();
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams p = (WindowManager.LayoutParams) container.getLayoutParams();
        p.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        p.dimAmount = 0.3f;
        wm.updateViewLayout(container, p);
    }

    public FolderAdapter getAdapter() {
        return mAdapter;
    }

}
