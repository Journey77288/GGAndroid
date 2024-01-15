package io.ganguo.incubator.ui.activity;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import com.activeandroid.ActiveAndroid;
import com.yqritc.recyclerviewflexibledivider.FlexibleDividerDecoration;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.List;

import io.ganguo.incubator.R;
import io.ganguo.incubator.bean.Constants;
import io.ganguo.incubator.databinding.ActivityDemoBinding;
import io.ganguo.incubator.entity.db.Employee;
import io.ganguo.incubator.entity.db.Group;
import io.ganguo.library.common.ToastHelper;
import io.ganguo.library.core.event.extend.OnSingleClickListener;
import io.ganguo.library.ui.activity.BaseActivity;
import io.ganguo.library.ui.adapter.v7.LoadMoreListener;
import io.ganguo.library.ui.adapter.v7.SimpleAdapter;
import io.ganguo.library.util.Benchmark;
import io.ganguo.library.util.Colors;
import io.ganguo.library.util.Randoms;
import io.ganguo.library.util.Strings;
import io.ganguo.library.util.Tasks;
import io.ganguo.library.util.Views;
import io.ganguo.library.util.log.Logger;
import io.ganguo.library.util.log.LoggerFactory;
import jp.wasabeef.recyclerview.animators.BaseItemAnimator;
import jp.wasabeef.recyclerview.animators.SlideInRightAnimator;

/**
 * Created by Wilson on 10/12/15.
 */
public class DemoActivity extends BaseActivity {
    private final Logger logger = LoggerFactory.getLogger(DemoActivity.class);
    private ActivityDemoBinding binding;
    private SimpleDividerAdapter adapter;

    @Override
    public void beforeInitView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_demo);
    }

    @Override
    public void initView() {
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new SimpleDividerAdapter(this);
//        adapter.onFinishLoadMore(true);
        binding.recyclerView.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(this)
                        .color(Colors.BLUE_LIGHT)
                        .size(2)
                        .marginResId(R.dimen.dp_4, R.dimen.dp_4)
                        .visibilityProvider(adapter)
                        .build());
        binding.recyclerView.setAdapter(adapter);
        BaseItemAnimator animator = new SlideInRightAnimator(new OvershootInterpolator(1f));
        animator.setAddDuration(500);
        animator.setRemoveDuration(300);
        binding.recyclerView.setItemAnimator(animator);
    }

    private final static class SimpleDividerAdapter extends SimpleAdapter implements FlexibleDividerDecoration.VisibilityProvider {
        public SimpleDividerAdapter(Context context) {
            super(context);
        }

        @Override
        public boolean shouldHideDivider(int position, RecyclerView parent) {
            if (position >= parent.getAdapter().getItemCount() - 2) {
                return true;
            }
            return false;
        }
    }

    @Override
    public void initListener() {
        binding.btnAdd.setOnClickListener(mClickListener);
        binding.btnRandom.setOnClickListener(mClickListener);
        binding.btnClearAll.setOnClickListener(mClickListener);

        adapter.setLoadMoreListener(new LoadMoreListener() {
            @Override
            protected void onLoadMore() {
                queryAndroidEployees();
            }
        });

    }

    @Override
    public void initData() {
//        queryAndroidEployees();
    }

    private final OnSingleClickListener mClickListener = new OnSingleClickListener() {
        @Override
        public void onSingleClick(View v) {
            if (v == binding.btnAdd) {
                addInput();
            } else if (v == binding.btnRandom) {
                random();
            } else if (v == binding.btnClearAll) {
                clearAll();
            }
        }
    };


    private Group getAndroidGroup() {
        Group androidGroup = Group.queryByName("android team");
        if (androidGroup != null) {
            return androidGroup;
        }
        Group group = new Group();
        group.setName("android team");
        group.setYear(2014);
        group.save();
        return group;
    }


    private void addInput() {
        if (Strings.isEmpty(Views.getText(binding.etEmpName))) {
            ToastHelper.showMessageMiddle(DemoActivity.this, "employee name cannot be null.");
            return;
        }
//                Systems.hideKeyboard(DemoActivity.this);
        Group group = getAndroidGroup();
        Employee employee = new Employee();
        employee.setName(Views.getText(binding.etEmpName));
        employee.setGroup(group);
        long start = System.currentTimeMillis();
        Benchmark.start(Constants.TEST_ORM + "_save");
        employee.save();
        Benchmark.end(Constants.TEST_ORM + "_save");
        ToastHelper.showMessageMiddle(DemoActivity.this, "added in " + (System.currentTimeMillis() - start) + " ms.");
        Views.clearText(binding.etEmpName);
        addItem(employee);
    }


    private void addItem(Employee employee) {
        adapter.add(0, employee);
        adapter.notifyItemInserted(0);
//        binding.recyclerView.scrollToPosition(0);
        binding.recyclerView.smoothScrollToPosition(0);
    }

    private void queryAndroidEployees() {
        //async thread
        Tasks.runOnQueue(new Runnable() {
            @Override
            public void run() {
                Benchmark.start(Constants.TEST_ORM + "_query_list");
                final List<Employee> employees = getAndroidGroup().queryEmployees();
                Benchmark.end(Constants.TEST_ORM + "_query_list");

                //call ui thread
                Tasks.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.clear();
                        adapter.addAll(employees);
                        adapter.notifyDataSetChanged();
                        adapter.onFinishLoadMore(true);
                    }
                });
            }
        });


    }

    private void random() {
        binding.btnRandom.setEnabled(false);
        //async thread
        Tasks.runOnQueue(new Runnable() {
            @Override
            public void run() {

                Benchmark.start(Constants.TEST_ORM + "_random_add_100");
                final int size = 100;
                ActiveAndroid.beginTransaction();
                Group androidGroup = getAndroidGroup();
                Employee employee;
                try {
                    for (int i = 0; i < size; i++) {
                        employee = new Employee();
                        employee.setName(Randoms.getRandomCapitalLetters(10));
                        employee.setGroup(androidGroup);
                        employee.save();
                    }
                    ActiveAndroid.setTransactionSuccessful();
                } finally {
                    ActiveAndroid.endTransaction();
                }
                Benchmark.end(Constants.TEST_ORM + "_random_add_100");

                //call ui thread
                Tasks.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        binding.btnRandom.setEnabled(true);
                        //reload
                        queryAndroidEployees();
                    }
                });

            }
        });

    }

    private void clearAll() {
        binding.btnClearAll.setEnabled(false);
        //async thread
        Tasks.runOnQueue(new Runnable() {
            @Override
            public void run() {
                Benchmark.start(Constants.TEST_ORM + "_clear_all");
                getAndroidGroup().clearEmployees();
                Benchmark.start(Constants.TEST_ORM + "_clear_all");

                //call ui thread
                Tasks.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        binding.btnClearAll.setEnabled(true);
                        //reload
                        queryAndroidEployees();
                    }
                });
            }
        });
    }
}
