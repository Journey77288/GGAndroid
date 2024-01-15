package io.ganguo.push.service

import cn.jpush.android.service.JCommonService

/**
 * <pre>
 *     @author : zoyen
 *     time   :  2019/09/25
 *     desc   :  这个是自定义Service，要继承极光JCommonService，可以在更多手机平台上使得推送通道保持的更稳定
 * </pre>
 */

class PushService : JCommonService()