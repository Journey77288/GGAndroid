package io.ganguo.library.ui.fragment;

import org.junit.Test;
import org.robolectric.shadows.support.v4.SupportFragmentTestUtil;

import io.ganguo.gtest.TestDes;
import io.ganguo.library.GGLibraryTestBase;

/**
 * 单元测试 - BaseFragment
 * Created by Lynn on 11/2/16.
 */

public class BaseFragmentTest extends GGLibraryTestBase {
    @Test
    @TestDes("test BaseFragment")
    public void testBaseFragment() {
        TestFragment testFragment = new TestFragment();
        SupportFragmentTestUtil.startFragment(testFragment);
        asNotNull(testFragment.getActivity());
        asNotNull(testFragment.getView());
    }
}
