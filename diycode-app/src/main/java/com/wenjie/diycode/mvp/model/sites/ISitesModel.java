package com.wenjie.diycode.mvp.model.sites;

import com.gcssloop.diycode_sdk.api.sites.bean.Sites;
import com.wenjie.diycode.mvp.model.OnLoadDatasListener;

import java.util.List;

/**
 * @ProjectName: DiycodeApp
 * @PackageName: com.wenjie.diycode.mvp.model.sites
 * @FileName: com.wenjie.diycode.mvp.model.sites.ISitesModel.java
 * @Author: wenjie
 * @Date: 2017-08-17 14:42
 * @Description:
 * @Version:
 */
public interface ISitesModel {
    void loadSites(OnLoadDatasListener<List<Sites>> onLoadDatasListener);
}
