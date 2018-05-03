package com.wenjie.diycode.mvp.model.login;

import com.gcssloop.diycode_sdk.api.topic.bean.Topic;
import com.wenjie.diycode.mvp.model.OnLoadDatasListener;

import java.util.List;

/**
 * @ProjectName: DiycodeApp
 * @PackageName: com.wenjie.diycode.mvp.model.login
 * @FileName: com.wenjie.diycode.mvp.model.login.IMyTopicModel.java
 * @Author: wenjie
 * @Date: 2017-09-30 12:27
 * @Description:
 * @Version:
 */
public interface IMyTopicModel {
    void loadUserCollectionTopicList(String loginName , Integer offset, Integer limit, OnLoadDatasListener<List<Topic>> onLoadDatasListener);
}
