package com.wenjie.diycode.mvp.presenter.sites;

import com.gcssloop.diycode_sdk.api.sites.bean.Sites;
import com.wenjie.diycode.mvp.model.OnLoadDatasListener;
import com.wenjie.diycode.mvp.model.sites.ISitesModel;
import com.wenjie.diycode.mvp.model.sites.SitesModel;
import com.wenjie.diycode.mvp.view.sites.ISitesFragment;

import java.util.List;

/**
 * @ProjectName: DiycodeApp
 * @PackageName: com.wenjie.diycode.mvp.presenter.sites
 * @FileName: com.wenjie.diycode.mvp.presenter.sites.SitesPresenter.java
 * @Author: wenjie
 * @Date: 2017-08-17 14:46
 * @Description:
 * @Version:
 */
public class SitesPresenter {
    private ISitesModel sitesModel;
    private ISitesFragment sitesFragment;

    public SitesPresenter(ISitesFragment sitesFragment) {
        this.sitesFragment = sitesFragment;
        this.sitesModel = new SitesModel();
    }

    public void getSites() {
        sitesModel.loadSites(new OnLoadDatasListener<List<Sites>>() {
            @Override
            public void onSuccess(List<Sites> sites) {
                sitesFragment.setDatas(sites);
            }

            @Override
            public void onFailure(String eroor) {
                sitesFragment.showFailure();
            }
        });
    }

    public void refresh() {
        sitesModel.loadSites(new OnLoadDatasListener<List<Sites>>() {
            @Override
            public void onSuccess(List<Sites> sites) {
                sitesFragment.refresh(sites);
            }

            @Override
            public void onFailure(String eroor) {
                sitesFragment.showFailure();
            }
        });
    }
}
