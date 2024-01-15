package io.ganguo.incubator.repository;


import io.ganguo.incubator.entity.UserEntity;
import io.ganguo.incubator.repository.base.BaseRepository;
import io.ganguo.library.Config;
import io.ganguo.utils.util.json.Gsons;

/**
 * 存放常用的数据（可拓展写多层缓存）
 * 此为示例示例示例
 * <p>
 * Created by hulkyao on 5/6/2017.
 */

public class UserRepository extends BaseRepository<UserEntity> {
    // TODO: 5/6/2017 这里的key要放在Constants引用过来，不要直接写在这里，以免key重复
    private static final String CACHE_KEY = "config_key_user_data";

    private static UserRepository instance = null;

    public static UserRepository get() {
        if (instance == null) {
            instance = new UserRepository();
        }
        return instance;
    }

    private UserRepository() {
    }

    @Override
    public void updateFromService() {
        // step1 get data from service
        // step2 set data to cache
    }

    @Override
    protected void loadCache() {
        // 如果数据量大的话考虑存数据库
        // SQLite / ORM / ROOM ...

        // 如果数据量小的话可以存入cache/disk
        // GGCache.cache().getString(CACHE_KEY);
        // GGCache.disk().getString(CACHE_KEY);

        // 常用的且非常少数据存入sharePreference
        String jsonStr = Config.getString(CACHE_KEY);
        UserEntity user = Gsons.fromJson(jsonStr, UserEntity.class);
        data.set(user);
    }

    @Override
    public void clearCache() {
        // 清除对应cache
        Config.putString(CACHE_KEY, "");
    }

    private void saveCache(UserEntity user) {
        // 将User对象存入你存放的地方
        String jsonStr = Gsons.toJson(user);
        Config.putString(CACHE_KEY, jsonStr);
    }
}
