package io.ganguo.adapter

import android.content.Context
import androidx.databinding.ViewDataBinding

/**
 * <pre>
 * author : leo
 * time   : 2020/05/23
 * desc   : RecyclerView List Adapter
 * </pre>
 * @param T
 * @param B : ViewDataBinding
 * @property data MutableList<T>
 * @property size Int
 * @constructor
 */
abstract class RecyclerListAdapter<T, B : ViewDataBinding>(context: Context) : RecyclerBindingAdapter<B>(context), MutableList<T> {
    var data: ArrayList<T> = ArrayList()
    override var size: Int = 0
        get() = itemCount

    override fun getItemCount(): Int {
        return data.size
    }


    override fun contains(element: T): Boolean {
        return data.contains(element)
    }

    override fun containsAll(elements: Collection<T>): Boolean {
        return data.containsAll(elements)
    }

    override fun get(index: Int): T {
        return data[index]
    }

    override fun indexOf(element: T): Int {
        return data.indexOf(element)
    }

    override fun isEmpty(): Boolean {
        return data.isEmpty()
    }

    override fun iterator(): MutableIterator<T> {
        return data.iterator()
    }

    override fun lastIndexOf(element: T): Int {
        return data.lastIndexOf(element)
    }

    override fun add(element: T): Boolean {
        return data.add(element)
    }


    override fun addAll(elements: Collection<T>): Boolean {
        return data.addAll(elements)
    }

    override fun clear() {
        data.clear()
    }

    override fun listIterator(): MutableListIterator<T> {
        return data.listIterator()
    }

    override fun listIterator(index: Int): MutableListIterator<T> {
        return data.listIterator(index)
    }


    override fun removeAll(elements: Collection<T>): Boolean {
        return data.removeAll(elements)
    }


    override fun retainAll(elements: Collection<T>): Boolean {
        return data.retainAll(elements)
    }

    override fun subList(fromIndex: Int, toIndex: Int): MutableList<T> {
        return data.subList(fromIndex, toIndex)
    }

    override fun add(index: Int, element: T) {
        return data.add(index, element)
    }

    override fun addAll(index: Int, elements: Collection<T>): Boolean {
        return data.addAll(index, elements)
    }

    override fun remove(element: T): Boolean {
        return data.remove(element)
    }

    override fun removeAt(index: Int): T {
        return data.removeAt(index)
    }


    override fun set(index: Int, element: T): T {
        return data.set(index, element)
    }

}