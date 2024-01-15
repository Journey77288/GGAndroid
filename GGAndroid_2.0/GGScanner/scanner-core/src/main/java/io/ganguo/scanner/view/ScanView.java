package io.ganguo.scanner.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.AttrRes;
import android.support.annotation.ColorInt;
import android.support.annotation.Dimension;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.util.ArrayList;

import io.ganguo.scanner.R;
import io.ganguo.scanner.bean.ScannerConfig;
import io.ganguo.utils.common.ResHelper;


/**
 * ScanView -- 二维码/一维码 扫描框
 */
public class ScanView extends FrameLayout {
    private LineView scanLine;
    private TranslateAnimation animation;
    private FrameLayout flScan;
    //扫描类型
    private CornerView cnv_left_top;
    private CornerView cnv_left_bottom;
    private CornerView cnv_right_top;
    private CornerView cnv_right_bottom;
    private View view_top;
    private View view_left;
    private View view_right;
    private View view_bottom;
    private ArrayList<CornerView> cornerViews;
    //扫描线动画间隔时长
    private long lineAnimationDuration = 3000;
    //边框View
    @ColorInt
    private int cornerColor = Color.GREEN;
    @Dimension
    private int cornerWidth;
    @Dimension
    private int cornerSize;//CornerView大小
    @Dimension
    private int scanWidth;
    @Dimension
    private int scanHeight;
    @Dimension
    private int scanLineHeight;
    @Dimension
    private int scanLineWidth;
    @Dimension
    private int scanLineMarginLeft;
    @Dimension
    private int scanLineMarginRight;
    @Dimension
    private int scanLineMarginTop;
    @Dimension
    private int scanLineMarginBottom;
    @ColorInt
    private int scanLineColor;
    @ColorInt
    private int scanBgColor;
    @Dimension
    private int scanRectMarginTop;

    public ScanView(@NonNull Context context) {
        this(context, null);
    }

    public ScanView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScanView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttributeSet(context, attrs);
        initView(context);
        initScanLineAnimation();
    }

    /**
     * function:读取控件自定义属性
     *
     * @param context
     * @param attrs
     */
    private void initAttributeSet(Context context, @Nullable AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.zbar_view);
        cornerColor = a.getColor(R.styleable.zbar_view_scan_corner_color, Color.GREEN);
        cornerWidth = a.getDimensionPixelOffset(R.styleable.zbar_view_scan_corner_width, ResHelper.getDimensionPixelOffsets(R.dimen.dp_5));
        cornerSize = a.getDimensionPixelOffset(R.styleable.zbar_view_scan_corner_size, ResHelper.getDimensionPixelOffsets(R.dimen.dp_20));

        scanBgColor = a.getColor(R.styleable.zbar_view_scan_bg_color, ResHelper.getColor(R.color.black_translucent));
        scanWidth = a.getDimensionPixelOffset(R.styleable.zbar_view_scan_rect_width, -1);
        scanHeight = a.getDimensionPixelOffset(R.styleable.zbar_view_scan_rect_height, -1);
        scanRectMarginTop = a.getDimensionPixelOffset(R.styleable.zbar_view_scan_rect_margin_top, ResHelper.getDimensionPixelOffsets(R.dimen.dp_200));

        int dp3 = ResHelper.getDimensionPixelOffsets(R.dimen.dp_3);
        scanLineColor = a.getColor(R.styleable.zbar_view_scan_line_color, Color.GREEN);
        scanLineWidth = a.getDimensionPixelOffset(R.styleable.zbar_view_scan_line_width, ViewGroup.LayoutParams.MATCH_PARENT);
        scanLineHeight = a.getDimensionPixelOffset(R.styleable.zbar_view_scan_line_height, dp3);
        scanLineMarginLeft = a.getDimensionPixelOffset(R.styleable.zbar_view_scan_line_margin_left, 0);
        scanLineMarginRight = a.getDimensionPixelOffset(R.styleable.zbar_view_scan_line_margin_right, 0);
        scanLineMarginTop = a.getDimensionPixelOffset(R.styleable.zbar_view_scan_line_margin_top, dp3);
        scanLineMarginBottom = a.getDimensionPixelOffset(R.styleable.zbar_view_scan_line_margin_left, dp3);


        checkScanSize();
        a.recycle();
    }

    /**
     * function：检查ScanView大小，是否有正常配置
     */
    private void checkScanSize() {
        if (scanWidth > 0 && scanHeight > 0) {
            return;
        }
        int width;
        int height;
        //默认为正方形扫描框，如需要其他尺寸，则可以自定义
        width = scanWidth != -1 ? scanWidth : ResHelper.getDimensionPixelOffsets(R.dimen.dp_200);
        height = scanHeight != -1 ? scanHeight : width;
        scanWidth = width;
        scanHeight = height;
    }


    /**
     * function：init view
     *
     * @param mContext
     */
    private void initView(Context mContext) {
        View scan_view = View.inflate(mContext, R.layout.view_scan, this);

        view_top = scan_view.findViewById(R.id.view_top);
        view_left = scan_view.findViewById(R.id.view_left);
        view_right = scan_view.findViewById(R.id.view_right);
        view_bottom = scan_view.findViewById(R.id.view_bottom);

        cnv_left_top = scan_view.findViewById(R.id.cnv_left_top);
        cnv_left_bottom = scan_view.findViewById(R.id.cnv_left_bottom);
        cnv_right_top = scan_view.findViewById(R.id.cnv_right_top);
        cnv_right_bottom = scan_view.findViewById(R.id.cnv_right_bottom);

        scanLine = scan_view.findViewById(R.id.iv_scan_line);
        flScan = scan_view.findViewById(R.id.fl_scan);
        setScanRectMarginTop(scanRectMarginTop);
        setScanViewSize(scanWidth, scanHeight);
        initScanBackground();
        initScanLineView();
        initCornerView();
    }

    /**
     * function：init Scan bg
     */
    protected void initScanBackground() {
        view_top.setBackgroundColor(scanBgColor);
        view_right.setBackgroundColor(scanBgColor);
        view_bottom.setBackgroundColor(scanBgColor);
        view_left.setBackgroundColor(scanBgColor);
    }


    /**
     * function：init ScanLineView
     */
    protected void initScanLineView() {
        scanLine.setLineColor(scanLineColor);
        setScanLineSize(scanLineWidth, scanLineHeight);
        setScanLineMargins(scanLineMarginLeft, scanLineMarginTop, scanLineMarginRight, scanLineMarginBottom);
    }


    /**
     * function：init CornerView
     */
    protected void initCornerView() {
        cornerViews = new ArrayList<>();
        cornerViews.add(cnv_left_top);
        cornerViews.add(cnv_left_bottom);
        cornerViews.add(cnv_right_top);
        cornerViews.add(cnv_right_bottom);
        //设置CornerView位置
        cnv_left_top.setCornerGravity(CornerView.LEFT_TOP);
        cnv_left_bottom.setCornerGravity(CornerView.LEFT_BOTTOM);
        cnv_right_top.setCornerGravity(CornerView.RIGHT_TOP);
        cnv_right_bottom.setCornerGravity(CornerView.RIGHT_BOTTOM);
        //设置CornerView颜色、宽度
        setCornerColor(cornerColor);
        setCornerLineWidth(cornerWidth);
        setCornerSize(cornerSize);
        requestLayout();
    }


    /**
     * function：设置扫描框闲大小
     */
    public void setScanLineSize(@Dimension int scanLineWidth, @Dimension int scanLineHeight) {
        ViewGroup.LayoutParams params = scanLine.getLayoutParams();
        params.width = scanLineWidth;
        params.height = scanLineHeight;
        scanLine.setLayoutParams(params);
    }

    /**
     * function：设置扫描框闲大小
     */
    public void setScanLineMargins(@Dimension int scanLineMarginLeft, @Dimension int scanLineMarginTop, @Dimension int scanLineMarginRight, @Dimension int scanLineMarginBottom) {
        LayoutParams params = (LayoutParams) scanLine.getLayoutParams();
        params.setMargins(scanLineMarginLeft, scanLineMarginTop, scanLineMarginRight, scanLineMarginBottom);
        scanLine.setLayoutParams(params);
    }

    /**
     * function：设置扫描框大小
     *
     * @param marginTop
     */
    public void setScanRectMarginTop(int marginTop) {
        this.scanRectMarginTop = marginTop;
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view_top.getLayoutParams();
        params.height = marginTop;
        view_top.setLayoutParams(params);
    }

    /**
     * function：设置扫描框大小
     *
     * @param width
     * @param height
     */
    public void setScanViewSize(int width, int height) {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) flScan.getLayoutParams();
        params.width = width;
        params.height = height;
        flScan.setLayoutParams(params);
    }


    /**
     * function：初始化扫描线 动画
     */
    protected void initScanLineAnimation() {
        animation = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.9f);
        animation.setDuration(lineAnimationDuration);
        animation.setRepeatCount(-1);
        animation.setRepeatMode(Animation.RESTART);
    }

    /**
     * function：设置扫描线动画速度
     */
    public void setScanLineAnimationSpeed(long duration) {
        this.lineAnimationDuration = duration;
        animation.setDuration(duration);
    }

    /**
     * function：开始扫描动画
     */
    public void startScan() {
        scanLine.startAnimation(animation);
    }

    /**
     * function：停止扫描动画
     */
    public void onPause() {
        if (scanLine != null) {
            scanLine.clearAnimation();
            scanLine.setVisibility(View.GONE);
        }
    }

    /**
     * function：恢复扫描动画
     */
    public void onResume() {
        if (scanLine != null) {
            scanLine.startAnimation(animation);
        }
    }


    /**
     * function：set CornerView Width
     *
     * @param color
     */
    public void setCornerColor(@ColorInt int color) {
        for (CornerView cornerView : cornerViews) {
            cornerView.setCornerColor(color);
        }
    }

    /**
     * function：set CornerView Width
     *
     * @param px
     */
    public void setCornerLineWidth(int px) {
        for (CornerView cornerView : cornerViews) {
            cornerView.setCornerLineWidth(px);
        }
    }

    /**
     * function：set CornerView size
     *
     * @param px
     */
    public void setCornerSize(int px) {
        for (CornerView cornerView : cornerViews) {
            ViewGroup.LayoutParams params = cornerView.getLayoutParams();
            params.height = px;
            params.width = px;
            cornerView.setLayoutParams(params);
        }
    }


    /**
     * function：设置扫描线颜色
     */
    public void setLineColor(int color) {
        this.scanLine.setLineColor(color);
    }

}
