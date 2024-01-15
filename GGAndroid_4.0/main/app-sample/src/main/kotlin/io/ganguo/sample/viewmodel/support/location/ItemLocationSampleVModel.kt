package io.ganguo.sample.viewmodel.support.location

import android.app.Activity
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ObservableField
import androidx.lifecycle.lifecycleScope
import io.ganguo.app.helper.activity.ActivityHelper
import io.ganguo.appcompat.Toasts
import io.ganguo.core.view.common.dialog.PermissionHintDialog
import io.ganguo.mvvm.viewinterface.ViewInterface
import io.ganguo.mvvm.viewmodel.ViewModel
import io.ganguo.permission.isLocationPermissionGranted
import io.ganguo.permission.requestLocationPermissions
import io.ganguo.rxjava.printThrowable
import io.ganguo.rxresult.RxActivityResult
import io.ganguo.sample.R
import io.ganguo.sample.databinding.ItemLocationSampleBinding
import io.ganguo.sample.view.support.LocationSampleActivity
import io.ganguo.utils.LocationHelper
import io.ganguo.utils.PermissionTimeHelper
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.internal.functions.Functions

/**
 * <pre>
 *     author : lucas
 *     time   : 2021/06/04
 *     desc   : Location Sample ItemViewModel
 * </pre>
 */
class ItemLocationSampleVModel : ViewModel<ViewInterface<ItemLocationSampleBinding>>() {
    override val layoutId: Int = R.layout.item_location_sample
    private val locationPermissionDialog: PermissionHintDialog by lazy {
        PermissionHintDialog(context, getString(R.string.str_location_permission_test_title), getString(R.string.str_location_permission_test_hint))
    }
    val location = ObservableField<String>()

    override fun onViewAttached(view: View) {

    }

    /**
     * 点击定位按钮
     */
    fun actionLocation(view: View) {
        val activity = ActivityHelper.currentActivity() ?: return
        if (activity.isLocationPermissionGranted()) {
            onLocationPermissionGranted()
        } else {
            if (PermissionTimeHelper.isEnableRequireLocationPermission()) {
                locationPermissionDialog.show()
                activity.requestLocationPermissions {
                    locationPermissionDialog.dismiss()
                    if (it.success) {
                        onLocationPermissionGranted()
                    } else {
                        PermissionTimeHelper.updateLocationDeniedTimestamp()
                    }
                }
            } else {
                Toasts.show(getString(R.string.str_location_permission_test_denied))
            }
        }
    }

    private fun onLocationPermissionGranted() {
        val activity = ActivityHelper.currentActivity() ?: return
        val isLocationEnabled = LocationHelper.get().isLocationServiceOpen()
        if (isLocationEnabled) {
            startLocate()
        } else {
            openGspSetting(activity)
        }
    }

    /**
     * 进入打开定位服务设置页
     *
     * @param activity Activity
     */
    private fun openGspSetting(activity: Activity) {
        val locationIntent = LocationHelper.get().getGPSSettingIntent()
        RxActivityResult.startIntent(activity, locationIntent)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext {
                val isOpen = LocationHelper.get().isLocationServiceOpen()
                if (isOpen) {
                    startLocate()
                }
            }
            .subscribe(Functions.emptyConsumer(), printThrowable("--actionLocation--"))
    }

    /**
     * 开始定位
     */
    private fun startLocate() {
        LocationHelper.get()
            .getCurrentLocation(lifecycleOwner.lifecycleScope) {
                val latitude = it?.latitude.toString()
                val longitude = it?.longitude.toString()
                location.set(getString(R.string.str_current_location_value, latitude, longitude))
            }
    }
}