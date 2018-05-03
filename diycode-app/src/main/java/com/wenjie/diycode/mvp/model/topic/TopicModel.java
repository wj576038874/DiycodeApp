package com.wenjie.diycode.mvp.model.topic;

import com.gcssloop.diycode_sdk.api.topic.bean.Topic;
import com.gcssloop.diycode_sdk.api.topic.bean.TopicContent;
import com.wenjie.diycode.http.ApiService;
import com.wenjie.diycode.http.OkHttpUtils;
import com.wenjie.diycode.mvp.model.BaseModel;
import com.wenjie.diycode.mvp.model.OnLoadDatasListener;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * @ProjectName: DiycodeApp
 * @PackageName: com.wenjie.diycode.mvp.model.topic
 * @FileName: com.wenjie.diycode.mvp.model.topic.TopicModel.java
 * @Author: wenjie
 * @Date: 2017-08-04 12:05
 * @Description:
 * @Version:
 */
public class TopicModel extends BaseModel implements ITopicModel {

    @Override
    public void loadTopicsList(String type, Integer node_id, Integer offset, Integer limit, final OnLoadDatasListener<List<Topic>> onLoadDatasListener) {
        Observable<List<Topic>> observable = OkHttpUtils.getRetrofit().create(ApiService.class).loadTopicList(type, node_id, offset, limit);
        Observer<List<Topic>> observer = new Observer<List<Topic>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull List<Topic> topics) {
                onLoadDatasListener.onSuccess(topics);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                onLoadDatasListener.onFailure(e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        };
        subscribe(observable, observer);
    }


    @Override
    public void loadTopicContent(int id, final OnLoadDatasListener<TopicContent> onLoadDatasListener) {
        Observable<TopicContent> observable = OkHttpUtils.getRetrofit().create(ApiService.class).loadTopicContent(id);
        Observer<TopicContent> observer = new Observer<TopicContent>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull TopicContent topicContent) {
                onLoadDatasListener.onSuccess(topicContent);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                onLoadDatasListener.onFailure(e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        };

        subscribe(observable, observer);
    }

    @Override
    public void loadUserTopicList(String login_name, String order, Integer offset, Integer limit,final OnLoadDatasListener<List<Topic>> onLoadDatasListener) {
        Observable<List<Topic>> observable = OkHttpUtils.getRetrofit().create(ApiService.class).getUserCreateTopicList(login_name , order , offset , limit);
        Observer<List<Topic>> observer = new Observer<List<Topic>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull List<Topic> topics) {
                onLoadDatasListener.onSuccess(topics);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                onLoadDatasListener.onFailure(e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        };
        subscribe(observable , observer);
    }
}
