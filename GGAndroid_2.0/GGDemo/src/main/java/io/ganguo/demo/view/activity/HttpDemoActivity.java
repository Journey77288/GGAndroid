package io.ganguo.demo.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;


import org.parceler.Parcels;

import io.ganguo.demo.bean.Constants;
import io.ganguo.demo.entity.CommonDemoEntity;
import io.ganguo.demo.view.activity.base.GGHFSRVModelActivity;
import io.ganguo.demo.view.activity.base.GGVModelActivity;
import io.ganguo.demo.viewmodel.activity.HttpDemoVModel;
import io.ganguo.utils.util.log.Logger;
import io.ganguo.viewmodel.databinding.IncludeHfSwipeRecyclerBinding;

/**
 * Http请求Demo
 * Created by leo on 2018/7/30.
 */
public class HttpDemoActivity extends GGHFSRVModelActivity<IncludeHfSwipeRecyclerBinding, HttpDemoVModel> {

    public static Intent intentFor(Context context, CommonDemoEntity entity) {
        //数据装箱
        Parcelable parcelable = Parcels.wrap(CommonDemoEntity.class, entity);
        Intent intent = new Intent(context, HttpDemoActivity.class);
        intent.putExtra(Constants.DATA, parcelable);
        return intent;
    }

    @Override
    public HttpDemoVModel createViewModel() {
        return new HttpDemoVModel();
    }

    @Override
    public void onViewAttached(HttpDemoVModel viewModel) {
        super.onViewAttached(viewModel);
        Parcelable parcelable = getIntent().getParcelableExtra(Constants.DATA);
        //数据拆箱
        CommonDemoEntity entity = Parcels.unwrap(parcelable);
        Logger.e("Parcels.unwrap:CommonDemoEntity:" + entity.toString());
    }

}
