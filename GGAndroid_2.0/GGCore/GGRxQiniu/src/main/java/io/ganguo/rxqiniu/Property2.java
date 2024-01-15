package io.ganguo.rxqiniu;

/**
 * Object 容器
 * Created by Roger on 7/26/16.
 */
class Property2<T1, T2> {

    private T1 o1;
    private T2 o2;

    Property2(T1 o1, T2 o2) {
        this.o1 = o1;
        this.o2 = o2;
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

    public boolean isValid() {
        return o1 != null && o2 != null;
    }

    @Override
    public String toString() {
        return "Property2{" +
                "o1=" + o1 +
                ", o2=" + o2 +
                '}';
    }
}
