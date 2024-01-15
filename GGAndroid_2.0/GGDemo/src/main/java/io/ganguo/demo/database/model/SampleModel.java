package io.ganguo.demo.database.model;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

/**
 * <p>
 * 注: SampleModel，项目中至少要有一个数据库Model类，objectbox数据库才能正常生成MyObjectBox类进行初始化操作
 * </p>
 * Created by leo on 2018/10/19.
 */
@Entity
public class SampleModel {
    @Id
    long id;
    String tag;
}
