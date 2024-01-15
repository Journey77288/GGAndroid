package io.ganguo.rxqiniu;

/**
 * Created by Roger on 7/28/16.
 */
public interface UrlGenerator {
//    private Logger logger = LoggerFactory.getLogger(getClass().getSimpleName());

    String call(String key);
}
