package io.ganguo.library.util;

import io.ganguo.library.ApplicationTest;
import io.ganguo.utils.util.Systems;
import io.ganguo.utils.util.log.Logger;

/**
 * Created by Wilson on 8/12/15.
 */
public class SystemsTest extends ApplicationTest {

    public void testScreen() {
        boolean isLandscape = Systems.isLandscape(getContext());
        boolean isPortrait = Systems.isPortrait(getContext());
        boolean isTablet = Systems.isTablet(getContext());

        Logger.e("isLandscape:" + isLandscape + ", isPortrait:" + isPortrait + ", isTablet:" + isTablet);
    }
}
