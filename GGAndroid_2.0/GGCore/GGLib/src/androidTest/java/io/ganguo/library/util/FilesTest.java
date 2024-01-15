package io.ganguo.library.util;

import java.io.File;
import java.io.IOException;

import io.ganguo.library.ApplicationTest;
import io.ganguo.library.Config;
import io.ganguo.utils.util.Assets;

/**
 * Created by Tony on 10/4/15.
 */
public class FilesTest extends ApplicationTest {

    public void testAsset() throws IOException {
        File to = new File(Config.getDataPath(), "assert_test");
        Assets.copyFile(getContext(), "test", to);
    }

}
