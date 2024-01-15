package io.ganguo.demo.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * Created by leo on 16/7/8.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@org.parceler.Parcel
public class ImageEntity {
    String name;
    String image;
}
