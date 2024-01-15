package io.ganguo.incubator.entity.db;

import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.View;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.afollestad.materialdialogs.MaterialDialog;

import io.ganguo.incubator.R;
import io.ganguo.incubator.bean.Constants;
import io.ganguo.library.common.ToastHelper;
import io.ganguo.library.ui.adapter.v7.Adaptable;
import io.ganguo.library.ui.adapter.v7.ListAdapter;
import io.ganguo.library.ui.adapter.v7.ViewHolder.LayoutId;
import io.ganguo.library.util.Benchmark;
import io.ganguo.library.util.Strings;
import io.ganguo.library.util.Systems;
import io.ganguo.library.util.Tasks;
import io.ganguo.library.util.log.Logger;
import io.ganguo.library.util.log.LoggerFactory;

/**
 * Created by Wilson on 10/12/15.
 */
@Table(name = "employee", id = "_id")
public class Employee extends Model implements LayoutId, Adaptable {
    private final static Logger LOG = LoggerFactory.getLogger(Employee.class);

    @Column(name = "name")
    public String name;

    @Column(name = "in_group")
    public Group group;

    @Override
    public String toString() {
        return "Employee{" +
                "group=" + group +
                ", name='" + name + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    @Override
    public int getItemLayoutId() {
        return R.layout.item_employee;
    }

    private RecyclerView.Adapter mAdapter;
    private boolean isDeleted;

    @Override
    public void setAdapter(RecyclerView.Adapter adapter) {
        this.mAdapter = adapter;
    }

    public void onDeleteClick(final View view) {
        if (isDeleted) {
            //连击
            return;
        }
        if (mAdapter != null && mAdapter instanceof ListAdapter) {
            isDeleted = true;
            //notify ui
            int index = ((ListAdapter) mAdapter).indexOf(this);
            ((ListAdapter) mAdapter).remove(index);
            mAdapter.notifyItemRemoved(index);

            ToastHelper.showMessageMiddle(view.getContext(), index + " on delete click ");

            //db
            Tasks.runOnQueue(new Runnable() {
                @Override
                public void run() {
                    Benchmark.start(Constants.TEST_ORM + "_delete");
                    Employee.this.delete();
                    Benchmark.end(Constants.TEST_ORM + "_delete");
                }
            });
        }

    }

    public void onAlter(final View view) {
        new MaterialDialog.Builder(view.getContext())
                .title("Edit Info")
                .content("pls enter correct information.")
                .inputType(InputType.TYPE_CLASS_TEXT)
                .input("employee name", name, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(MaterialDialog dialog, CharSequence input) {
                        // Do something
                        LOG.d("input:" + input);
                        if (Strings.isEmpty(input.toString())) {
                            ToastHelper.showMessageMiddle(view.getContext(), "employee name cannot be null.");
                            return;
                        }
                        alterName(input.toString());
                    }
                })
                .dismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        Systems.hideKeyboard(view.getContext());
                    }
                })
                .show();
    }

    private void alterName(String name) {
        setName(name);
        if (mAdapter != null && mAdapter instanceof ListAdapter) {
            //notify ui
            int index = ((ListAdapter) mAdapter).indexOf(this);
            mAdapter.notifyItemChanged(index);

            //db
            Tasks.runOnQueue(new Runnable() {
                @Override
                public void run() {
                    Benchmark.start(Constants.TEST_ORM + "_alter_save");
                    Employee.this.save();
                    Benchmark.end(Constants.TEST_ORM + "_alter_save");
                }
            });
        }


    }
}
