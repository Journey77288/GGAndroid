package io.ganguo.rx;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import io.ganguo.gtest.TestDes;
import io.ganguo.library.GGLibraryTestBase;
import rx.Observable;
import rx.observers.TestSubscriber;

/**
 * 单元测试 - RxFilter
 * Created by Lynn on 11/17/16.
 */

public class RxFilterTest extends GGLibraryTestBase {
    @Test
    @TestDes("test filterNotNull")
    public void testFilterNotNull() {
        final TestSubscriber<List> testSubscriber = new TestSubscriber<>();

        Observable.just(null, Arrays.asList(), Arrays.asList(1, 2))
                .compose(RxFilter.<List>filterNotNull())
                .subscribe(testSubscriber);

        testSubscriber.assertCompleted();
        testSubscriber.assertNoErrors();
        testSubscriber.assertValues(Arrays.asList(), Arrays.asList(1, 2));
    }

    @Test
    @TestDes("test filterNotNull")
    public void testFilterNull() {
        final TestSubscriber<List> testSubscriber = new TestSubscriber<>();

        Observable.just(null, Arrays.asList(), Arrays.asList(1, 2))
                .compose(RxFilter.<List>filterNull())
                .subscribe(testSubscriber);

        testSubscriber.assertCompleted();
        testSubscriber.assertNoErrors();
        final List nullList = null;
        testSubscriber.assertValues(nullList);
    }
}
