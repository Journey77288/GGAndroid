package io.ganguo.core;


import io.ganguo.utils.util.Files;
import io.ganguo.log.Logger;

/**
 * Created by Tony on 10/4/15.
 *
 * /data/data/io.ganguo.library.test
 *
 */
public class TestConfig extends ApplicationTest {

    public void testConfigInfo() {
        Logger.d(Config.getDataPath());
        Logger.d(Config.getImagePath());
        Logger.d(Config.getImageCachePath());

        Logger.d("AppPath: " + Config.getDataPath());
        Logger.d("DateSize: " + Config.getDataSize());
        Logger.d("SDCardMounted: " + Files.isSdCardMounted());
        Logger.d("AvailableSize: " + Files.getSDCardAvailableSize());
    }

}
