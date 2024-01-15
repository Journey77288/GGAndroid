package io.scanner.sample.viewmodel;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import io.ganguo.rx.RxActions;
import io.ganguo.scanner.CodeEncryptHelper;
import io.ganguo.scanner.builder.TextViewConfig;
import io.ganguo.utils.util.log.Logger;
import io.ganguo.viewmodel.common.HFRecyclerViewModel;
import io.ganguo.viewmodel.common.HeaderItemViewModel;
import io.ganguo.viewmodel.common.HeaderViewModel;
import io.ganguo.viewmodel.common.ImageViewModel;
import io.ganguo.vmodel.ViewModelHelper;
import io.ganguo.vmodel.rx.RxVMLifecycle;
import io.ganguo.library.ui.view.ActivityInterface;
import io.ganguo.viewmodel.databinding.IncludeHfRecyclerBinding;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.internal.functions.Functions;
import io.reactivex.schedulers.Schedulers;
import io.scanner.sample.R;

/**
 * <p>
 * 生成二维码&&条形码Demo
 * </p>
 * Created by leo on 2018/8/9.
 */
public class CreateCodeSampleVModel extends HFRecyclerViewModel<ActivityInterface<IncludeHfRecyclerBinding>> {


    /**
     * <p>
     * init header
     * </p>
     */
    @Override
    public void initHeader(ViewGroup container) {
        super.initHeader(container);
        HeaderViewModel headerViewModel = new HeaderViewModel.Builder()
                .appendItemCenter(new HeaderItemViewModel.TitleItemViewModel("生成二维码&&条形码"))
                .appendItemLeft(new HeaderItemViewModel.BackItemViewModel(getView().getActivity()))
                .build();
        ViewModelHelper.bind(container, this, headerViewModel);
    }

    @Override
    public void onViewAttached(View view) {
        createCodeBitmap();
    }

    /**
     * function: 生成二维码&&条形码
     *
     * @param
     */
    protected void createCodeBitmap() {
        Observable
                .concat(onCreateQrCode(), onCreateAddLogoQrCode(), onCreateBarCode())
                .subscribeOn(Schedulers.computation())
                .compose(RxVMLifecycle.bindViewModel(this))
                .toList()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess(imageViewModels -> {
                    Logger.e("createCodeBitmap:imageViewModels:" + imageViewModels.size());
                    getAdapter().addAll(imageViewModels);
                    getAdapter().notifyDataSetChanged();
                })
                .doFinally(() -> toggleEmptyView())
                .subscribe(Functions.emptyConsumer(), RxActions.printThrowable(getClass().getSimpleName() + "--createCodeBitmap--"));
    }

    /**
     * function: 生成二维码
     *
     * @return
     */
    protected Observable<ImageViewModel> onCreateQrCode() {
        return Observable
                .just("测试生成二维码")
                .map(s -> CodeEncryptHelper.createQRCode(s, getDimensionPixelOffsets(R.dimen.dp_200), getDimensionPixelOffsets(R.dimen.dp_200)))
                .map(bitmap -> getImageViewModel(bitmap));
    }

    /**
     * function: 生成带Logo的二维码
     *
     * @return
     */
    protected Observable<ImageViewModel> onCreateAddLogoQrCode() {
        return Observable
                .just("测试生成带Logo的二维码")
                .map(s -> {
                    int size = getDimensionPixelOffsets(R.dimen.dp_200);
                    Bitmap logo = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.ic_launcher);
                    CodeEncryptHelper.createLogoQRCode(s, size, size, logo);
                    return CodeEncryptHelper.createLogoQRCode(s, size, size, logo);
                })
                .map(bitmap -> getImageViewModel(bitmap));
    }

    /**
     * function: 生成条形码
     *
     * @return
     */
    protected Observable<ImageViewModel> onCreateBarCode() {
        return Observable.
                just("1123444343")
                .map(s -> {
                    TextViewConfig config = new TextViewConfig
                            .Builder()
                            .setColor(getColors(R.color.green))
                            .setGravity(Gravity.CENTER)
                            .build();
                    int size = getDimensionPixelOffsets(R.dimen.dp_200);
                    Bitmap bitmap = CodeEncryptHelper.createBarCodeWithText(getContext(), s, size, size / 2, config);
                    return bitmap;
                })
                .map(bitmap -> getImageViewModel(bitmap));
    }

    /**
     * function: create ImageViewModel
     *
     * @param bitmap
     * @return
     */
    protected ImageViewModel getImageViewModel(Bitmap bitmap) {
        return new ImageViewModel
                .Builder()
                .width(ImageViewModel.MATCH_PARENT)
                .height(R.dimen.dp_300)
                .setBitmap(bitmap)
                .scaleType(ImageView.ScaleType.CENTER_INSIDE)
                .build();
    }


}
