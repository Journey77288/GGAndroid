package io.ganguo.picker.ui.preview

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.viewpager.widget.ViewPager
import com.blankj.utilcode.util.BarUtils
import io.ganguo.picker.R
import io.ganguo.picker.core.entity.IncapableCause
import io.ganguo.picker.core.entity.Media
import io.ganguo.picker.core.entity.PickerSpec
import io.ganguo.picker.core.model.SelectedMediaModel
import io.ganguo.picker.databinding.ActivityPreviewBinding
import io.ganguo.picker.ui.widget.CheckView

/**
 * <pre>
 *     author : Raynor
 *     time   : 2020/07/22
 *     desc   : 图片预览 Activity 抽象类
 * </pre>
 *
 * 图片预览 Activity 抽象类
 * @param
 * @see
 * @author Raynor
 * @property
 */
abstract class BasePreviewActivity : AppCompatActivity(), View.OnClickListener, ViewPager.OnPageChangeListener, OnFragmentInteractionListener {

    protected lateinit var binding : ActivityPreviewBinding
    protected val selectedMediaModel = SelectedMediaModel(this)
    private lateinit var viewPagerAdapter: PreviewPagerAdapter
    protected var previousPosition = -1
    private var isToolBarHide = false

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(PickerSpec.styleRes)
        super.onCreate(savedInstanceState)
        BarUtils.setStatusBarLightMode(this, false)
        binding = ActivityPreviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //设置透明toolbar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }

        if (savedInstanceState == null) {
            //如果是新创建的页面 重新创建选中集合
            selectedMediaModel.onCreate(intent.getBundleExtra(EXTRA_DEFAULT_BUNDLE))
        } else {
            //如果之前重载了 从savedInstanceState里拿选中状态
            selectedMediaModel.onCreate(savedInstanceState)
        }

        //初始化View
        binding.buttonApply.setOnClickListener(this)
        binding.buttonBack.setOnClickListener(this)

        //初始化ViewPager
        binding.pager.addOnPageChangeListener(this)
        viewPagerAdapter = PreviewPagerAdapter(supportFragmentManager, null)
        binding.pager.adapter = viewPagerAdapter

        //初始化右上角选中View
        binding.checkView.setCountable(PickerSpec.countable)
        binding.checkView.setOnClickListener {
            val media = viewPagerAdapter.getMedia(binding.pager.currentItem)
            if (selectedMediaModel.isSelected(media)) {
                selectedMediaModel.remove(media)

                if (PickerSpec.countable) {
                    binding.checkView.setCheckedNum(CheckView.UNCHECKED)
                } else {
                    binding.checkView.setChecked(false)
                }

            } else {
                if (assertAddSelection(media)) {
                    selectedMediaModel.add(media)

                    if (PickerSpec.countable) {
                        binding.checkView.setCheckedNum(selectedMediaModel.checkedNum(media))
                    } else {
                        binding.checkView.setChecked(true)
                    }
                }
            }
            updateApplyButton()
        }

        updateApplyButton()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        selectedMediaModel.onSaveInstanceState(outState)
        super.onSaveInstanceState(outState)
    }

    /**
     * 设置Activity Result
     * @param [apply] 如果为false即舍弃本次选择结果
     */
    fun sendBackResult(apply: Boolean) {
        val intent = Intent()
        intent.putExtra(EXTRA_RESULT_BUNDLE, selectedMediaModel.getBundle())
        intent.putExtra(EXTRA_RESULT_APPLY, apply)
        setResult(Activity.RESULT_OK, intent)
    }

    /**
     * 更新 "应用" 按钮样式
     */
    fun updateApplyButton() {
        val count = selectedMediaModel.getSelectedCount()
        when {
            count == 0 -> {
                binding.buttonApply.setText(R.string.button_apply_default)
                binding.buttonApply.isEnabled = false
            }

            (count == 1) and PickerSpec.isSingleSelectionModeEnabled() -> {
                binding.buttonApply.setText(R.string.button_apply_default)
                binding.buttonApply.isEnabled = true
            }

            else -> {
                binding.buttonApply.setText(getString(R.string.button_apply, count))
                binding.buttonApply.isEnabled = true
            }
        }
    }

    /**
     * 断言 [selectedMediaModel]是否接受本次数据插入
     * @return 如允许 返回true
     */
    private fun assertAddSelection(media: Media): Boolean {
        val cause = selectedMediaModel.isAcceptable(media)
        IncapableCause.handleCause(this, cause);
        return cause == null
    }

    override fun onBackPressed() {
        sendBackResult(false)
        super.onBackPressed()
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.button_back) {
            onBackPressed()
        } else {
            sendBackResult(true)
            finish()
        }
    }

    override fun onClick() {
        if (isToolBarHide) {
            binding.topToolbar.animate()
                    .setInterpolator(FastOutSlowInInterpolator())
                    .translationYBy(binding.topToolbar.measuredHeight.toFloat())
                    .start()

            binding.bottomToolbar.animate()
                    .setInterpolator(FastOutSlowInInterpolator())
                    .translationYBy(-(binding.bottomToolbar.measuredHeight.toFloat()))
                    .start()
        } else {
            binding.topToolbar.animate()
                    .setInterpolator(FastOutSlowInInterpolator())
                    .translationYBy(-(binding.topToolbar.measuredHeight.toFloat()))
                    .start()

            binding.bottomToolbar.animate()
                    .setInterpolator(FastOutSlowInInterpolator())
                    .translationYBy(binding.bottomToolbar.measuredHeight.toFloat())
                    .start()
        }

        isToolBarHide = isToolBarHide.not()
    }

    override fun onPageScrollStateChanged(state: Int) {
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun onPageSelected(position: Int) {
        val adapter = binding.pager.adapter as PreviewPagerAdapter?
        if (previousPosition != -1 && previousPosition != position) {
            //这一步回去找前一个Fragment 如果存在就直接丢过来 然后这边调用resetView()重置其缩放
            adapter?.apply {
                (adapter.instantiateItem(binding.pager, previousPosition) as PreviewItemFragment).resetView()

                val media = getMedia(position)

                if (PickerSpec.countable) {
                    val checkedNum = selectedMediaModel.checkedNum(media)
                    binding.checkView.setCheckedNum(checkedNum)
                    if (checkedNum > 0) {
                        binding.checkView.isEnabled = true
                    } else {
                        binding.checkView.isEnabled = selectedMediaModel.isNumLimitReached().not()
                    }
                } else {
                    val checked = selectedMediaModel.isSelected(media)
                    binding.checkView.setChecked(checked)
                    if (checked) {
                        binding.checkView.isEnabled = true
                    } else {
                        binding.checkView.isEnabled = selectedMediaModel.isNumLimitReached().not()
                    }
                }
            }
        }

        previousPosition = position
    }

    companion object {
        const val EXTRA_DEFAULT_BUNDLE = "extra_default_bundle"
        const val EXTRA_RESULT_BUNDLE = "extra_result_bundle"
        const val EXTRA_RESULT_APPLY = "extra_result_apply"
        const val EXTRA_RESULT_ORIGINAL_ENABLE = "extra_result_original_enable"
        const val CHECK_STATE = "checkState"
    }
}