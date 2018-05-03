package com.wenjie.diycode.mvp.view.topic;

import com.gcssloop.diycode_sdk.api.topic.bean.TopicContent;

/**
 * @ProjectName: DiycodeApp
 * @PackageName: com.wenjie.diycode.mvp.view.topic
 * @FileName: com.wenjie.diycode.mvp.view.topic.ITopicDesc.java
 * @Author: wenjie
 * @Date: 2017-08-18 14:58
 * @Description:
 * @Version:
 */
public interface ITopicDesc {
    void setData(TopicContent topicContent);

    int getId();
}
