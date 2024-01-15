package io.ganguo.image;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.ganguo.image.entity.SysImageInfo;
import io.ganguo.library.util.date.Date;
import io.ganguo.library.util.log.Logger;
import io.ganguo.library.util.log.LoggerFactory;

/**
 * 加载系统图片
 * Created by hulkyao on 6/7/15.
 */
public class SysImageUtil {
    public final static Logger logger = LoggerFactory.getLogger(SysImageUtil.class);

    public static String[] IMAGE_PROJECTION = {
            MediaStore.Images.Media.DATA,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.LATITUDE,
            MediaStore.Images.Media.LONGITUDE,
    };


    public static List<SysImageInfo> loadSystemPhotos(Context context) {
        List<SysImageInfo> infos = new ArrayList<SysImageInfo>();

        Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_PROJECTION, null, null, IMAGE_PROJECTION[2] + " DESC");
        if (cursor != null) {
            cursor.moveToFirst();
            do {
                String path = cursor.getString(cursor.getColumnIndexOrThrow(IMAGE_PROJECTION[0]));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(IMAGE_PROJECTION[1]));
                String lat = cursor.getString(cursor.getColumnIndexOrThrow(IMAGE_PROJECTION[2]));
                String lon = cursor.getString(cursor.getColumnIndexOrThrow(IMAGE_PROJECTION[3]));

                SysImageInfo info = new SysImageInfo();
                info.setPath(path);
                info.setName(name);
                info.setDate((new Date(new File(path).lastModified())).toDate());
                info.setLat(lat);
                info.setLon(lon);
                infos.add(info);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return infos;
    }
}
