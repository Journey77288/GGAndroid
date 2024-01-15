package io.ganguo.banner;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import androidx.viewpager.widget.ViewPager;

import com.ganguo.banner.R;

import io.ganguo.banner.adapter.BannerAdapter;
import io.ganguo.banner.callback.OnBannerCreateViewListener;
import io.ganguo.banner.callback.OnBannerScrollPositionListener;
import io.ganguo.banner.callback.OnRecycleBitmapListener;
import io.ganguo.banner.callback.OnRecycleImageListener;
import io.ganguo.banner.view.BannerViewPager;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import io.ganguo.log.core.Logger;
import io.ganguo.utils.util.Timers;


/**
 * 广告轮播栏  -  控件
 */
public class Banner extends FrameLayout implements ViewPager.OnPageChangeListener {
    //config
    private int autoPlayDuration = BannerConfig.TIME;
    private int scrollDuration = BannerConfig.SCROLL_DURATION;
    private int pageRealCount = 0;//ViewPager实际页数量，由外部设置
    private int currentItem = 1;
    private int selectedPositon = 0;//当前Banner显示索引
    private boolean isAutoPlay = BannerConfig.IS_AUTO_PLAY;
    private boolean isViewPagerScroll = BannerConfig.IS_SCROLL;

    //View
    private List<OnBannerScrollPositionListener> scrollPositionListeners = new ArrayList<>();
    private OnRecycleBitmapListener mOnRecycleBitmapListener;
    private BannerScroller mScroller;
    private BannerViewPager viewPager;
    private BannerAdapter adapter;
    private boolean isStart = false;
    private boolean isLoop = true;
    //通过该接口创建单个PageView
    private OnBannerCreateViewListener createViewListener;


    public Banner(Context context) {
        this(context, null);
    }

    public Banner(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Banner(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(getContext(), attrs);
    }


    private void initView(Context context, AttributeSet attrs) {
        handleTypedArray(context, attrs);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.banner, this, true);
        viewPager = view.findViewById(R.id.bannerViewPager);
        initViewPagerScrollConfig();
    }


    /**
     * 初始化AttributeSet参数
     *
     * @param context
     * @param attrs
     */
    private void handleTypedArray(Context context, AttributeSet attrs) {
        if (attrs == null) {
            return;
        }
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.Banner);
        int n = typedArray.getIndexCount();
        autoPlayDuration = typedArray.getInt(R.styleable.Banner_delay_time, BannerConfig.TIME);
        scrollDuration = typedArray.getInt(R.styleable.Banner_scroll_time, BannerConfig.SCROLL_DURATION);
        isAutoPlay = typedArray.getBoolean(R.styleable.Banner_is_auto_play, BannerConfig.IS_AUTO_PLAY);
        typedArray.recycle();
    }


    /**
     * 设置是否开启自动轮播
     *
     * @param isAutoPlay
     * @return
     */
    public Banner isAutoPlay(boolean isAutoPlay) {
        this.isAutoPlay = isAutoPlay;
        return this;
    }

    /**
     * 设置轮播间隔时长
     *
     * @param autoPlayDuration
     * @return
     */
    public Banner setAutoPlayDuration(int autoPlayDuration) {
        this.autoPlayDuration = autoPlayDuration;
        return this;
    }


    /**
     * 设置ViewPager平滑过渡时间
     *
     * @param scrollTime
     * @return
     */
    public Banner setScrollDuration(int scrollTime) {
        this.scrollDuration = scrollTime;
        return this;
    }


    /**
     * 设置ViewPager切换过渡动画
     *
     * @param transformer
     * @return
     */
    public Banner setBannerAnimation(ViewPager.PageTransformer transformer) {
        if (transformer != null) {
            setPageTransformer(true, transformer);
        }
        return this;
    }

    /**
     * Set the number of pages that should be retained to either side of the
     * current page in the view hierarchy in an idle state. Pages beyond this
     * limit will be recreated from the adapter when needed.
     *
     * @param limit How many pages will be kept offscreen in an idle state.
     * @return Banner
     */
    public Banner setOffscreenPageLimit(int limit) {
        if (limit == -1) {
            return this;
        }
        if (viewPager != null) {
            viewPager.setOffscreenPageLimit(limit);
        }
        return this;
    }

    /**
     * Set a {@link ViewPager.PageTransformer} that will be called for each attached page whenever
     * the scroll position is changed. This allows the application to apply custom property
     * transformations to each page, overriding the default sliding look and feel.
     *
     * @param reverseDrawingOrder true if the supplied PageTransformer requires page views
     *                            to be drawn from last to first instead of first to last.
     * @param transformer         PageTransformer that will modify each page's animation properties
     * @return Banner
     */
    public Banner setPageTransformer(boolean reverseDrawingOrder, ViewPager.PageTransformer transformer) {
        viewPager.setPageTransformer(reverseDrawingOrder, transformer);
        return this;
    }


    /**
     * ViewPager 是否允许滑动
     *
     * @param isViewPagerScroll
     */
    public Banner setViewPagerIsScroll(boolean isViewPagerScroll) {
        this.isViewPagerScroll = isViewPagerScroll;
        return this;
    }


    /**
     * 初始化ViewPager 滑动过渡时间
     */
    private void initViewPagerScrollConfig() {
        try {
            Field mField = ViewPager.class.getDeclaredField("mScroller");
            mField.setAccessible(true);
            mScroller = new BannerScroller(viewPager.getContext());
            mScroller.setDuration(scrollDuration);
            mField.set(viewPager, mScroller);
        } catch (Exception e) {
            Logger.INSTANCE.e(e.getMessage());
        }
    }


    /**
     * 是否可循环
     *
     * @param isLoop
     * @return
     */
    public Banner setLoop(boolean isLoop) {
        this.isLoop = isLoop;
        return this;
    }


    /**
     * 暴露bitmap回收方法
     *
     * @param onRecycleBitmapListener
     * @return
     */
    public Banner setOnRecycleBitmapListener(OnRecycleBitmapListener onRecycleBitmapListener) {
        this.mOnRecycleBitmapListener = onRecycleBitmapListener;
        return this;
    }


    /**
     * 参数配置完成执行方法
     *
     * @return
     */
    public Banner build(int pageRealCount, OnBannerCreateViewListener createViewListener) {
        this.pageRealCount = pageRealCount;
        this.createViewListener = createViewListener;
        if (pageRealCount == 0) {
            throw new RuntimeException("The number of pages must be greater than 0");
        }
        if (isAutoPlay) {
            isAutoPlay = pageRealCount > 1;
        }
        initViewPagerScrollConfig();
        initViewPager();
        return this;
    }


    /**
     * 初始化 ViewPager
     */
    private void initViewPager() {
        adapter = createBannerAdapter();
        viewPager.addOnPageChangeListener(this);
        viewPager.setAdapter(adapter);
        viewPager.setFocusable(true);
        if (isViewPagerScroll && pageRealCount > 1) {
            viewPager.setScrollable(true);
        } else {
            viewPager.setScrollable(false);
        }
        if (adapter.getCount() <= 0) {
            return;
        }
        if (isLoop) {
            currentItem = 1;
            viewPager.setCurrentItem(currentItem, false);
        }
        if (isAutoPlay) {
            startAutoPlay();
        }
    }


    /**
     * 创建BannerAdapter
     *
     * @return BannerAdapter
     */
    public BannerAdapter createBannerAdapter() {
        if (pageRealCount == 0) {
            return null;
        }
        List<View> viewList = new ArrayList<>();
        if (!isViewPagerScroll) {
            View view = createViewListener.onCreateLoopPagerView(0);
            viewList.add(view);
        } else if (isLoop) {
            viewList.addAll(getLoopPagerView());
        } else {
            viewList.addAll(createViewListener.onCreateUnLoopAllPagerView());
        }
        return new BannerAdapter(viewList);
    }

    /**
     * 添加可循环的View
     *
     * @return List<View>
     */
    protected List<View> getLoopPagerView() {
        List<View> views = new ArrayList<>();
        for (int i = 0; i <= getRealPageSize() + 1; i++) {
            View view;
            int position;
            if (i == 0) {
                position = getRealPageSize() - 1;
            } else if (i == getRealPageSize() + 1) {
                position = 0;
            } else {
                position = i - 1;
            }
            view = createViewListener.onCreateLoopPagerView(position);
            views.add(view);
        }
        return views;
    }


    /**
     * 开始自动轮播
     */
    public void startAutoPlay() {
        isStart = true;
        Timers.killTimer(task);
        Timers.setTimeout(task, autoPlayDuration);
    }

    /**
     * 停止自动轮播
     */
    public void stopAutoPlay() {
        isStart = false;
        Timers.killTimer(task);
    }


    /**
     * 自动轮播 Runnable
     */
    private final Runnable task = new Runnable() {
        @Override
        public void run() {
            if (!isStart) {
                return;
            }
            if (!isAutoPlay) {
                return;
            }
            if (getRealPageSize() <= 1) {
                return;
            }
            if (isLoop) {
                switchCyclicAutoPlayPage();
            } else {
                switchUnCyclicAutoPlayPage();
            }
            Timers.setTimeout(this, autoPlayDuration);
        }
    };

    /**
     * function：自动轮播，循环播放
     */
    protected void switchCyclicAutoPlayPage() {
        currentItem = currentItem + 1;
        if (currentItem >= adapter.getCount()) {
            currentItem = 0;
            viewPager.setCurrentItem(currentItem, false);
        } else {
            viewPager.setCurrentItem(currentItem);
        }
    }

    /**
     * function：自动轮播，不循环播放
     */
    protected void switchUnCyclicAutoPlayPage() {
        currentItem = currentItem + 1;
        if (currentItem >= getRealPageSize()) {
            currentItem = 0;
        }
        if (currentItem == 0) {
            viewPager.setCurrentItem(currentItem, false);
        } else {
            viewPager.setCurrentItem(currentItem);
        }
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (isAutoPlay) {
            int action = ev.getAction();
            if (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_MOVE) {
                stopAutoPlay();
            } else if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL
                    || action == MotionEvent.ACTION_OUTSIDE) {
                startAutoPlay();
            }
        }
        return super.dispatchTouchEvent(ev);
    }


    @Override
    public void onPageScrollStateChanged(int state) {
        if (adapter != null && isViewPagerScroll && isLoop) {
            onChangeCurrentItem(state);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        if (adapter != null && isViewPagerScroll) {
            onChangeIndicatorSelected(position);
        }

    }


    /**
     * banner实际数据大小
     *
     * @return
     */
    public int getRealPageSize() {
        return pageRealCount;
    }


    /**
     * 当前显示的位置
     *
     * @param position
     * @return 下标从0开始
     */
    private int toSelectedPosition(int position) {
        return (position - 1 + pageRealCount) % pageRealCount;
    }

    /**
     * 改变指示器选中状态
     *
     * @param position
     */
    private void onChangeIndicatorSelected(int position) {
        int indicatorPosition = position;
        if (isLoop) {
            indicatorPosition = toSelectedPosition(position);
        }
        if (selectedPositon == indicatorPosition) {
            return;
        }
        selectedPositon = indicatorPosition;
        notifyScrollPositionListener(indicatorPosition);
    }


    /**
     * 通知BannerScrollPositionListener
     *
     * @param indicatorPosition
     */
    private void notifyScrollPositionListener(int indicatorPosition) {
        for (OnBannerScrollPositionListener listener : scrollPositionListeners) {
            if (listener != null) {
                listener.onBannerPageSelected(indicatorPosition);
            }
        }
    }


    /**
     * 改变当前显示的页面
     *
     * @param state
     */
    private void onChangeCurrentItem(int state) {
        switch (state) {
            case ViewPager.SCROLL_STATE_IDLE://No operation
                isStart = true;
                if (currentItem == 0) {
                    viewPager.setCurrentItem(pageRealCount, false);
                } else if (currentItem == pageRealCount + 1) {
                    viewPager.setCurrentItem(1, false);
                }
                break;
            case ViewPager.SCROLL_STATE_DRAGGING://start Sliding
                isStart = false;
                if (currentItem == pageRealCount + 1) {
                    viewPager.setCurrentItem(1, false);
                } else if (currentItem == 0) {
                    viewPager.setCurrentItem(pageRealCount, false);
                }
                break;

            case 2://end Sliding
                isStart = true;
                break;
        }
        currentItem = viewPager.getCurrentItem();
    }


    /**
     * 回收Bitmap
     *
     * @param
     */
    public void onRecycleBannerBitmap() {
        if (adapter == null) {
            return;
        }
        for (View view : adapter.getCacheView().values()) {
            Object temp = view.getTag();
            if (temp == null || !(temp instanceof OnRecycleImageListener)) {
                continue;
            }
            if (mOnRecycleBitmapListener != null) {
                OnRecycleImageListener callBack = (OnRecycleImageListener) temp;
                mOnRecycleBitmapListener.recycleImageBitmap(callBack.getRecycleImage());
            }
        }
    }


    public Banner addBannerScrollPositionListener(OnBannerScrollPositionListener listener) {
        this.scrollPositionListeners.add(listener);
        return this;
    }

    public Banner bindIndicatorView(OnBannerScrollPositionListener listener) {
        return addBannerScrollPositionListener(listener);
    }

    public Banner setCurrentItem(int currentItem) {
        this.currentItem = currentItem;
        return this;
    }

    public Banner setCurrentPage(int currentItem) {
        viewPager.setCurrentItem(currentItem);
        return this;
    }

    public void releaseBanner() {
        Timers.killAll();
    }

    public int getCurrentPage() {
        return viewPager.getCurrentItem();
    }

    public BannerViewPager getViewInterfacePager() {
        return viewPager;
    }

}
