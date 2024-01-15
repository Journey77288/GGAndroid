package io.ganguo.tab.utils;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.List;

import io.ganguo.tab.callback.OnTabChooseListener;
import io.ganguo.tab.callback.OnTabSelectedChangeListener;
import io.ganguo.tab.view.TabStrip;

/**
 * Created by leo on 2017/11/2.
 * TabViewHelper TabLayout 辅助工具类,方便代码复用
 */
public class TabViewHelper implements View.OnClickListener {
    private static final int TITLE_OFFSET_DIPS = 24;
    private final int titleOffset;
    private final Builder builder;
    private int currentPosition = -1;

    public TabViewHelper(Builder builder) {
        this.builder = builder;
        titleOffset = (int) (TITLE_OFFSET_DIPS * this.builder.parentView.getResources().getDisplayMetrics().density);
        initTab();
    }


    public static class Builder {
        //tab父ViewGroup
        public View parentView;
        //context上下文
        public Context context;
        //TabStrip 滑动指示器
        public TabStrip tabStrip;
        //存储Tab View对象
        public List<View> tabViews;
        //tab栏父容器宽度 铺满屏幕/根据内容自适应
        public boolean distributeEvenly;
        //tab栏选中回调
        public OnTabChooseListener tabChooseListener;
        //默认选中位置
        public int defaultPosition = 0;
        //tab状态回调
        public OnTabSelectedChangeListener selectedListener;


        public Builder(Context context) {
            this.context = context;
        }

        public Builder setParentView(View parentView) {
            this.parentView = parentView;
            return this;
        }

        public Builder setContext(Context context) {
            this.context = context;
            return this;
        }


        public Builder setTabStrip(TabStrip tabStrip) {
            this.tabStrip = tabStrip;
            return this;
        }


        public Builder setTabViews(List<View> tabViews) {
            this.tabViews = tabViews;
            return this;
        }

        public Builder setDistributeEvenly(boolean distributeEvenly) {
            this.distributeEvenly = distributeEvenly;
            return this;
        }

        public Builder setTabChooseListener(OnTabChooseListener tabChooseListener) {
            this.tabChooseListener = tabChooseListener;
            return this;
        }

        public Builder setSelectedListener(OnTabSelectedChangeListener selectedListener) {
            this.selectedListener = selectedListener;
            return this;
        }

        public TabViewHelper build() {
            return new TabViewHelper(this);
        }
    }

    /**
     * Tab栏初始化操作
     */
    public void initTab() {
        if (builder.tabStrip.getChildCount() != 0) {
            builder.tabStrip.removeAllViews();
        }
        initTabStrip();
    }


    /**
     * 配置Tab栏参数
     */
    public void initTabStrip() {
        if (builder.tabViews.size() == 0) {
            return;
        }
        for (int i = 0; i < builder.tabViews.size(); i++) {
            View tabView = builder.tabViews.get(i);
            initTabAttribute(tabView, i);
        }
    }


    /**
     * 设置tabView相关属性
     *
     * @param view
     * @param i
     */
    public void initTabAttribute(View view, int i) {
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) view.getLayoutParams();
        if (lp == null) {
            lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }
        //是否平均分布于屏幕
        if (builder.distributeEvenly) {
            lp.width = 0;
            lp.weight = 1;
        }
        view.setLayoutParams(lp);
        if (view.getParent() != null) {
            ((ViewGroup) view.getParent()).removeView(view);
        }
        builder.tabStrip.addView(view);
        int oldPosition = currentPosition;
        currentPosition = builder.defaultPosition;
        boolean selected = (i == builder.defaultPosition);
        view.setSelected(selected);
        onTabSelectedChange(oldPosition, selected);
        view.setOnClickListener(this);
    }


    /**
     * 滑动tab栏
     *
     * @param positionOffset
     * @param position
     */
    public void scrollToTab(int position, int positionOffset) {
        TabStrip tabStrip = builder.tabStrip;
        int tabChildCount = tabStrip.getChildCount();
        if (position < 0 || position >= tabChildCount) {
            return;
        }
        View selectedChild = tabStrip.getChildAt(position);
        if (selectedChild != null) {
            int targetScrollX = selectedChild.getLeft() + positionOffset;
            if (position > 0 || positionOffset > 0) {
                targetScrollX -= titleOffset;
            }
            builder.parentView.scrollTo(targetScrollX, 0);
        }
    }


    /**
     * 设置单个tab栏选中状态
     *
     * @param view
     * @param isChoose
     */
    public void setTabViewChooseState(View view, boolean isChoose) {
        view.setSelected(isChoose);
        ViewGroup viewGroup;
        if (!(view instanceof ViewGroup)) {
            return;
        }
        viewGroup = (ViewGroup) view;
        for (int j = 0; j < viewGroup.getChildCount(); j++) {
            viewGroup.getChildAt(j).setSelected(isChoose);
        }
    }


    /**
     * 重置tab栏选中状态
     *
     * @param position
     */
    public void onResetTabView(int position) {
        TabStrip tabStrip = builder.tabStrip;
        if (tabStrip == null) {
            return;
        }
        int oldPosition = currentPosition;
        currentPosition = position;
        for (int i = 0; i < tabStrip.getChildCount(); i++) {
            View view = tabStrip.getChildAt(i);
            boolean selected = (position == i);
            setTabViewChooseState(view, selected);
            onTabSelectedChange(oldPosition, selected);
        }
    }


    /**
     * Selected回调
     *
     * @param oldPosition
     * @param selected
     */
    private void onTabSelectedChange(int oldPosition, boolean selected) {
        OnTabSelectedChangeListener selectedListener = builder.selectedListener;
        if (selectedListener != null && selected && oldPosition != currentPosition) {
            selectedListener.onTabSelectedChange(currentPosition, oldPosition);
        }
    }


    /**
     * tab栏点击事件
     */
    @Override
    public void onClick(View v) {
        TabStrip tabStrip = builder.tabStrip;
        OnTabChooseListener listener = builder.tabChooseListener;
        OnTabSelectedChangeListener selectedListener = builder.selectedListener;
        int oldPosition = currentPosition;
        for (int i = 0; i < tabStrip.getChildCount(); i++) {
            View tabView = tabStrip.getChildAt(i);
            boolean selected = (listener.isSwitchTab(i) && v == tabView);
            if (selected) {
                currentPosition = i;
                listener.onChooseTab(i);
                onTabSelectedChange(oldPosition, true);
            }
        }
    }
}
