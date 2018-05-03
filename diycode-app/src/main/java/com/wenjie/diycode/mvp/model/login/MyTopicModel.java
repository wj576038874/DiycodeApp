package com.wenjie.diycode.mvp.model.login;

import com.gcssloop.diycode_sdk.api.topic.bean.Topic;
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
 * @PackageName: com.wenjie.diycode.mvp.model.login
 * @FileName: com.wenjie.diycode.mvp.model.login.MyTopicModel.java
 * @Author: wenjie
 * @Date: 2017-09-30 12:27
 * @Description:
 * @Version:
 */
public class MyTopicModel extends BaseModel implements IMyTopicModel{
    @Override
    public void loadUserCollectionTopicList(String loginName, Integer offset, Integer limit, final OnLoadDatasListener<List<Topic>> onLoadDatasListener) {
        Observable<List<Topic>> observable = OkHttpUtils.getRetrofit().create(ApiService.class).getUserCollectionTopicList(loginName ,offset , limit);
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
