package io.ganguo

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * <pre>
 *   @author : leo
 *   @time   : 2021/01/19
 *   @desc   : Request Permission Result
 * </pre>
 * @see success  If the permission request succeeds, true for success, false for failure
 * @see permissions The type of permission applied
 */
@Parcelize
data class PermissionResult(val success: Boolean, val permissions: List<String>) : Parcelable
