package io.ganguo.demo.viewmodel.activity;

import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import io.ganguo.demo.R;
import io.ganguo.demo.database.helper.BoxSportHelper;
import io.ganguo.demo.database.model.SportModel;
import io.ganguo.demo.entity.CommonEntity;
import io.ganguo.demo.viewmodel.item.ItemDemoVModel;
import io.ganguo.demo.viewmodel.loading.CenterLoadingVModel;
import io.ganguo.demo.viewmodel.page.EmptyDataViewModel;
import io.ganguo.library.ui.view.ActivityInterface;
import io.ganguo.rx.RxActions;
import io.ganguo.rx.RxCollections;
import io.ganguo.rx.RxFilter;
import io.ganguo.viewmodel.common.HFSRecyclerViewModel;
import io.ganguo.viewmodel.common.HeaderItemViewModel;
import io.ganguo.viewmodel.common.HeaderViewModel;
import io.ganguo.viewmodel.common.TextViewModel;
import io.ganguo.viewmodel.databinding.IncludeHfSwipeRecyclerBinding;
import io.ganguo.viewmodel.callback.ICenterLoadingView;
import io.ganguo.vmodel.BaseViewModel;
import io.ganguo.vmodel.ViewModelHelper;
import io.ganguo.vmodel.rx.RxVMLifecycle;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.Functions;
import io.reactivex.schedulers.Schedulers;

/**
 * ObjectBox -- 数据库Demo ViewModel
 * Created by leo on 2018/11/10.
 */
public class ObjectBoxDemoVModel extends HFSRecyclerViewModel<ActivityInterface<IncludeHfSwipeRecyclerBinding>> {
    @Override
    public void onViewAttached(View view) {
        getAdapter().disableLoadMore();
        queryAllSport();
    }


    @Override
    public void initHeader(ViewGroup container) {
        super.initHeader(container);
        BaseViewModel headerMenu = new HeaderViewModel
                .Builder()
                .appendItemLeft(putSportViewModel())
                .appendItemRight(deleteSportViewModel())
                .appendItemCenter(querySportViewModel())
                .build();
        ViewModelHelper.bind(container, this, onCreateHeaderVModel());
        ViewModelHelper.bind(container, this, headerMenu);
    }


    /**
     * function：create header ViewModel
     *
     * @return
     */
    protected HeaderViewModel onCreateHeaderVModel() {
        return new HeaderViewModel.Builder()
                .appendItemCenter(new HeaderItemViewModel
                        .TitleItemViewModel(getClass().getSimpleName()))
                .appendItemLeft(new HeaderItemViewModel
                        .BackItemViewModel(getView().getActivity()))
                .build();
    }


    /**
     * function: 插入多条数据
     *
     * @return
     */
    protected BaseViewModel putSportViewModel() {
        return new TextViewModel
                .Builder()
                .paddingRightRes(R.dimen.dp_10)
                .paddingLeftRes(R.dimen.dp_10)
                .paddingBottomRes(R.dimen.dp_10)
                .paddingTopRes(R.dimen.dp_10)
                .content("插入数据")
                .onClickListener(v -> addSportData())
                .build();
    }

    /**
     * function: 插入运动数据
     */
    private void addSportData() {
        BoxSportHelper.putSports();
    }


    /**
     * function: 删除全部数据
     *
     * @return
     */
    protected BaseViewModel deleteSportViewModel() {
        return new TextViewModel
                .Builder()
                .paddingRightRes(R.dimen.dp_10)
                .paddingLeftRes(R.dimen.dp_10)
                .paddingBottomRes(R.dimen.dp_10)
                .paddingTopRes(R.dimen.dp_10)
                .content("删除全部数据")
                .onClickListener(v -> {
                    BoxSportHelper.deleteSport();
                })
                .build();
    }

    /**
     * function: 查询全部数据
     *
     * @return
     */
    protected BaseViewModel querySportViewModel() {
        return new TextViewModel
                .Builder()
                .content("查询全部数据")
                .paddingRightRes(R.dimen.dp_10)
                .paddingLeftRes(R.dimen.dp_10)
                .paddingBottomRes(R.dimen.dp_10)
                .paddingTopRes(R.dimen.dp_10)
                .onClickListener(v -> {
                    queryAllSport();
                })
                .build();
    }

    /**
     * function: 查询全部运动数据
     *
     * @return
     */
    protected void queryAllSport() {
        BoxSportHelper
                .getQueryObservable()
                .subscribeOn(Schedulers.io())
                .flatMap((Function<List<SportModel>, ObservableSource<?>>) sportModels -> addSportModels(sportModels))
                .compose(RxVMLifecycle.bindViewModel(this))
                .subscribe(Functions.emptyConsumer(), RxActions.printThrowable(getClass().getSimpleName() + "--queryAllSpotData--"));
    }


    /**
     * function: 添加数据到Adapter
     *
     * @return
     */
    protected Observable<List<ItemDemoVModel>> addSportModels(List<SportModel> sportModels) {
        return Observable
                .just(sportModels)
                .compose(RxCollections.filterNotEmpty())
                .compose(RxCollections.emitItems())
                .compose(RxFilter.filterNotNull())
                .observeOn(AndroidSchedulers.mainThread())
                .map(splashVModel -> createItemDemoVModel(splashVModel))
                .toList()
                .toObservable()
                .doOnNext(itemDemoVModels -> {
                    getAdapter().clear();
                    getAdapter().addAll(itemDemoVModels);
                    getAdapter().notifyDataSetChanged();
                    toggleEmptyView();
                });
    }


    /**
     * function: create Item ViewModel
     *
     * @return
     */
    public ItemDemoVModel createItemDemoVModel(SportModel sportModel) {
        CommonEntity entity = new CommonEntity();
        entity.setId(sportModel.getId());
        entity.setName(entity.getName());
        return new ItemDemoVModel<CommonEntity>()
                .setBtnText("删除")
                .setData(entity)
                .setContent(sportModel.getName())
                .setClickAction(v -> {
                    CommonEntity commonEntity = (CommonEntity) v.getData();
                    getAdapter().remove(v);
                    BoxSportHelper.deleteSport(commonEntity.getId());
                });
    }


    @Override
    public ICenterLoadingView createLoadingView() {
        return new CenterLoadingVModel();
    }

    @Override
    public void initEmpty(ViewGroup container) {
        super.initEmpty(container);
        ViewModelHelper.bind(container, this, new EmptyDataViewModel());
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        queryAllSport();
    }
}
