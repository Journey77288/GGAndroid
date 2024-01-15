package io.ganguo.library.ui.activity;

import org.junit.Test;
import org.robolectric.shadows.support.v4.SupportFragmentController;

import io.ganguo.gtest.TestDes;
import io.ganguo.library.GGLibraryTestBase;
import io.ganguo.library.ui.fragment.TestFragment;

/**
 * 单元测试 - BaseFragmentActivity
 * TODO 要引用对应的moduel资源文件需要更改 @Config下的packageName
 * Created by Lynn on 11/2/16.
 */
public class BaseFragmentActivityTest extends GGLibraryTestBase {

    @Test
    @TestDes("test BaseFragmentActivity")
    public void testBaseFragmentActivity() {
        final TestFragment testFragment = new TestFragment();
        TestFragment attachedFragment = SupportFragmentController.of(testFragment, TestFragmentActivity.class)
                .create()
                .visible()
                .get();

        asNotNull(attachedFragment);
        asNotNull(attachedFragment.getActivity());
        asEquals(attachedFragment.getActivity().getClass(), TestFragmentActivity.class);
    }
}
