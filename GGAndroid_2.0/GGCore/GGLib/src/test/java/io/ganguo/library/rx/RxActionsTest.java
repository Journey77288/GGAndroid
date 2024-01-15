package io.ganguo.rx;

import android.app.Activity;
import android.content.Intent;

import org.junit.Test;
import org.robolectric.Robolectric;
import org.robolectric.shadows.ShadowActivity;

import io.ganguo.gtest.TestDes;
import io.ganguo.library.GGLibraryTestBase;
import io.ganguo.library.ui.activity.TestActivity;
import rx.Observable;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.observers.TestSubscriber;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.robolectric.Shadows.shadowOf;

/**
 * 单元测试 - RxActions
 * Created by Lynn on 11/3/16.
 */

public class RxActionsTest extends GGLibraryTestBase {

    @Test
    @TestDes("测试RxJava自定义RxActions, printThrowable")
    public void testPrintThrowable() {
        TestSubscriber<Integer> testSubscriber = new TestSubscriber<>();
        //TODO 注意，log会输出exception的调用链，但是测试是顺利通过的，输出详细可以看printThrowable实现
//        expect(AssertionError.class);
        Observable.just(1)
                .doOnNext(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        integer /= 0;
                    }
                })
                .doOnError(RxActions.printThrowable())
                .subscribe(testSubscriber);

        testSubscriber.assertError(ArithmeticException.class);
        testSubscriber.assertNotCompleted();
    }

    @Test
    @TestDes("测试RxJava自定义RxActions, printVariable")
    public void testPrintVariable() {
        TestSubscriber<Integer> testSubscriber = new TestSubscriber<>();

        Observable.just(1)
                .doOnNext(RxActions.<Integer>printVariable())
                .subscribe(testSubscriber);

        testSubscriber.assertNoErrors();
        testSubscriber.assertCompleted();
    }

    @Test
    @TestDes("测试RxJava自定义RxActions, startActivity")
    public void testStartActivity() {
        TestSubscriber<Integer> testSubscriber = new TestSubscriber<>();

        TestActivity testActivity = Robolectric.buildActivity(TestActivity.class).create().get();
        final ShadowActivity shadowActivity = shadowOf(testActivity);
        final Intent intent = new Intent(testActivity, TestActivity.class);

        Observable.just(1)
                .doOnCompleted(RxActions.startActivity(testActivity, intent))
                .doOnCompleted(new Action0() {
                    @Override
                    public void call() {
                        asThat(shadowActivity.getNextStartedActivity(), equalTo(intent));
                    }
                })
                .subscribe(testSubscriber);

        testSubscriber.assertNoErrors();
        testSubscriber.assertCompleted();
    }

    @Test
    @TestDes("测试RxJava自定义RxActions, startActivityForResult")
    public void testStartActivityForResult() {
        TestSubscriber<Integer> testSubscriber = new TestSubscriber<>();

        final TestActivity testActivity = Robolectric.buildActivity(TestActivity.class).get();
        final ShadowActivity shadowActivity = shadowOf(testActivity);

        final Intent intent = new Intent(testActivity, TestActivity.class);

        Observable.just(1)
                .doOnCompleted(RxActions.startActivityForResult(testActivity, intent, TestActivity.TEST_REQUEST_CODE))
                .doOnCompleted(new Action0() {
                    @Override
                    public void call() {
                        asThat(shadowActivity.getNextStartedActivity(), equalTo(intent));

                        final String testKey = "testKey";
                        final String testValue = "testValue";
                        shadowActivity.receiveResult(intent,
                                Activity.RESULT_OK,
                                new Intent().putExtra(testKey, testValue));

                        asTrue(testActivity.mIsGetResult);
                    }
                })
                .subscribe(testSubscriber);

        testSubscriber.assertNoErrors();
        testSubscriber.assertCompleted();
    }

    @Test
    @TestDes("测试RxJava自定义RxActions, finishActivity")
    public void testFinishActivity() {
        TestSubscriber<Integer> testSubscriber = new TestSubscriber<>();

        TestActivity testActivity = Robolectric.buildActivity(TestActivity.class).create().get();
        final ShadowActivity shadowActivity = shadowOf(testActivity);

        Observable.just(1)
                .doOnCompleted(RxActions.finishActivity(testActivity))
                .doOnCompleted(new Action0() {
                    @Override
                    public void call() {
                        asThat(shadowActivity.isFinishing(), equalTo(true));
                    }
                })
                .subscribe(testSubscriber);

        testSubscriber.assertNoErrors();
        testSubscriber.assertCompleted();
    }
}
