package com.wenjie.diycode.mvp.presenter.topic;

import com.gcssloop.diycode_sdk.api.topic.bean.Topic;
import com.wenjie.diycode.mvp.model.OnLoadDatasListener;
import com.wenjie.diycode.mvp.model.topic.ITopicModel;
import com.wenjie.diycode.mvp.model.topic.TopicModel;
import com.wenjie.diycode.mvp.view.topic.ITopicView;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @ProjectName: DiycodeApp
 * @PackageName: com.wenjie.diycode.mvp.presenter.topic
 * @FileName: com.wenjie.diycode.mvp.presenter.topic.UserTopicPresenter.java
 * @Author: wenjie
 * @Date: 2017-09-29 15:12
 * @Description:
 * @Version:
 */
public class UserTopicPresenter {

    private ITopicModel topicModel;
    private ITopicView topicView;

    public UserTopicPresenter(ITopicView mTopicView){
        this.topicModel = new TopicModel();
        topicView = checkNotNull(mTopicView, "tasksView cannot be null!");
    }


    public void getUserTopicList(String loginName) {
        topicView.showLoading();
        topicModel.loadUserTopicList(loginName, null, topicView.getOffset(), topicView.getLimit(), new OnLoadDatasListener<List<Topic>>() {
            @Override
            public void onSuccess(List<Topic> topics) {
                topicView.hideLoading();
                topicView.setTopics(topics);
            }

            @Override
            public void onFailure(String eroor) {
                topicView.hideLoading();
                topicView.showFailure();
            }
        });
    }

    public void refreshData(String loginName){
        topicModel.loadUserTopicList(loginName, null, topicView.getOffset(), topicView.getLimit(), new OnLoadDatasListener<List<Topic>>() {
            @Override
            public void onSuccess(List<Topic> topics) {
                topicView.setReferesh(topics);
            }

            @Override
            public void onFailure(String eroor) {
                topicView.showFailure();
            }
        });
    }

    public void getTopicsListMore(String loginName) {
        topicModel.loadUserTopicList(loginName, null, topicView.getOffset(), topicView.getLimit(), new OnLoadDatasListener<List<Topic>>() {
            @Override
            public void onSuccess(List<Topic> topics) {
                topicView.setTopicsMore(topics);
            }

            @Override
            public void onFailure(String eroor) {
                topicView.showFailure();
            }
        });
    }
}
