package io.ganguo.library.ui.adapter.v7.callback;

/**
 * <p>
 * DiffComparator 数据比较接口,配合AdapterDiffCallback
 *
 * @see {@link  AdapterDiffCallback}
 * </p>
 * Created by leo on 2018/8/23.
 */
public interface IDiffComparator<T> {
    /**
     * function：数据比较、判断函数
     *
     * @param t
     * @return
     */
    boolean isDataEquals(T t);

    /**
     * function：待比较数据的对象引用
     *
     * @return
     * @see {@link #isDataEquals(Object)} 函数用于判断比较的数据对象
     */
    T getDiffCompareObject();
}
