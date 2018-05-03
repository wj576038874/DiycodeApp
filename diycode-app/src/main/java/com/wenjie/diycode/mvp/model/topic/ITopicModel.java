package com.wenjie.diycode.mvp.model.topic;

import com.gcssloop.diycode_sdk.api.topic.bean.Topic;
import com.gcssloop.diycode_sdk.api.topic.bean.TopicContent;
import com.wenjie.diycode.mvp.model.OnLoadDatasListener;

import java.util.List;

/**
 * @ProjectName: DiycodeApp
 * @PackageName: com.wenjie.diycode.mvp.model.topic
 * @FileName: com.wenjie.diycode.mvp.model.topic.ITopicModel.java
 * @Author: wenjie
 * @Date: 2017-08-04 12:05
 * @Description:
 * @Version:
 */
public interface ITopicModel {

    void loadTopicsList(String type, Integer node_id, Integer offset, Integer limit, OnLoadDatasListener<List<Topic>> onLoadDatasListener);

    void loadTopicContent(int id, OnLoadDatasListener<TopicContent> onLoadDatasListener);

    void loadUserTopicList(String login_name, String order, Integer offset, Integer limit,OnLoadDatasListener<List<Topic>> onLoadDatasListener);
}
