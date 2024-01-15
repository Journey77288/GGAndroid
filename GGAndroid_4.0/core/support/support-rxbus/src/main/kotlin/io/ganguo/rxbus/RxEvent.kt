package io.ganguo.rxbus

import androidx.core.util.Pair

/**
 * <pre>
 *   @author : leo
 *   @time   : 2020/07/18
 *   @desc   : RxBus Event Object
 * </pre>
 */
class RxEvent(eventName: String, eventData: Any) : Pair<String, Any>(eventName, eventData)