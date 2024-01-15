package io.ganguo.incubator.entity;

import io.ganguo.incubator.R;
import io.ganguo.library.ui.adapter.v7.ViewHolder.LayoutId;

/**
 * Created by Tony on 10/22/15.
 */
public class Contributor implements LayoutId{

    private String login;
    private int contributions;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public int getContributions() {
        return contributions;
    }

    public void setContributions(int contributions) {
        this.contributions = contributions;
    }

    @Override
    public int getItemLayoutId() {
        return R.layout.item_contributor;
    }
}
