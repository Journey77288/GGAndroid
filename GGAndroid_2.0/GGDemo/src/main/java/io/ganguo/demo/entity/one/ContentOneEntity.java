package io.ganguo.demo.entity.one;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


/**
 * <p>
 * 《一个》 - content_list数据
 * </p>
 * Created by leo on 2018/7/30.
 */
//添加get、set方法，并添加、equals、canEquals、hashCode、toString方法
@Data
//支持链式写法
@Accessors(chain = true)
//生成无参数构造函数
@NoArgsConstructor
@AllArgsConstructor
@org.parceler.Parcel
public class ContentOneEntity {

    @SerializedName("id")
    String id;
    @SerializedName("category")
    String category;
    @SerializedName("display_category")
    int displayCategory;
    @SerializedName("item_id")
    String itemId;
    @SerializedName("title")
    String title;
    @SerializedName("forward")
    String forward;
    @SerializedName("img_url")
    String imgUrl;
    @SerializedName("like_count")
    int likeCount;
    @SerializedName("post_date")
    String postDate;
    @SerializedName("last_update_date")
    String lastUpdateDate;
    @SerializedName("video_url")
    String videoUrl;
    @SerializedName("audio_url")
    String audioUrl;
    @SerializedName("audio_platform")
    int audioPlatform;
    @SerializedName("start_video")
    String startVideo;
    @SerializedName("volume")
    String volume;
    @SerializedName("pic_info")
    String picInfo;
    @SerializedName("words_info")
    String wordsInfo;
    @SerializedName("subtitle")
    String subtitle;
    @SerializedName("number")
    int number;
    @SerializedName("serial_id")
    int serialId;
    @SerializedName("movie_story_id")
    int movieStoryId;
    @SerializedName("ad_id")
    int adId;
    @SerializedName("ad_type")
    int adType;
    @SerializedName("ad_pvurl")
    String adPvurl;
    @SerializedName("ad_linkurl")
    String adLinkurl;
    @SerializedName("ad_makettime")
    String adMakettime;
    @SerializedName("ad_closetime")
    String adClosetime;
    @SerializedName("ad_share_cnt")
    String adShareCnt;
    @SerializedName("ad_pvurl_vendor")
    String adPvurlVendor;
    @SerializedName("content_id")
    String contentId;
    @SerializedName("content_type")
    String contentType;
    @SerializedName("content_bgcolor")
    String contentBgcolor;
    @SerializedName("share_url")
    String shareUrl;
    @SerializedName("orientation")
    String orientation;

}
