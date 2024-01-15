package io.ganguo.vmodel.callback;

/**
 * PopupWindow Interface
 * Created by leo on 2018/10/20.
 */
public interface IPopupWindowInterface {
    boolean isWindowWidthFull();

    boolean isWindowHeightFull();

    /**
     * function：是否允许点击外部关闭PopupWindow
     *
     * @return
     */
    boolean isTouchOutsideDismiss();
}
