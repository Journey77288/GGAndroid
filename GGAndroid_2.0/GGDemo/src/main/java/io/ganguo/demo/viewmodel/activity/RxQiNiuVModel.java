package io.ganguo.demo.viewmodel.activity;

import android.databinding.ObservableField;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.mlsdev.rximagepicker.RxImagePicker;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import io.ganguo.demo.R;
import io.ganguo.demo.databinding.ActivityRxQiniuDemoBinding;
import io.ganguo.demo.module.impl.APIQiNiuImpl;
import io.ganguo.demo.viewmodel.item.ItemQiNiuImageVModel;
import io.ganguo.library.Config;
import io.ganguo.rx.RxActions;
import io.ganguo.viewmodel.common.RecyclerViewModel;
import io.ganguo.vmodel.adapter.ViewModelAdapter;
import io.ganguo.vmodel.rx.RxVMLifecycle;
import io.ganguo.vmodel.BaseViewModel;
import io.ganguo.vmodel.ViewModelHelper;
import io.ganguo.library.ui.view.ActivityInterface;
import io.ganguo.rxqiniu.KeyGenerator;
import io.ganguo.rxqiniu.RxQiNiu;
import io.ganguo.rxqiniu.RxQiNiuTransformer;
import io.ganguo.rxqiniu.UploadParamBuilder;
import io.ganguo.rxqiniu.UploadRequest;
import io.ganguo.utils.common.LoadingHelper;
import io.ganguo.utils.common.UIHelper;
import io.ganguo.utils.util.Strings;
import io.ganguo.utils.util.log.Logger;
import io.ganguo.viewmodel.common.HeaderItemViewModel;
import io.ganguo.viewmodel.common.HeaderViewModel;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.functions.Functions;
import io.reactivex.schedulers.Schedulers;

/**
 * function：上传图片文件到七牛 - Demo
 * Created by Roger on 7/28/16.
 */
public class RxQiNiuVModel extends BaseViewModel<ActivityInterface<ActivityRxQiniuDemoBinding>> {
    public ObservableField<String> src = new ObservableField<>("");
    public ObservableField<String> remoteUrl = new ObservableField<>("");
    private RecyclerViewModel recyclerViewModel;

    @Override
    public void onViewAttached(View view) {
        initRecycler();
        initRxQiNiuConfig();
    }

    /**
     * function:RxQiNiuConfig
     */
    private void initRxQiNiuConfig() {
        RxQiNiu.setDebug(true);
        RxQiNiu.setUrlGenerator(key -> {
            StringBuilder builder = new StringBuilder("http://7xsflv.com1.z0.glb.clouddn.com/");
            builder.append(key);
            return builder.toString();
        });
    }


    /**
     * function:init RecyclerViewModel
     */
    private void initRecycler() {
        recyclerViewModel = RecyclerViewModel.linerLayout(getContext(), LinearLayoutManager.HORIZONTAL);
        ViewModelHelper.bind(getView().getBinding().includeRecycler, this, recyclerViewModel);
    }

    private ViewModelAdapter getAdapter() {
        return recyclerViewModel.getAdapter();
    }

    /**
     * 从相册选择
     *
     * @return
     */
    public View.OnClickListener pickImage() {
        return v -> choosePhoto(RxImagePicker.ChooseMode.GALLERY);

    }

    /**
     * 拍照选择
     *
     * @return
     */
    public View.OnClickListener takePhoto() {
        return v -> choosePhoto(RxImagePicker.ChooseMode.CAMERA);
    }

    /**
     * 选择图片
     *
     * @param mode
     */
    public void choosePhoto(@RxImagePicker.ChooseMode int mode) {
        RxImagePicker
                .get()
                .setChooseMode(mode)
                .setSaveImagePath(Config.getImagePath().getAbsolutePath())
                .start(getContext())
                .map(file -> file.getAbsolutePath())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(filePath -> {
                    Logger.d("call: " + "filePath: " + filePath);
                    src.set(filePath);
                    int count = getAdapter().getItemCount();
                    getAdapter().clear();
                    getAdapter().notifyItemRangeRemoved(0, count);
                    remoteUrl.set(null);
                })
                .compose(RxVMLifecycle.bindViewModel(this))
                .subscribe(Functions.emptyConsumer(), RxActions.printThrowable());
    }

    /**
     * 批量队列上传
     *
     * @return
     */
    public View.OnClickListener queueUpload() {
        return v -> {
            if (Strings.isEmpty(src.get())) {
                UIHelper.snackBar(getRootView(), "请选择图片后再上传");
                return;
            }
            multiRequest(getMultiUploadParamBuilder(true));
        };
    }

    /**
     * 批量异步上传
     *
     * @return
     */
    public View.OnClickListener uploadMulti() {
        return v -> {
            if (Strings.isEmpty(src.get())) {
                UIHelper.snackBar(getRootView(), "请选择图片后再上传");
                return;
            }
            multiRequest(getMultiUploadParamBuilder(false));
        };
    }


    /**
     * function：批量队列上传
     *
     * @param builder 上传参数配置
     */
    private void multiRequest(UploadParamBuilder builder) {
        RxQiNiu.getInstance()
                .upload(builder)
                .compose(RxQiNiuTransformer.retry(3, APIQiNiuImpl.get().requestQiNiuToken()))
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(showLoading("正在上传..."))
                .subscribeOn(AndroidSchedulers.mainThread())
                .doOnError(Functions.actionConsumer(hideLoading()))
                .observeOn(AndroidSchedulers.mainThread())
                .zipWith(Observable.range(1, builder.getRequestList().size()), (uploadResult, integer) -> {
                    Logger.d("call: " + "index: " + integer);
                    LoadingHelper.showMaterLoading(getContext(),
                            String.format(Locale.getDefault(), "第%s张上传完毕", integer));

                    int position = getAdapter().getItemCount();
                    int index = builder.getRequestList().indexOf(uploadResult.getUploadRequest());
                    getAdapter().add(new ItemQiNiuImageVModel(uploadResult.getImageUrl(), String.valueOf(index)));
                    getAdapter().notifyItemInserted(position);
                    return uploadResult;
                })
                .compose(RxVMLifecycle.bindViewModel(this))
                .subscribe(RxActions.printVariable(getClass().getSimpleName() + "_result"), RxActions.printThrowable(), hideLoading());

    }

    /**
     * function：批量上传参数配置
     *
     * @param isQueue 是否异步上传
     * @return
     */
    private UploadParamBuilder getMultiUploadParamBuilder(boolean isQueue) {
        List<UploadRequest> requestList = new ArrayList<>();
        requestList.add(new UploadRequest(src.get()));
        requestList.add(new UploadRequest(src.get()));
        requestList.add(new UploadRequest(src.get()));
        requestList.add(new UploadRequest(src.get()));
        requestList.add(new UploadRequest(src.get()));
        requestList.add(new UploadRequest(src.get()));
        requestList.add(new UploadRequest(src.get()));
        return new UploadParamBuilder(isQueue ? UploadParamBuilder.MULTI_REQUEST | UploadParamBuilder.QUEUE_REQUEST :
                UploadParamBuilder.MULTI_REQUEST)
                .requestList(requestList)
                .keyGenerator(keyGenerator())
                .tokenGenerator(APIQiNiuImpl.get().requestQiNiuToken());
    }

    /**
     * function：单张上传参数配置
     *
     * @return
     */
    protected UploadParamBuilder getSingleUploadParamBuilder() {
        return new UploadParamBuilder(UploadParamBuilder.SINGLE_REQUEST)
                .request(new UploadRequest(src.get()))
                .keyGenerator(keyGenerator())
                .tokenGenerator(APIQiNiuImpl.get().requestQiNiuToken());
    }

    /**
     * 上传单张 click
     */
    public View.OnClickListener uploadSingle() {
        return v -> onUploadSingle();
    }

    /**
     * 上传单张
     */
    protected void onUploadSingle() {
        if (Strings.isEmpty(src.get())) {
            UIHelper.snackBar(getRootView(), "请选择图片后再上传");
            return;
        }
        RxQiNiu.getInstance()
                .upload(getSingleUploadParamBuilder())
                .compose(RxQiNiuTransformer.retry(3, APIQiNiuImpl.get().requestQiNiuToken()))
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> showLoading("正在上传..."))
                .subscribeOn(AndroidSchedulers.mainThread())
                .doOnComplete(() -> LoadingHelper.hideMaterLoading())
                .doOnError(Functions.actionConsumer(hideLoading()))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(uploadResult -> {
                    remoteUrl.set(uploadResult.getImageUrl());
                    Logger.d("call: " + "result: " + uploadResult);
                }, RxActions.printThrowable());
    }

    private KeyGenerator keyGenerator() {
        return fileName -> UUID.randomUUID().toString();
    }

    public ObservableField<String> getRemoteUrl() {
        return remoteUrl;
    }

    @Override
    public int getItemLayoutId() {
        return R.layout.activity_rx_qiniu_demo;
    }

    private Consumer<Disposable> showLoading(final String msg) {
        return disposable -> LoadingHelper.showMaterLoading(getContext(), msg);
    }

    private Action hideLoading() {
        return () -> LoadingHelper.hideMaterLoading();
    }
}
