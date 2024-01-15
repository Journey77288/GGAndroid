package io.ganguo.utils.common.base;

import android.app.Activity;
import android.support.annotation.NonNull;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Stack;

import io.ganguo.utils.util.Collections;

/**
 * <p>
 * Activity管理工具基类
 * </P>
 * Created by leo on 2018/6/15.
 */
public class BaseActivityHelper implements List<Activity> {
    protected static final Stack<Activity> activityStack = new Stack<>();//Activity记录栈

    @Override
    public int size() {
        return activityStack.size();
    }

    @Override
    public boolean isEmpty() {
        return Collections.isEmpty(activityStack);
    }

    @Override
    public boolean contains(Object o) {
        return Collections.isContains(activityStack, o);
    }

    @NonNull
    @Override
    public Iterator<Activity> iterator() {
        return activityStack.iterator();
    }

    @NonNull
    @Override
    public Object[] toArray() {
        return activityStack.toArray();
    }

    @NonNull
    @Override
    public <T> T[] toArray(@NonNull T[] a) {
        return a == null ? a : activityStack.toArray(a);
    }

    @Override
    public boolean add(Activity activity) {
        return activity == null ? false : activityStack.add(activity);
    }

    @Override
    public boolean remove(Object o) {
        return o == null ? false : activityStack.remove(o);
    }

    @Override
    public boolean containsAll(@NonNull Collection<?> c) {
        return Collections.isEmpty(c) ? false : activityStack.contains(c);
    }

    @Override
    public boolean addAll(@NonNull Collection<? extends Activity> c) {
        return Collections.isEmpty(c) ? false : activityStack.addAll(c);
    }

    @Override
    public boolean addAll(int index, @NonNull Collection<? extends Activity> c) {
        return Collections.isEmpty(c) ? false : activityStack.addAll(index, c);
    }

    @Override
    public boolean removeAll(@NonNull Collection<?> c) {
        return Collections.isEmpty(c) ? false : activityStack.removeAll(c);
    }

    @Override
    public boolean retainAll(@NonNull Collection<?> c) {
        return Collections.isEmpty(c) ? false : activityStack.retainAll(c);
    }

    @Override
    public void clear() {
        activityStack.clear();
    }

    @Override
    public Activity get(int index) {
        return index >= size() ? null : activityStack.get(index);
    }

    @Override
    public Activity set(int index, Activity element) {
        return element == null ? null : activityStack.set(index, element);
    }

    @Override
    public void add(int index, Activity element) {
        if (element == null) {
            return;
        }
        activityStack.add(index, element);
    }

    @Override
    public Activity remove(int index) {
        return index >= size() ? null : activityStack.remove(index);
    }

    @Override
    public int indexOf(Object o) {
        return o == null ? -1 : activityStack.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return o == null ? -1 : activityStack.lastIndexOf(o);
    }

    @NonNull
    @Override
    public ListIterator<Activity> listIterator() {
        return activityStack.listIterator();
    }

    @NonNull
    @Override
    public ListIterator<Activity> listIterator(int index) {
        return activityStack.listIterator(index);
    }

    @NonNull
    @Override
    public List<Activity> subList(int fromIndex, int toIndex) {
        return fromIndex >= size() || toIndex >= size() ? null : activityStack.subList(fromIndex, toIndex);
    }

    public static Stack<Activity> getActivityStack() {
        return activityStack;
    }
}
