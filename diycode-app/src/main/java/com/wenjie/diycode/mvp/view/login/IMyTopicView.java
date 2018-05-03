package com.wenjie.diycode.mvp.view.login;

import com.gcssloop.diycode_sdk.api.topic.bean.Topic;

import java.util.List;

/**
 * @ProjectName: DiycodeApp
 * @PackageName: com.wenjie.diycode.mvp.view.login
 * @FileName: com.wenjie.diycode.mvp.view.login.IMyTopicView.java
 * @Author: wenjie
 * @Date: 2017-09-30 12:26
 * @Description:
 * @Version:
 */
public interface IMyTopicView {
    void showLoading();

    void hideLoading();

    void setDatas(List<Topic> datas);

    void refresh(List<Topic> datas);

    Integer getLimit();

    Integer getOffset();

    void setLoadMore(List<Topic> datas);
}
