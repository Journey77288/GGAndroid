package ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Tony on 3/29/15.
 */
public class CustomView extends View {

    final static String NS_ANDROID = "http://schemas.android.com/apk/res/android";

    public CustomView(Context context) {
        this(context, null);
    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

}
