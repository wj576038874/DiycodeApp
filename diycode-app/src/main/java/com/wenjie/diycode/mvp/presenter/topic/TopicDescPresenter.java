package com.wenjie.diycode.mvp.presenter.topic;

import com.gcssloop.diycode_sdk.api.topic.bean.TopicContent;
import com.wenjie.diycode.mvp.model.OnLoadDatasListener;
import com.wenjie.diycode.mvp.model.topic.ITopicModel;
import com.wenjie.diycode.mvp.model.topic.TopicModel;
import com.wenjie.diycode.mvp.view.topic.ITopicDesc;

/**
 * @ProjectName: DiycodeApp
 * @PackageName: com.wenjie.diycode.mvp.presenter.topic
 * @FileName: com.wenjie.diycode.mvp.presenter.topic.TopicDescPresenter.java
 * @Author: wenjie
 * @Date: 2017-08-18 14:58
 * @Description:
 * @Version:
 */
public class TopicDescPresenter {
    private ITopicModel topicModel;
    private ITopicDesc topicDesc;

    public TopicDescPresenter(ITopicDesc topicDesc){
        this.topicDesc = topicDesc;
        this.topicModel = new TopicModel();
    }

    public void getTopicContent(){
        topicModel.loadTopicContent(topicDesc.getId(), new OnLoadDatasListener<TopicContent>() {
            @Override
            public void onSuccess(TopicContent topicContent) {
                topicDesc.setData(topicContent);
            }

            @Override
            public void onFailure(String eroor) {

            }
        });
    }
}
