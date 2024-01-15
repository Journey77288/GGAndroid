package io.ganguo.picker.ui

import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.blankj.utilcode.util.UriUtils
import io.ganguo.permission.*
import io.ganguo.picker.R
import io.ganguo.picker.core.bucket.Bucket
import io.ganguo.picker.core.entity.Media
import io.ganguo.picker.core.entity.PickerSpec
import io.ganguo.picker.core.model.BucketModel
import io.ganguo.picker.core.model.BucketModelCallbacks
import io.ganguo.picker.core.model.SelectedMediaModel
import io.ganguo.picker.databinding.ActivityPickerBinding
import io.ganguo.picker.ui.bucket.BucketAdapter
import io.ganguo.picker.ui.bucket.BucketSpinner
import io.ganguo.picker.ui.media.MediaAdapter
import io.ganguo.picker.ui.preview.BasePreviewActivity
import io.ganguo.picker.ui.preview.NormalPreviewActivity
import io.ganguo.rxresult.GGActivityResultLauncher
import io.ganguo.utils.PermissionTimeHelper
import io.ganguo.utils.URIs

/**
 * <pre>
 *     author : Raynor
 *     time   : 2020/06/04
 *     desc   : 图片选择器的Main Activity
 * </pre>
 *
 * 媒体选择器的Main Activity
 * 用来显示相册列表与对应的图片（视频）列表
 * bucketModel.load() -> onBucketLoad
 * @param
 * @see
 * @author Raynor
 * @property
 */
class PickerActivity : AppCompatActivity(), BucketModelCallbacks, OnItemSelectedListener,
    SelectedMediaModel.SelectedMediaModelProvider, MediaAdapter.CheckStateListener, MediaAdapter.MediaClickListener,
    MediaAdapter.CaptureClickListener, View.OnClickListener {
    private val bucketModel = BucketModel()
    lateinit var bucketSpinner: BucketSpinner
    private var bucketAdapter = BucketAdapter(this, null)
    private val selectedMediaModel = SelectedMediaModel(this)
    private var currentFragment: PickerFragment ?= null
    private var cameraUri: Uri ?= null
    private lateinit var binding: ActivityPickerBinding
    private val resultLauncher = GGActivityResultLauncher(this, ActivityResultContracts.StartActivityForResult())
    private val pickPhotoLauncher = GGActivityResultLauncher(this, ActivityResultContracts.TakePicture())

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(PickerSpec.styleRes)
        super.onCreate(savedInstanceState)
        binding = ActivityPickerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initToolbar()
        initPermissionHint()

        lifecycle.addObserver(bucketModel)
        selectedMediaModel.onCreate(savedInstanceState)

        if (PickerSpec.isOnlyShowImages()) {
            if (!isImageMediaPermissionGranted()) {
                toastMessage(getString(R.string.str_require_media_denied_hint))
            }
            loadMediaData(savedInstanceState)
        } else if (PickerSpec.isOnlyShowVideos()) {
            if (!isVideoMediaPermissionGranted()) {
                toastMessage(getString(R.string.str_require_media_denied_hint))
            }
            loadMediaData(savedInstanceState)
        } else if (PickerSpec.isOnlyShowAudios()) {
            if (!isAudioMediaPermissionGranted()) {
                toastMessage(getString(R.string.str_require_media_denied_hint))
            }
            loadMediaData(savedInstanceState)
        } else {
            if (!isStoragePermissionGranted()) {
                toastMessage(getString(R.string.str_require_media_denied_hint))
            }
            loadMediaData(savedInstanceState)
        }

        binding.buttonApply.setOnClickListener(this)
        binding.buttonPreview.setOnClickListener(this)
        updateBottomBar()
    }

    /**
     * 加载媒体数据
     *
     * @param savedInstanceState
     */
    private fun loadMediaData(savedInstanceState: Bundle?) {
        bucketSpinner = BucketSpinner(this)
        bucketSpinner.itemSelectedListener = this
        bucketSpinner.setSelectedTextView((findViewById<View>(R.id.selected_bucket) as TextView))
        bucketSpinner.setPopupAnchorView(findViewById(R.id.toolbar))
        bucketSpinner.setAdapter(bucketAdapter)

        bucketModel.onCreate(this, this)
        bucketModel.onRestoreInstanceState(savedInstanceState)

        bucketModel.load()
    }

    /**
     * Bucket 查询完成回调
     */
    override fun onBucketLoad(cursor: Cursor?) {
        bucketAdapter.swapCursor(cursor)
        val handler = Handler(Looper.getMainLooper())

        //查询完成后 将游标移动到第一位 使用Bucket初始化Fragment
        handler.post {
            cursor?.moveToPosition(bucketModel.currentSelection)
            bucketSpinner.setSelection(this, bucketModel.currentSelection)
            val bucket = Bucket.valueOf(cursor)
            initFragmentByBucket(bucket)
        }
    }

    override fun onBucketReset() {
        bucketAdapter.swapCursor(null)
    }

    /**
     * 初始化工具栏
     */
    private fun initToolbar() {
        //设置为SupportActionBar才有NavigationIcon
        setSupportActionBar(binding.toolbar)
        val actionBar = supportActionBar
        actionBar?.setDisplayShowTitleEnabled(false)
        actionBar?.setDisplayHomeAsUpEnabled(true)
        //TODO 设置按钮color filter

    }

    /**
     * 初始化权限提示
     */
    private fun initPermissionHint() {
        if (PickerSpec.isOnlyShowVideos()) {
            binding.tvPermissionTitle.text = getString(R.string.str_require_video_record_permission)
            binding.tvPermissionHint.text = getString(R.string.str_require_video_record_permission_hint)
        } else {
            binding.tvPermissionTitle.text = getString(R.string.str_require_camera_permission)
            binding.tvPermissionHint.text = getString(R.string.str_require_camera_permission_hint)
        }
    }

    /**
     * 顶部按钮点击回调
     *
     * @param item 按钮Item
     * @return Boolean
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

    /**
     * 左上角BucketSpinner中item选中回调
     * 左上角选中回调 -> 拿到Bucket -> initFragmentByBucket
     */
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        bucketAdapter.cursor.moveToPosition(position)
        val bucket = Bucket.valueOf(bucketAdapter.cursor)
        bucketModel.currentSelection = position
        if (currentFragment == null) {
            initFragmentByBucket(bucket)
        } else {
            currentFragment!!.refreshData(bucket)
        }
    }


    /**
     * 通过具体的Bucket初始化Fragment
     * @param [bucket] Bucket
     */
    private fun initFragmentByBucket(bucket: Bucket) {
        if (bucket.isAll() && bucket.isEmpty() && !PickerSpec.capture) {
            binding.container.visibility = View.GONE
            binding.emptyView.visibility = View.VISIBLE
        } else {
            binding.container.visibility = View.VISIBLE
            binding.emptyView.visibility = View.GONE

            //初始化Fragment
            val fragment = PickerFragment.instance(bucket)
            supportFragmentManager
                    .beginTransaction()
                    .setReorderingAllowed(false)
                    .replace(R.id.container, fragment, PickerFragment::class.java.simpleName)
                    .commitAllowingStateLoss()
            currentFragment = fragment
        }
    }

    private fun updateBottomBar() {
        val selectedNum = selectedMediaModel.getSelectedCount()
        when {
            selectedNum == 0 -> {
                binding.buttonPreview.isEnabled = false
                binding.buttonApply.isEnabled = false
                binding.buttonApply.text = getString(R.string.button_apply_default)
            }
            (selectedNum == 1) && (PickerSpec.isSingleSelectionModeEnabled()) -> {
                binding.buttonPreview.isEnabled = true
                binding.buttonApply.isEnabled = true
                binding.buttonApply.text = getString(R.string.button_apply_default)
            }
            else -> {
                binding.buttonPreview.isEnabled = true
                binding.buttonApply.isEnabled = true
                binding.buttonApply.text = getString(R.string.button_apply, selectedNum)
            }
        }
    }

    override fun provideSelectedMediaModel(): SelectedMediaModel {
        return selectedMediaModel
    }

    override fun onCheckStateUpdate() {
        updateBottomBar()
    }

    override fun onMediaClick(bucket: Bucket?, media: Media) {
        val selectedMediaList = selectedMediaModel.selectedMedias
        val intent = NormalPreviewActivity.intentFor(
            this,
            bucket,
            media,
            selectedMediaModel.getBundle(),
            ArrayList(selectedMediaList)
        )
        resultLauncher.launch(intent) {
            if (it.resultCode == Activity.RESULT_OK) {
                val data = it.data
                val resultBundle = data?.getBundleExtra(BasePreviewActivity.EXTRA_RESULT_BUNDLE)
                val selectedList = ArrayList<Media>()
                val resultList = resultBundle?.getParcelableArrayList<Media>(SelectedMediaModel.SAVED_SELECTION)
                selectedList.addAll(resultList ?: emptyList())
                val selectionType = resultBundle!!.getInt(
                    SelectedMediaModel.STATE_SELECTION_TYPE,
                    SelectedMediaModel.SELECTION_TYPE_UNDEFINED
                )

                if (data.getBooleanExtra(BasePreviewActivity.EXTRA_RESULT_APPLY, false)) {
                    val intent = Intent()
                    intent.putParcelableArrayListExtra(EXTRA_RESULT_SELECTION, selectedList)
                    setResult(Activity.RESULT_OK, intent)
                    finish()

                } else {
                    selectedMediaModel.overwrite(selectedList, selectionType)
                    val fragment = supportFragmentManager.findFragmentByTag(PickerFragment::class.java.simpleName)
                    if (fragment is PickerFragment) {
                        fragment.refreshMediaGrid()
                    }
                    updateBottomBar()
                }
            }
        }
    }

    override fun onCaptureClick() {
        val isVideo = PickerSpec.isOnlyShowVideos()
        if (isVideo) {
            onRecordVideo()
        } else {
            onTakePhoto()
        }
    }

    /**
     * 选择录像
     */
    private fun onRecordVideo() {
        if (this.isVideoRecordPermissionGranted()) {
            recordVideo()
        } else {
            if (PermissionTimeHelper.isEnableRequireVideoRecordPermission()) {
                binding.llPermissionHint.isVisible = true
                this.requestCameraPermissions {
                    binding.llPermissionHint.isVisible = false
                    if (it.success) {
                        recordVideo()
                    } else {
                        PermissionTimeHelper.updateVideoRecordDeniedTimestamp()
                    }
                }
            } else {
                toastMessage(getString(R.string.str_require_video_record_denied_hint))
            }
        }
    }

    private fun recordVideo() {
        val intent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
        resultLauncher.launch(intent) { onMediaPickResult() }
    }

    /**
     * 选择拍照
     */
    private fun onTakePhoto() {
        if (this.isCameraPermissionGranted()) {
            takePhoto()
        } else {
            if (PermissionTimeHelper.isEnableRequireCameraPermission()) {
                binding.llPermissionHint.isVisible = true
                this.requestCameraPermissions {
                    binding.llPermissionHint.isVisible = false
                    if (it.success) {
                        takePhoto()
                    } else {
                        PermissionTimeHelper.updateCameraDeniedTimestamp()
                    }
                }
            } else {
                toastMessage(getString(R.string.str_require_camera_denied_hint))
            }
        }
    }

    private fun takePhoto() {
        cameraUri = URIs.createNewPhotoUri(this)
        cameraUri?.let { uri ->
            pickPhotoLauncher.launch(uri) { onMediaPickResult() }
        }
    }


    private fun toastMessage(hint: String) {
        Toast.makeText(applicationContext, hint, Toast.LENGTH_SHORT).show()
    }

    /**
     * 媒体库更新回调
     */
    private fun onMediaPickResult() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val bucket = Bucket.valueOf(bucketAdapter.cursor)
            currentFragment?.refreshData(bucket)
        } else {
            cameraUri?: return
            val file = UriUtils.uri2File(cameraUri!!)
            MediaScannerConnection.scanFile(applicationContext, arrayOf(file.absolutePath), null) { _, _ ->
                val bucket = Bucket.valueOf(bucketAdapter.cursor)
                currentFragment?.refreshData(bucket)
            }
        }
    }

    /**
     * 处理点击回调
     * 包括"预览" "完成"
     */
    override fun onClick(v: View?) {
        if (v?.id == R.id.button_preview) {
            onPreview()
        } else if (v?.id == R.id.button_apply) {
            val intent = Intent()
            val selectedMediaList = ArrayList(selectedMediaModel.selectedMedias)
            intent.putParcelableArrayListExtra(EXTRA_RESULT_SELECTION, selectedMediaList)
            setResult(Activity.RESULT_OK, intent)
            finish()
            PickerSpec.reset()
        }
    }

    /**
     * 点击预览
     */
    private fun onPreview() {
        val selectedMediaList = selectedMediaModel.selectedMedias
        val firstItem = selectedMediaList.first()
        if (PickerSpec.isOnlyShowVideos() || PickerSpec.isOnlyShowAudios()) {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setDataAndType(firstItem.uri, firstItem.mimeType)
            startActivity(intent)
        } else {
            val bucket = Bucket.valueOf(bucketAdapter.cursor)
            onMediaClick(bucket, firstItem)
        }
    }

    /**
     * @property [EXTRA_RESULT_SELECTION] 选项结果回调Key
     * @property [REQUEST_CODE_PREVIEW] 图片视频预览RequestCode
     * @property [REQUEST_CODE_CAPTURE] 拍照录像RequestCode
     */
    companion object {
        const val EXTRA_RESULT_SELECTION = "extra_result_selection"
        private const val REQUEST_CODE_PREVIEW = 23
        private const val REQUEST_CODE_CAPTURE = 24
    }
}