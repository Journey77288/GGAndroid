package io.ganguo.library.ui.adapter;

import java.util.List;

/**
 * Created by Tony on 4/8/15.
 */
public interface IListAdapter<T> {
    void setList(List<T> list);

    List<T> getList();

    void addAll(List<T> list);

    void add(T item);

    T getItem(int position);

    boolean contains(T item);

    void remove(T item);

    void remove(int position);

    void clear();

    void notifyDataSetChanged();
}
