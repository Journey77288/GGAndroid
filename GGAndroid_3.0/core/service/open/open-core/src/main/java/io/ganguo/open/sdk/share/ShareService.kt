package io.ganguo.open.sdk.share

import io.ganguo.factory.service.ResultEmitterService

/**
 * <pre>
 *     author : leo
 *     time   : 2019/10/11
 *     desc   : Open Sdk 接口
 * </pre>
 * @see
 */

abstract class ShareService<Result, ResultObservable> : ResultEmitterService<Result, ResultObservable>() {
    /**
     * 分享
     * @return
     */
    internal fun share(): ResultObservable {
        return sendErrorOrStartService()
    }

}