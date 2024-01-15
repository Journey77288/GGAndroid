package io.ganguo.demo.view.activity;

import android.content.Context;
import android.content.Intent;

import io.ganguo.demo.view.activity.base.GGVModelActivity;
import io.ganguo.demo.viewmodel.activity.ObjectBoxDemoVModel;
import io.ganguo.viewmodel.databinding.IncludeHfSwipeRecyclerBinding;

/**
 * ObjectBox  - 数据库 Demo
 * Created by leo on 2018/11/10.
 */
public class ObjectBoxDemoActivity extends GGVModelActivity<IncludeHfSwipeRecyclerBinding, ObjectBoxDemoVModel> {

    public static Intent intentFor(Context context) {
        return new Intent(context, ObjectBoxDemoActivity.class);
    }

    @Override
    public ObjectBoxDemoVModel createViewModel() {
        return new ObjectBoxDemoVModel();
    }

    @Override
    public void onViewAttached(ObjectBoxDemoVModel viewModel) {
    }
}
