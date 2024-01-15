package io.ganguo.tab.callback;

/**
 * <pre>
 *   @author : leo
 *   @time   : 2020/07/14
 *   @desc   : TabLayoutMediator
 * </pre>
 */
public interface TabLayoutMediator {

    void onPageScrolled(int position, float positionOffset, int positionOffsetPixels);

    void onPageRemove(int position);

    void onPageScrollStateChanged(int state);

    void onPageSelected(int position);

}
