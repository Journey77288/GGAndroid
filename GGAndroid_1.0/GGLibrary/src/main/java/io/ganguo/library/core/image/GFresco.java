package io.ganguo.library.core.image;

import android.app.ActivityManager;
import android.content.Context;
import android.net.Uri;

import com.facebook.common.internal.Supplier;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.DraweeView;
import com.facebook.imagepipeline.cache.MemoryCacheParams;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.decoder.ProgressiveJpegConfig;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.image.ImmutableQualityInfo;
import com.facebook.imagepipeline.image.QualityInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by rick on 4/7/15.
 * 使用前必须在Context里 调用init
 */
public class GFresco {

    private Context mContext;
    private volatile static GFresco instance;
    private int mPlaceHolderImage = -1;


    private int mPrgressImage = -1;

    /**
     * Returns singleton class instance
     */
    public static GFresco getInstance() {
        if (instance == null) {
            synchronized (ImageLoader.class) {
                if (instance == null) {
                    instance = new GFresco();
                }
            }
        }
        return instance;
    }


    public void init(Context context) {


        int memClass = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass();
        int cacheSize = 1024 * 1024 * memClass / 8;  //系统可用内存的1/8


        final MemoryCacheParams bitmapCacheParams = new MemoryCacheParams(
                cacheSize, // Max total size of elements in the cache
                Integer.MAX_VALUE,                     // Max entries in the cache
                cacheSize, // Max total size of elements in eviction queue
                Integer.MAX_VALUE,                     // Max length of eviction queue
                Integer.MAX_VALUE);                    // Max cache entry size

        Supplier<MemoryCacheParams> memoryCacheConfig = new Supplier<MemoryCacheParams>() {
            public MemoryCacheParams get() {

                return bitmapCacheParams;
            }
        };


//        DiskCacheConfig diskCacheConfig = DiskCacheConfig.newBuilder()
//                .setBaseDirectoryName(Config.getImageCachePath())
//                .setMaxCacheSize(50 * 1024 * 1024)
//                .setMaxCacheSizeOnLowDiskSpace(50 * 1024 * 1024)
//                .setMaxCacheSizeOnVeryLowDiskSpace(50 * 1024 * 1024)
//                .setVersion(0)
//                .build();


        ProgressiveJpegConfig pjpegConfig = new ProgressiveJpegConfig() {
            @Override
            public int getNextScanNumberToDecode(int scanNumber) {
                return scanNumber + 2;
            }

            public QualityInfo getQualityInfo(int scanNumber) {
                boolean isGoodEnough = (scanNumber >= 5);
                return ImmutableQualityInfo.of(scanNumber, isGoodEnough, false);
            }
        };


        /*线程池默认配置：
         3个线程用作网络下载 - forBackground()
         2个线程用作文件读写操作 - forLocalStorageRead() & forLocalStorageWrite()
         2个线程用作图片解码,转换,过滤 - forDecode() & forTransForm()
         */
        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(context)
                .setBitmapMemoryCacheParamsSupplier(memoryCacheConfig)
                .setProgressiveJpegConfig(pjpegConfig)
                .build();

        Fresco.initialize(context, config);
        mContext = context;

    }

    public void displayImage(DraweeView draweeView, String largeImage, String smallImage) {
        displayImage(draweeView, largeImage, smallImage, null);
    }

    public void displayImage(DraweeView draweeView, String largeImage, ControllerListener controllerListener) {
        displayImage(draweeView, largeImage, null, controllerListener);
    }

    public void displayImage(DraweeView draweeView, String largeImage) {
        displayImage(draweeView, largeImage, null, null);
    }

    public void displayImage(DraweeView draweeView, String largeImage, String smallImage, ControllerListener<ImageInfo> controllerListener) {
        if (draweeView == null) {
            throw new IllegalArgumentException("draweeView must not be null");
        }

        PipelineDraweeControllerBuilder controllerBuilder = Fresco.newDraweeControllerBuilder();

        if (smallImage != null) {
            Uri smallImageUri = Uri.parse(smallImage);
            ImageRequest lowResRequest = ImageRequestBuilder.newBuilderWithSource(smallImageUri)
                    .setLocalThumbnailPreviewsEnabled(true)
                    .setProgressiveRenderingEnabled(true)
                    .build();
            controllerBuilder.setLowResImageRequest(lowResRequest);
        }

        Uri lageImageUri = Uri.parse(largeImage);
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(lageImageUri)
                .setLocalThumbnailPreviewsEnabled(true)
                .setProgressiveRenderingEnabled(true)
                .build();


        controllerBuilder
                .setImageRequest(request)
                .setTapToRetryEnabled(true)
                .setOldController(draweeView.getController());


        DraweeController controller = controllerBuilder
                .setControllerListener(controllerListener)
                .build();

        GenericDraweeHierarchyBuilder builder =
                new GenericDraweeHierarchyBuilder(mContext.getResources());


        if (mPlaceHolderImage != -1) {
            builder.setPlaceholderImage(mContext.getResources().getDrawable(mPlaceHolderImage));
        }
        if (mPrgressImage != -1) {
            builder.setProgressBarImage(mContext.getResources().getDrawable(mPrgressImage));
        }

        GenericDraweeHierarchy hierarchy = builder
                .setFadeDuration(300)
                .build();
        draweeView.setHierarchy(hierarchy);
        draweeView.setController(controller);
    }


    public void setPlaceHolderImage(int id) {
        this.mPlaceHolderImage = id;
    }

    public void setPrgressImage(int id) {
        this.mPrgressImage = mPrgressImage;
    }
}
