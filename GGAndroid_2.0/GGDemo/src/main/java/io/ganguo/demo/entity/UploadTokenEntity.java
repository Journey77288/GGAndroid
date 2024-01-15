package io.ganguo.demo.entity;


import org.parceler.Parcel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * Created by hulk on 30/3/2016.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Parcel
public class UploadTokenEntity {
    String domain;
    String token;
}
