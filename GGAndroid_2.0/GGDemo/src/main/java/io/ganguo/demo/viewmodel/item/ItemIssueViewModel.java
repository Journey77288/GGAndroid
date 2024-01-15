package io.ganguo.demo.viewmodel.item;

import android.view.View;

import io.ganguo.demo.R;
import io.ganguo.demo.entity.IssueEntity;
import io.ganguo.vmodel.BaseViewModel;

/**
 * Created by hulkyao on 13/6/2017.
 */

public class ItemIssueViewModel extends BaseViewModel {

    private String title = null;

    public ItemIssueViewModel(IssueEntity issue) {
        this.title = issue.getTitle();
    }

    @Override
    public void onViewAttached(View view) {

    }

    public String getIssueTitle() {
        return title;
    }

    @Override
    public int getItemLayoutId() {
        return R.layout.item_issue;
    }

}
