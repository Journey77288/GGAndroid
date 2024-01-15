package io.ganguo.demo.database.helper;


import android.support.annotation.WorkerThread;

import java.util.ArrayList;
import java.util.List;

import io.ganguo.demo.R;
import io.ganguo.demo.database.model.SportModel;
import io.ganguo.utils.common.ResHelper;
import io.objectbox.query.Query;
import io.objectbox.rx.RxQuery;
import io.reactivex.Observable;

/**
 * 运动类型 - 数据库工具类
 * Created by leo on 2018/11/10.
 */
public class BoxSportHelper {

    /**
     * function: 判断本地是否已经存在运动类型数据
     *
     * @return
     */
    public static boolean isExistSport() {
        return BoxHelper.get().queryFirst(SportModel.class) != null;
    }

    /**
     * function:
     *
     * @return
     */
    public static void putSports() {
        String[] sports = ResHelper.getStringArray(R.array.sports);
        List<SportModel> sportModels = new ArrayList<>();
        for (int i = 0; i < sports.length; i++) {
            SportModel model = new SportModel();
            model.setName(sports[i]);
            sportModels.add(model);
        }
        BoxHelper.get().putAll(SportModel.class, sportModels);

    }

    /**
     * function: 查询所有Sport数据
     *
     * @return
     */
    public static Observable<List<SportModel>> getQueryObservable() {
        Query<SportModel> query = BoxHelper
                .get()
                .queryBuilder(SportModel.class)
                .build();
        return RxQuery.observable(query);
    }


    /**
     * function: 删除所有Sport数据
     */
    public static void deleteSport() {
        BoxHelper.get().deleteAll(SportModel.class);
    }

    /**
     * function: 根据id删除某一条数据
     *
     * @param sportId
     */
    @WorkerThread
    public static void deleteSport(long sportId) {
        BoxHelper.get().delete(SportModel.class, sportId);
    }


}
