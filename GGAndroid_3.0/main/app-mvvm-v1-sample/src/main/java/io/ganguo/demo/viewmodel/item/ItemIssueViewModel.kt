package io.ganguo.demo.viewmodel.item

import android.view.View

import io.ganguo.demo.R
import io.ganguo.demo.databinding.ItemIssueBinding
import io.ganguo.demo.entity.IssueEntity
import io.ganguo.viewmodel.core.viewinterface.ViewInterface
import io.ganguo.viewmodel.core.BaseViewModel

/**
 * Created by hulkyao on 13/6/2017.
 */

class ItemIssueViewModel(issue: IssueEntity) : BaseViewModel<ViewInterface<ItemIssueBinding>>() {
    override val layoutId: Int by lazy {
        R.layout.item_issue
    }
    var issueTitle: String? = null

    init {
        this.issueTitle = issue.title
    }

    override fun onViewAttached(view: View) {

    }


}
