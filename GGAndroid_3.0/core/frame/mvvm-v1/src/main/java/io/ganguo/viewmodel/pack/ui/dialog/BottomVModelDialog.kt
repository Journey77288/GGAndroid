package io.ganguo.viewmodel.pack.ui.dialog

import android.content.Context
import androidx.databinding.ViewDataBinding
import io.ganguo.viewmodel.core.BaseViewModel

/**
 *
 *
 * 底部弹窗 - dialog
 *
 * Created by leo on 2018/9/30.
 */
abstract class BottomVModelDialog<T : ViewDataBinding, B : BaseViewModel<*>>(context: Context, theme: Int) : FullScreenVModelDialog<T, B>(context, theme)