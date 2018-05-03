package com.wenjie.diycode.mvp.view.sites;

import com.gcssloop.diycode_sdk.api.sites.bean.Sites;

import java.util.List;

/**
 * @ProjectName: DiycodeApp
 * @PackageName: com.wenjie.diycode.mvp.view.sites
 * @FileName: com.wenjie.diycode.mvp.view.sites.ISitesFragment.java
 * @Author: wenjie
 * @Date: 2017-08-17 14:47
 * @Description:
 * @Version:
 */
public interface ISitesFragment {

    void showFailure();

    void setDatas(List<Sites> sites);


    void refresh(List<Sites> sites);

    void showLoading();

    void hideLoading();
}
