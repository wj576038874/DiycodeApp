package com.wenjie.diycode.mvp.model.sites;

import com.gcssloop.diycode_sdk.api.sites.bean.Sites;
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
 * @PackageName: com.wenjie.diycode.mvp.model.sites
 * @FileName: com.wenjie.diycode.mvp.model.sites.SitesModel.java
 * @Author: wenjie
 * @Date: 2017-08-17 14:42
 * @Description:
 * @Version:
 */
public class SitesModel extends BaseModel implements ISitesModel {


    @Override
    public void loadSites(final OnLoadDatasListener<List<Sites>> onLoadDatasListener) {
        Observable<List<Sites>> observable = OkHttpUtils.getRetrofit().create(ApiService.class).loadSites();
        Observer<List<Sites>> observer = new Observer<List<Sites>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull List<Sites> sites) {
                onLoadDatasListener.onSuccess(sites);
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
