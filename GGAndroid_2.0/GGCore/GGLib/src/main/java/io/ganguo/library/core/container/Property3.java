package io.ganguo.library.core.container;

/**
 * Object 容器
 * Created by Roger on 7/26/16.
 */
public class Property3<T1, T2, T3> {

    private T1 o1;
    private T2 o2;
    private T3 o3;

    public Property3(T1 o1, T2 o2, T3 o3) {
        this.o1 = o1;
        this.o2 = o2;
        this.o3 = o3;
    }

    public Property3() {

    }

    public T1 getFirst() {
        return o1;
    }

    public void setFirst(T1 o1) {
        this.o1 = o1;
    }

    public T2 getSecond() {
        return o2;
    }

    public void setSecond(T2 o2) {
        this.o2 = o2;
    }

    public T3 getThird() {
        return o3;
    }

    public void setThird(T3 o3) {
        this.o3 = o3;
    }

    public boolean isValid() {
        return o1 != null && o2 != null && o3 != null;
    }

    @Override
    public String toString() {
        return "Property3{" +
                "o1=" + o1 +
                ", o2=" + o2 +
                ", o3=" + o3 +
                '}';
    }
}
