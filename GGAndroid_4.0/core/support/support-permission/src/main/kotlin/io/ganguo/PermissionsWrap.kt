package io.ganguo

import io.reactivex.rxjava3.subjects.PublishSubject

/**
 * <pre>
 *   @author : leo
 *   @time   : 2021/01/19
 *   @desc   : Wrap PublishSubject<PermissionResult>，Avoid invoking security issues
 * </pre>
 */
class PermissionsWrap {
    var rxPermissions: PublishSubject<PermissionResult>? = null
}
