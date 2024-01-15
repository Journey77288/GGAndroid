package io.ganguo.library.ui.base;

import android.support.v4.app.Fragment;

/**
 * Created by hulkyao on 11/7/2016.
 */

public interface FragmentPresenter {

    void addFragment(Fragment fragment, int containerId, String tag);

    void showFragment(Fragment fragment, int containerId, String tag);

    void hideFragment(Fragment fragment);

    void hideFragment(String tag);

    void detachFragment(Fragment fragment);

    void detachFragment(String tag);

    <S extends Fragment> S findFragmentByTag(String tag);
}
