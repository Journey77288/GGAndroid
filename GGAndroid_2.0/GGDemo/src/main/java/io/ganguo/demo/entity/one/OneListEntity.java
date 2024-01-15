package io.ganguo.demo.entity.one;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <p>
 * api/oneList 接口数据
 * </p>
 * Created by leo on 2018/7/30.
 */
//添加get、set方法，并添加、equals、canEquals、hashCode、toString方法
@Data
//支持链式写法
@Accessors(chain = true)
//生成无参数构造函数
@NoArgsConstructor
//生成全参数构造函数
@AllArgsConstructor

@org.parceler.Parcel
public class OneListEntity {
    @SerializedName("content_list")
    List<ContentOneEntity> contentOneEntities;
}
