package io.ganguo.http.base;


/**
 * <p>
 * BaseApiImpl - 基类
 * </p>
 * Created by leo on 2018/7/30.
 */
public abstract class BaseApiImpl<T> {
    protected T apiModule;

    protected BaseApiImpl() {
        apiModule = createApiModule();
    }

    /**
     * function：create api module
     *
     * @return
     */
    protected abstract T createApiModule();

    /**
     * function：get api module
     *
     * @return
     */
    public T getApiModule() {
        if (apiModule == null) {
            apiModule = createApiModule();
        }
        return apiModule;
    }

}
