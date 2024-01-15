package io.ganguo.rx;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import io.ganguo.gtest.TestDes;
import io.ganguo.library.GGLibraryTestBase;
import io.ganguo.utils.util.log.Logger;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.observers.TestSubscriber;

/**
 * 单元测试 - RxCollections
 * Created by Lynn on 11/17/16.
 */

public class RxCollectionsTest extends GGLibraryTestBase {
    @Test
    @TestDes("test filterNotEmpty")
    public void testFilterNotEmpty() {
        TestSubscriber<List> subscriber = new TestSubscriber<>();
        List<Integer> testList = new ArrayList<>();
        Observable.just(testList)
                .compose(RxCollections.<List>filterNotEmpty())
                .doOnNext(new Action1<List>() {
                    @Override
                    public void call(List list) {
                        asTrue(list.isEmpty());
                    }
                })
                .subscribe(subscriber);

        subscriber.assertCompleted();
        subscriber.assertNoErrors();

        TestSubscriber<List> subscriber2 = new TestSubscriber<>();
        List<Integer> testList2 = new ArrayList<>();
        testList2.add(1);
        Observable.just(testList2)
                .compose(RxCollections.<List<Integer>>filterNotEmpty())
                .doOnNext(new Action1<List<Integer>>() {
                    @Override
                    public void call(List<Integer> integers) {
                        asFalse(integers.isEmpty());
                    }
                })
                .subscribe(subscriber2);

        subscriber2.assertNoErrors();
        subscriber2.assertCompleted();
    }

    @Test
    @TestDes("test emitItems")
    public void testEmitItems() {
        TestSubscriber<Integer> subscriber = new TestSubscriber<>();
        //空列表
        List<Integer> testList = new ArrayList<>();

        Observable.just(testList)
                .compose(RxCollections.<Integer, List<Integer>>emitItems())
                .subscribe(subscriber);

        subscriber.assertNoErrors();
        subscriber.assertCompleted();

        List<Integer> testList2 = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            testList2.add(i);
        }

        TestSubscriber<Integer> subscriber2 = new TestSubscriber<>();
        Observable.just(testList2)
                .compose(RxCollections.<Integer, List<Integer>>emitItems())
                .doOnNext(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        Logger.d("RxCollections: " + integer);
                    }
                })
                .subscribe(subscriber2);

        subscriber2.assertNoErrors();
        subscriber2.assertCompleted();
        subscriber2.assertValues(0, 1, 2, 3, 4);
    }

    @Test
    @TestDes("test emitItems dataTransformer")
    public void testEmitItemsWithDataTransformer() {
        TestSubscriber<String> subscriber = new TestSubscriber<>();
        List<Integer> testList = new ArrayList<>();
        Observable.just(testList)
                .compose(RxCollections.emitItems(new Func1<List<Integer>, Collection<String>>() {
                    @Override
                    public List<String> call(List<Integer> integers) {
                        final List<String> result = new ArrayList<>();
                        for (int i = 0; i < integers.size(); i++) {
                            result.add(integers.get(i) + "");
                        }
                        return result;
                    }
                }))
                .subscribe(subscriber);

        subscriber.assertNoErrors();
        subscriber.assertCompleted();
        //empty list
        subscriber.assertValues();

        TestSubscriber<String> subscriber2 = new TestSubscriber<>();

        List<Integer> testList2 = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            testList2.add(i);
        }

        Observable.just(testList2)
                .compose(RxCollections.emitItems(new Func1<List<Integer>, Collection<String>>() {
                    @Override
                    public List<String> call(List<Integer> integers) {
                        final List<String> result = new ArrayList<>();
                        for (int i = 0; i < integers.size(); i++) {
                            result.add(integers.get(i) + "");
                        }
                        return result;
                    }
                }))
                .subscribe(subscriber2);

        subscriber2.assertNoErrors();
        subscriber2.assertCompleted();
        subscriber2.assertValues("0", "1", "2", "3", "4");
    }

    @Test
    @TestDes("test cast")
    public void testCast() {
        TestSubscriber<List> subscriber = new TestSubscriber<>();

        List<Integer> testList = new ArrayList<>();

        Observable.just(testList)
                .compose(RxCollections.cast(new Func1<Integer, String>() {
                    @Override
                    public String call(Integer integer) {
                        return integer + "";
                    }
                }))
                .subscribe(subscriber);
        subscriber.assertNoErrors();
        subscriber.assertCompleted();
        //empty
        subscriber.assertValues(new ArrayList<String>());

        TestSubscriber<List> subscriber2 = new TestSubscriber<>();

        Observable.just(Arrays.asList(0, 1, 2, 3, 4))
                .compose(RxCollections.cast(new Func1<Integer, String>() {
                    @Override
                    public String call(Integer integer) {
                        return integer + "";
                    }
                }))
                .subscribe(subscriber2);
        subscriber2.assertNoErrors();
        subscriber2.assertCompleted();
        subscriber2.assertValues(Arrays.asList("0", "1", "2", "3", "4"));
    }
}
