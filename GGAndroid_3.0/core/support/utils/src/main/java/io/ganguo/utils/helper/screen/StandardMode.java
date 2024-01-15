package io.ganguo.utils.helper.screen;

/**
 * <pre>
 *     author : leo
 *     time   : 2019/09/11
 *     desc   : 屏幕适配基准模式，宽度/高度，一般用宽度即可
 * </pre>
 */
public enum StandardMode {
    DESIGN_WIDTH("Design Width"), DESIGN_HEIGHT("Design height");
    private String name;

    StandardMode(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
