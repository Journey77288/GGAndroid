package io.ganguo.demo.database.model;

import org.parceler.Parcel;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * Created by leo on 2018/11/10.
 * 数据库规范文档
 * <a link="https://gitlab.cngump.com/ganguo_android/ggandroid/wikis/Android-database-document"><a/>
 * 运动
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Parcel
public class SportModel {
    @Id
    long id;
    String name;
}
