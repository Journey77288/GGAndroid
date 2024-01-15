package io.ganguo.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * Created by Tony on 11/10/15.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@org.parceler.Parcel
public class IssueEntity {
    String title;

}
