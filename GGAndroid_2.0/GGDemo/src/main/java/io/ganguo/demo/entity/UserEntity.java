package io.ganguo.demo.entity;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * Created by Tony on 10/6/15.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@org.parceler.Parcel
public class UserEntity {
    int id;
    String name;
    @SerializedName("api_token")
    String apiToken;

}
