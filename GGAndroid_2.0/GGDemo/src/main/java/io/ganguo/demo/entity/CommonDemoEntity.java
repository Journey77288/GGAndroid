package io.ganguo.demo.entity;


import org.parceler.Parcel;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <p>
 * </p>
 * Created by leo on 2018/8/27.
 */
//添加get、set方法，并添加、equals、canEquals、hashCode、toString方法
@Data
//支持链式写法
@Accessors(chain = true)
//生成无参数构造函数
@NoArgsConstructor
@Parcel
public class CommonDemoEntity {
    String text;

}
