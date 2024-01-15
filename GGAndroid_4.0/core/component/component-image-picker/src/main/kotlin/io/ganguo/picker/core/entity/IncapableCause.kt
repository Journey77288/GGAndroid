package io.ganguo.picker.core.entity

import android.content.Context
import android.widget.Toast

/**
 * <pre>
 *     author : Raynor
 *     time   : 2020/06/24
 *     desc   : 提示消息包装类
 * </pre>
 *
 * 提示消息包装类
 * 包含提示内容与消息提醒方式
 * @param
 * @see
 * @author Raynor
 * @property
 */
class IncapableCause private constructor() {
     var form = NotificationForm.TOAST
    private var title = ""
    private var content = ""

    constructor(message: String) : this() {
        content = message
    }

    constructor(title: String, message: String) : this(message) {
        this.title = title
    }

    constructor(form: NotificationForm, message: String) : this(message) {
        this.form = form
    }

    constructor(form: NotificationForm, title: String, message: String) : this(title, message) {
        this.form = form
    }

    enum class NotificationForm(val form: Int) {
        TOAST(0x01),
        DIALOG(0x02),
        NONE(0x00)
    }

    companion object {
        fun handleCause(context: Context, cause: IncapableCause?) {
            if (cause == null) {
                return
            }

            if (PickerSpec.handleNotification != null) {
                PickerSpec.handleNotification?.invoke(cause)
            } else {
                when(cause.form) {
                    NotificationForm.TOAST -> {
                        Toast.makeText(context, cause.content, Toast.LENGTH_SHORT).show()
                    }
                    NotificationForm.DIALOG -> {
                        //TODO 展示Dialog
                    }
                    NotificationForm.NONE -> {

                    }
                }
            }
        }
    }
}