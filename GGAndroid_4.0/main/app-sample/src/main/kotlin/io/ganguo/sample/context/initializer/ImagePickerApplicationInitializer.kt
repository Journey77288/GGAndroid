package io.ganguo.sample.context.initializer

import android.app.Application
import io.ganguo.app.context.initializer.ApplicationInitializer
import io.ganguo.app.context.initializer.ApplicationInitializerCreator
import io.ganguo.picker.core.entity.PickerSpec
import io.ganguo.sample.viewmodel.widget.PickerImageEngine

/**
 * <pre>
 *     author : lucas
 *     time   : 2022/02/11
 *     desc   : Image Picker Config
 * </pre>
 */
class ImagePickerApplicationInitializer : ApplicationInitializer{
    override fun initialize(application: Application) {
        PickerSpec.init(PickerImageEngine())
    }

    companion object: ApplicationInitializerCreator<ImagePickerApplicationInitializer> {
        override fun createInitializer(parameter: Map<String, Any>): ImagePickerApplicationInitializer {
            return ImagePickerApplicationInitializer()
        }
    }
}