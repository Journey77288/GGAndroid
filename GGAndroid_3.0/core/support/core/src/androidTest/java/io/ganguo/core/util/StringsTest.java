package io.ganguo.core.util;

import java.util.List;

import io.ganguo.core.ApplicationTest;
import io.ganguo.core.R;
import io.ganguo.utils.util.Assets;
import io.ganguo.utils.util.Strings;

/**
 * Created by Tony on 9/30/15.
 */
public class StringsTest extends ApplicationTest {

    public void testAssets() {
        String str = Assets.getString(getContext(), "pro_gg.txt");
        assertEquals("this is line 0.this is line 1.", str);

        List<String> arr = Assets.getStringList(getContext(), "pro_gg.txt");
        assertEquals("this is line 0.", arr.get(0));
        assertEquals("this is line 1.", arr.get(1));
    }

    public void testRaw() {
        String str = Strings.getStringFromRaw(getContext(), R.raw.pro_gg);
        assertEquals("this is line 3.this is line 4.", str);

        List<String> arr = Strings.getStringListFromRaw(getContext(), R.raw.pro_gg);
        assertEquals("this is line 3.", arr.get(0));
        assertEquals("this is line 4.", arr.get(1));
    }
}
