package io.ganguo.rx;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.ganguo.gtest.TestDes;
import io.ganguo.library.GGLibraryTestBase;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.observers.TestSubscriber;

/**
 * 单元测试 - RxStatement
 * Created by Lynn on 11/17/16.
 */

public class RxStatementTest extends GGLibraryTestBase {
    @Test
    @TestDes("test ifThen 三个参数 condition是Fun")
    public void testIfThenWithThreeArgsAndConditionFun() {
        final TestSubscriber<String> subscriber = new TestSubscriber<>();
        //有转换数据类型
        Observable.just(0, 1, -1)
                .compose(RxStatement.ifThen(new Func1<Integer, Boolean>() {
                    @Override
                    public Boolean call(Integer o) {
                        return o > 0;
                    }
                }, new Func1<Integer, Observable<String>>() {
                    @Override
                    public Observable<String> call(Integer o) {
                        //条件 True 触发
                        return Observable.just(o + "");
                    }
                }, new Func1<Integer, Observable<String>>() {
                    @Override
                    public Observable<String> call(Integer o) {
                        //条件 False 触发
                        return Observable.just("错误");
                    }
                }))
                .subscribe(subscriber);

        subscriber.assertNoErrors();
        subscriber.assertCompleted();
        subscriber.assertValues("错误", "1", "错误");

        //没有转换数据类型
        final TestSubscriber<Integer> subscriber2 = new TestSubscriber<>();
        Observable.just(0, 1, -2)
                .compose(RxStatement.ifThen(new Func1<Integer, Boolean>() {
                    @Override
                    public Boolean call(Integer o) {
                        return o > 0;
                    }
                }, new Action1<Integer>() {
                    @Override
                    public void call(Integer o) {
                        //条件 True 触发
                        asTrue(o > 0);
                    }
                }, new Action1<Integer>() {
                    @Override
                    public void call(Integer o) {
                        //条件 False 触发
                        asTrue(o <= 0);
                    }
                }))
                .subscribe(subscriber2);

        subscriber2.assertNoErrors();
        subscriber2.assertCompleted();
        subscriber2.assertValues(0, 1, -2);
    }

    @Test
    @TestDes("test ifThen 三个参数 condition 是boolean")
    public void testIfThenWithThreeArgs() {
        final boolean isAddToFront = true;
        final boolean isAddToRear = false;

        //转换数据类型
        TestSubscriber<List> subscriber = new TestSubscriber<>();
        Observable.just(new ArrayList<>(Arrays.asList(0, 1, 2)))
                .compose(RxStatement.ifThen(isAddToFront, new Func1<List<Integer>, Observable<List<Integer>>>() {
                    @Override
                    public Observable<List<Integer>> call(List<Integer> integers) {
                        //条件 True 执行
                        integers.add(0, 3);
                        return Observable.just(integers);
                    }
                }, new Func1<List<Integer>, Observable<List<Integer>>>() {
                    @Override
                    public Observable<List<Integer>> call(List<Integer> integers) {
                        //条件 False 执行
                        return Observable.just(integers);
                    }
                }))
                .subscribe(subscriber);

        subscriber.assertNoErrors();
        subscriber.assertCompleted();
        subscriber.assertValues(Arrays.asList(3, 0, 1, 2));

        TestSubscriber<List> subscriber2 = new TestSubscriber<>();
        Observable.just(new ArrayList<>(Arrays.asList(0, 1, 2)))
                .compose(RxStatement.ifThen(isAddToRear, new Func1<List<Integer>, Observable<List<Integer>>>() {
                    @Override
                    public Observable<List<Integer>> call(List<Integer> integers) {
                        //条件 True 执行
                        integers.add(3);
                        return Observable.just(integers);
                    }
                }, new Func1<List<Integer>, Observable<List<Integer>>>() {
                    @Override
                    public Observable<List<Integer>> call(List<Integer> integers) {
                        //条件 False 执行
                        return Observable.just(integers);
                    }
                }))
                .subscribe(subscriber2);

        subscriber2.assertNoErrors();
        subscriber2.assertCompleted();
        subscriber2.assertValues(Arrays.asList(0, 1, 2));

        //没有转换数据类型
        final TestSubscriber<List> subscriber3 = new TestSubscriber<>();
        Observable.just(new ArrayList<>(Arrays.asList(0, 1, 2)))
                .compose(RxStatement.ifThen(isAddToFront, new Action1<List<Integer>>() {
                    @Override
                    public void call(List<Integer> list) {
                        //条件 True 触发
                        list.add(0, 3);
                    }
                }, new Action1<List<Integer>>() {
                    @Override
                    public void call(List<Integer> list) {
                        //条件 False 触发
                        list.add(0, -1);
                    }
                }))
                .subscribe(subscriber3);

        subscriber3.assertNoErrors();
        subscriber3.assertCompleted();
        subscriber3.assertValues(Arrays.asList(3, 0, 1, 2));

        final TestSubscriber<List> subscriber4 = new TestSubscriber<>();
        Observable.just(new ArrayList<>(Arrays.asList(0, 1, 2)))
                .compose(RxStatement.ifThen(isAddToRear, new Action1<List<Integer>>() {
                    @Override
                    public void call(List<Integer> list) {
                        //条件 True 触发
                        list.add(3);
                    }
                }, new Action1<List<Integer>>() {
                    @Override
                    public void call(List<Integer> list) {
                        //条件 False 触发
                        list.add(-1);
                    }
                }))
                .subscribe(subscriber4);

        subscriber4.assertNoErrors();
        subscriber4.assertCompleted();
        subscriber4.assertValues(Arrays.asList(0, 1, 2, -1));
    }

    @Test
    @TestDes("test ifThen 两个参数 condition 是Fun")
    public void testIfThenWithTwoArgsAndConditionFun() {
        //有转换数据类型
        TestSubscriber<Integer> subscriber = new TestSubscriber<>();
        Observable.just(0, 1, 2)
                .compose(RxStatement.ifThen(new Func1<Integer, Boolean>() {
                    @Override
                    public Boolean call(Integer integer) {
                        //TODO 鉴于如果条件不满足，则直接Observable.just(原数据源)的做法，
                        //TODO 不建议通过两参数方法向下游发送不同数据类型的数据
                        return integer > 0;
                    }
                }, new Func1<Integer, Observable<Integer>>() {
                    @Override
                    public Observable<Integer> call(Integer integer) {
                        //条件True触发
                        return Observable.just(integer + 2);
                    }
                }))
                .subscribe(subscriber);

        subscriber.assertNoErrors();
        subscriber.assertCompleted();
        subscriber.assertValues(0, 3, 4);

        //没有转换数据类型
        final TestSubscriber<Integer> subscriber2 = new TestSubscriber<>();
        Observable.just(0, 1, 2)
                .compose(RxStatement.ifThen(new Func1<Integer, Boolean>() {
                    @Override
                    public Boolean call(Integer integer) {
                        return integer > 0;
                    }
                }, new Action1<Integer>() {
                    @Override
                    public void call(Integer o) {
                        asTrue(o > 0);
                    }
                }))
                .subscribe(subscriber2);

        subscriber2.assertNoErrors();
        subscriber2.assertCompleted();
        subscriber2.assertValues(0, 1, 2);
    }

    @Test
    @TestDes("test ifThen 两个参数 condition 是Fun")
    public void testIfThenWithTwoArgs() {
        final boolean isAdd = true;
        final boolean isAddFalse = false;

        //有转换数据类型
        TestSubscriber<Integer> subscriber = new TestSubscriber<>();
        Observable.just(0, 1, 2)
                .compose(RxStatement.ifThen(isAdd, new Func1<Integer, Observable<Integer>>() {
                    @Override
                    public Observable<Integer> call(Integer integer) {
                        //条件True触发
                        return Observable.just(integer + 2);
                    }
                }))
                .subscribe(subscriber);

        subscriber.assertNoErrors();
        subscriber.assertCompleted();
        subscriber.assertValues(2, 3, 4);

        TestSubscriber<Integer> subscriber2 = new TestSubscriber<>();
        Observable.just(0, 1, 2)
                .compose(RxStatement.ifThen(isAddFalse, new Func1<Integer, Observable<Integer>>() {
                    @Override
                    public Observable<Integer> call(Integer integer) {
                        //条件True触发
                        return Observable.just(integer + 2);
                    }
                }))
                .subscribe(subscriber2);

        subscriber2.assertNoErrors();
        subscriber2.assertCompleted();
        subscriber2.assertValues(0, 1, 2);

        //没有转换数据类型
        final TestSubscriber<List> subscriber3 = new TestSubscriber<>();
        Observable.just(new ArrayList<>(Arrays.asList(0, 1, 2)))
                .compose(RxStatement.ifThen(isAdd, new Action1<List<Integer>>() {
                    @Override
                    public void call(List<Integer> o) {
                        o.add(0, 3);
                    }
                }))
                .subscribe(subscriber3);

        subscriber3.assertNoErrors();
        subscriber3.assertCompleted();
        subscriber3.assertValues(Arrays.asList(3, 0, 1, 2));
    }

    @Test
    @TestDes("test forEach")
    public void testForEach() {
        TestSubscriber<Integer> testSubscriber = new TestSubscriber<>();

        Observable.just(Arrays.asList("2", "3", "4"))
                .compose(new RxStatement.forEach<String>())
                .map(new Func1<RxStatement.Indexed<String>, Integer>() {
                    @Override
                    public Integer call(RxStatement.Indexed<String> stringIndexed) {
                        return (int) stringIndexed.index();
                    }
                })
                .subscribe(testSubscriber);

        testSubscriber.assertNoErrors();
        testSubscriber.assertCompleted();
        testSubscriber.assertValues(0, 1, 2);
    }
}
