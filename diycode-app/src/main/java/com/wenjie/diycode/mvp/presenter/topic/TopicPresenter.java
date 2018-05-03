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
 * @FileName: com.wenjie.diycode.mvp.presenter.topic.TopicPresenter.java
 * @Author: wenjie
 * @Date: 2017-08-04 12:05
 * @Description:
 * @Version:
 */
public class TopicPresenter implements ITopicPresenter{

    private ITopicModel topicModel;
    private ITopicView topicView;

    public TopicPresenter(ITopicView mTopicView) {
        this.topicModel = new TopicModel();
        topicView = checkNotNull(mTopicView, "tasksView cannot be null!");
    }

    /**
     * 加载所有topic
     */
    @Override
    public void getTopicsList() {
        topicView.showLoading();
        topicModel.loadTopicsList(null, null, topicView.getOffset(), topicView.getLimit(), new OnLoadDatasListener<List<Topic>>() {
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

    /**
     * 加载更多数据
     */
    @Override
    public void getTopicsListMore() {
        topicModel.loadTopicsList(null, null, topicView.getOffset(), topicView.getLimit(), new OnLoadDatasListener<List<Topic>>() {
            @Override
            public void onSuccess(List<Topic> topics) {
                topicView.setTopicsMore(topics);
            }

            @Override
            public void onFailure(String eroor) {

            }
        });
    }

    /**
     * 刷新数据
     */
    @Override
    public void refreshData(){
        topicModel.loadTopicsList(null, null, topicView.getOffset(), topicView.getLimit(), new OnLoadDatasListener<List<Topic>>() {
            @Override
            public void onSuccess(List<Topic> topics) {
                topicView.setReferesh(topics);
            }

            @Override
            public void onFailure(String eroor) {
            }
        });
    }
}
