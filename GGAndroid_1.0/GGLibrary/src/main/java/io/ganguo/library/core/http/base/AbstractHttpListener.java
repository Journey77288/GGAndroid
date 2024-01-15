package io.ganguo.library.core.http.base;

import io.ganguo.library.BaseContext;
import io.ganguo.library.core.http.HttpConstants;
import io.ganguo.library.core.http.response.HttpError;
import io.ganguo.library.util.log.Logger;
import io.ganguo.library.util.log.LoggerFactory;


/**
 * Created by Tony on 3/2/15.
 */
public abstract class AbstractHttpListener<T> implements HttpListener<T> {
    private Logger logger = LoggerFactory.getLogger(HttpConstants.TAG);

    /**
     * 解析错误
     *
     * @param httpError
     */
    @Override
    public void handleError(HttpError httpError) {
        onFailure(httpError);
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onFinish() {

    }

    @Override
    public void onFailure(HttpError error) {
        // print toString()
        logger.w(error);

        error.makeToast(BaseContext.getInstance());
    }

}
