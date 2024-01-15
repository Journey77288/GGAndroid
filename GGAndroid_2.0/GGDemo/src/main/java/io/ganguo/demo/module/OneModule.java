package io.ganguo.demo.module;

import io.ganguo.demo.entity.one.OneListEntity;
import io.ganguo.demo.http.one.OnesHttpResponse;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * <p>
 * 《一个》开源API
 * </p>
 * Created by leo on 2018/7/30.
 */

public interface OneModule {
    //一个--首页列表
    String API_ONE_LIST = "api/onelist/{id}/0";

    /**
     * function:获取OneList
     *
     * @param id
     * @return
     */
    @GET(API_ONE_LIST)
    Observable<OnesHttpResponse<OneListEntity>> getOneList(@Path("id") String id);
}
