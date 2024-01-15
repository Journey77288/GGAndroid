package io.ganguo.rxqiniu;

/**
 * 图片Key生成接口
 * Created by Roger on 7/28/16.
 */
public interface KeyGenerator {

    String call(String fileName);
}
