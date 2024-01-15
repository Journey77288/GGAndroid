package io.ganguo.app.helper.activity

/**
 * <pre>
 *     author : leo
 *     time   : 2020/05/18
 *     desc   : MutableList 包装接口
 * </pre>`
 */
interface ActivitiesContainer<T> : MutableList<T> {
    val container: MutableList<T>


    override val size: Int
        get() = container.size

    override fun contains(element: T): Boolean {
        return container.contains(element)
    }

    override fun containsAll(elements: Collection<T>): Boolean {
        return container.containsAll(elements)
    }

    override fun get(index: Int): T {
        return container[index]
    }

    override fun indexOf(element: T): Int {
        return container.indexOf(element)
    }

    override fun isEmpty(): Boolean {
        return container.isEmpty()
    }

    override fun iterator(): MutableIterator<T> {
        return container.iterator()
    }

    override fun lastIndexOf(element: T): Int {
        return container.lastIndexOf(element)
    }

    override fun add(element: T): Boolean {
        return container.add(element)
    }

    override fun add(index: Int, element: T) {
        container.add(index, element)
    }

    override fun addAll(index: Int, elements: Collection<T>): Boolean {
        return container.addAll(index, elements)
    }

    override fun addAll(elements: Collection<T>): Boolean {
        return container.addAll(elements)
    }

    override fun clear() {
        container.clear()
    }

    override fun listIterator(): MutableListIterator<T> {
        return container.listIterator()
    }

    override fun listIterator(index: Int): MutableListIterator<T> {
        return container.listIterator(index)
    }

    override fun remove(element: T): Boolean {
        return container.remove(element)
    }

    override fun removeAll(elements: Collection<T>): Boolean {
        return container.removeAll(elements)
    }

    override fun removeAt(index: Int): T {
        return container.removeAt(index)
    }


    override fun retainAll(elements: Collection<T>): Boolean {
        return container.retainAll(elements)
    }

    override fun set(index: Int, element: T): T {
        return container.set(index, element)
    }


    override fun subList(fromIndex: Int, toIndex: Int): MutableList<T> {
        return container.subList(fromIndex, toIndex)
    }
}
