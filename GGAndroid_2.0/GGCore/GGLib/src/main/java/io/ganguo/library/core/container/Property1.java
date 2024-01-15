package io.ganguo.library.core.container;

/**
 * Object 容器
 * Created by Roger on 7/26/16.
 */
public class Property1<T> {

    private T property;

    public Property1(T initialValue) {
        this.property = initialValue;
    }

    public Property1() {

    }

    public T get() {
        return property;
    }

    public void set(T property) {
        this.property = property;
    }

    public boolean isValid() {
        return property != null;
    }

    @Override
    public String toString() {
        return "Property1{" +
                "property=" + property +
                '}';
    }
}
