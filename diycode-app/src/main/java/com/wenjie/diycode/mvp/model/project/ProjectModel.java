package com.wenjie.diycode.mvp.model.project;

import com.gcssloop.diycode_sdk.api.project.bean.Project;
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
 * @PackageName: com.wenjie.diycode.mvp.model.project
 * @FileName: com.wenjie.diycode.mvp.model.project.ProjectModel.java
 * @Author: wenjie
 * @Date: 2017-08-04 13:49
 * @Description:
 * @Version:
 */
public class ProjectModel extends BaseModel implements IProjectModel {


    @Override
    public void getProjectsList(Integer node_id, Integer offset, Integer limit, final OnLoadDatasListener<List<Project>> onLoadDatasListener) {
        Observable<List<Project>> observable = OkHttpUtils.getRetrofit().create(ApiService.class).getProjectsList(node_id, offset, limit);
        Observer<List<Project>> observer = new Observer<List<Project>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull List<Project> projects) {
                onLoadDatasListener.onSuccess(projects);
//                RxBus.getIntanceBus().post(new BaseEvent<>(projects));
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
}
