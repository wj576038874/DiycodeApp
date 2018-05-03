package com.wenjie.diycode.mvp.presenter.login;

import com.gcssloop.diycode_sdk.api.topic.bean.Topic;
import com.wenjie.diycode.mvp.model.OnLoadDatasListener;
import com.wenjie.diycode.mvp.model.login.IMyTopicModel;
import com.wenjie.diycode.mvp.model.login.MyTopicModel;
import com.wenjie.diycode.mvp.view.login.IMyTopicView;

import java.util.List;

/**
 * @ProjectName: DiycodeApp
 * @PackageName: com.wenjie.diycode.mvp.presenter.login
 * @FileName: com.wenjie.diycode.mvp.presenter.login.MyTopicPresenter.java
 * @Author: wenjie
 * @Date: 2017-09-30 12:27
 * @Description:
 * @Version:
 */
public class MyTopicPresenter {

    private IMyTopicModel myTopicModel;
    private IMyTopicView myTopicView;
    public MyTopicPresenter(IMyTopicView myTopicView){
        myTopicModel = new MyTopicModel();
        this.myTopicView = myTopicView;
    }

    public void getUserCollectionTopicList(String loginName){
        myTopicView.showLoading();
        myTopicModel.loadUserCollectionTopicList(loginName, myTopicView.getOffset(), myTopicView.getLimit(), new OnLoadDatasListener<List<Topic>>() {
            @Override
            public void onSuccess(List<Topic> topics) {
                myTopicView.hideLoading();
                myTopicView.setDatas(topics);
            }

            @Override
            public void onFailure(String eroor) {
                myTopicView.hideLoading();
            }
        });
    }

    public void refresh(String loginName){
        myTopicModel.loadUserCollectionTopicList(loginName, myTopicView.getOffset(), myTopicView.getLimit(), new OnLoadDatasListener<List<Topic>>() {
            @Override
            public void onSuccess(List<Topic> topics) {
                myTopicView.refresh(topics);
            }

            @Override
            public void onFailure(String eroor) {
                myTopicView.hideLoading();
            }
        });
    }

    public void loadmore(String loginName){
        myTopicModel.loadUserCollectionTopicList(loginName, myTopicView.getOffset(), myTopicView.getLimit(), new OnLoadDatasListener<List<Topic>>() {
            @Override
            public void onSuccess(List<Topic> topics) {
                myTopicView.refresh(topics);
            }

            @Override
            public void onFailure(String eroor) {
                myTopicView.hideLoading();
            }
        });
    }

}
