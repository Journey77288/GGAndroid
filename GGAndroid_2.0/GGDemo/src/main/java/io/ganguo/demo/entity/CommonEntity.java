package io.ganguo.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * Created by leo on 2018/11/10.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@org.parceler.Parcel
public class CommonEntity {
    long id;
    String name;
}
