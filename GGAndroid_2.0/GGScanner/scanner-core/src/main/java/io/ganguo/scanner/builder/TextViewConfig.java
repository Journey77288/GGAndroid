package io.ganguo.scanner.builder;

import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.Dimension;
import android.view.Gravity;

/**
 * <p>
 * TextView参数配置
 * </p>
 * Created by leo on 2018/8/7.
 */
public class TextViewConfig {
    public Builder builder;

    private TextViewConfig() {
    }

    public int getGravity() {
        return builder.gravity;
    }

    public int getMaxLines() {
        return builder.maxLines;
    }

    public int getColor() {
        return builder.color;
    }

    public int getSize() {
        return builder.size;
    }


    public TextViewConfig(Builder builder) {
        this.builder = builder;
    }

    public static class Builder {
        private int gravity = Gravity.CENTER;
        private int maxLines = 1;
        @ColorInt
        private int color = Color.BLACK;
        @Dimension
        private int size;

        public TextViewConfig build() {
            return new TextViewConfig(this);
        }


        public Builder setGravity(int gravity) {
            this.gravity = gravity;
            return this;
        }

        public Builder setMaxLines(int maxLines) {
            this.maxLines = maxLines;
            return this;
        }

        public Builder setColor(@ColorInt int color) {
            this.color = color;
            return this;
        }

        public Builder setSize(int size) {
            this.size = size;
            return this;
        }

    }


}
