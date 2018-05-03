package com.wenjie.diycode.mvp.view.topic;

import com.gcssloop.diycode_sdk.api.topic.bean.Topic;

import java.util.List;

/**
 * @ProjectName: DiycodeApp
 * @PackageName: com.wenjie.diycode.mvp.view.topic
 * @FileName: com.wenjie.diycode.mvp.view.topic.ITopicView.java
 * @Author: wenjie
 * @Date: 2017-08-04 12:40
 * @Description:
 * @Version:
 */
public interface ITopicView {
    Integer getLimit();

    Integer getOffset();

    void setTopics(List<Topic> topics);

    void setReferesh(List<Topic> topics);

    void setTopicsMore(List<Topic> topics);

    void showLoading();

    void hideLoading();

    void showFailure();
}
