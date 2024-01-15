package io.ganguo.demo.viewmodel.activity;

import android.view.View;

import java.util.Arrays;

import io.ganguo.demo.R;
import io.ganguo.demo.databinding.ActivityViewPagerDemoBinding;
import io.ganguo.demo.entity.ImageEntity;
import io.ganguo.demo.util.anim.ZoomOutPageTransformer;
import io.ganguo.demo.viewmodel.item.ItemPagerCardVModel;
import io.ganguo.vmodel.BaseViewModel;
import io.ganguo.vmodel.ViewModelHelper;
import io.ganguo.library.ui.view.ActivityInterface;
import io.ganguo.viewmodel.common.CommonViewPagerVModel;

/**
 * update by leo on 2018/07/06.
 * CommonViewPagerVModel - demo
 */
public class ViewPagerDemoVModel extends BaseViewModel<ActivityInterface<ActivityViewPagerDemoBinding>> {
    private CommonViewPagerVModel pagerVModel;

    @Override
    public void onViewAttached(View view) {
        ViewModelHelper.bind(getView().getBinding().includeViewpager, this, getPagerVModel());
    }


    /**
     * function：get CommonViewPagerVModel
     *
     * @return
     */
    public CommonViewPagerVModel getPagerVModel() {
        if (pagerVModel == null) {
            ImageEntity image = new ImageEntity("kk", "http://d.hiphotos.baidu.com/zhidao/pic/item/4e4a20a4462309f7ab5f674a700e0cf3d7cad6b2.jpg");
            pagerVModel = new CommonViewPagerVModel(
                    Arrays.asList(
                            new ItemPagerCardVModel(image),
                            new ItemPagerCardVModel(image),
                            new ItemPagerCardVModel(image),
                            new ItemPagerCardVModel(image)
                    ))
                    .setViewPagerSmoothScroll(true)
                    .setPageMargin(getDimensionPixelOffsets(R.dimen.dp_5))
                    .setClipChildren(false)
                    .setClipPadding(false)
                    .setPaddingLeft(getDimensionPixelOffsets(R.dimen.dp_25))
                    .setPaddingRight(getDimensionPixelOffsets(R.dimen.dp_25));

        }
        return pagerVModel;
    }


    @Override
    public int getItemLayoutId() {
        return R.layout.activity_view_pager_demo;
    }

}
