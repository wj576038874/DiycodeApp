package com.wenjie.diycode.mvp.model.login;

import com.gcssloop.diycode_sdk.api.login.bean.Token;
import com.gcssloop.diycode_sdk.api.user.bean.UserDetail;
import com.gcssloop.diycode_sdk.utils.CacheUtil;
import com.wenjie.diycode.app.DiycodeApplication;
import com.wenjie.diycode.http.ApiService;
import com.wenjie.diycode.http.OkHttpUtils;
import com.wenjie.diycode.mvp.model.BaseModel;
import com.wenjie.diycode.mvp.model.OnLoadDatasListener;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

/**
 * @ProjectName: DiycodeApp
 * @PackageName: com.wenjie.diycode.mvp.model.login
 * @FileName: com.wenjie.diycode.mvp.model.login.LoginModel.java
 * @Author: wenjie
 * @Date: 2017-08-07 13:50
 * @Description:
 * @Version:
 */
public class LoginModel extends BaseModel implements ILoginModel {


    @Override
    public void login(String client_id, String client_secret, String grant_type, String username, String password, final OnLoadDatasListener<UserDetail> onLoadDatasListener) {
        Observable<UserDetail> observable = OkHttpUtils.getRetrofit().create(ApiService.class).getToken(client_id, client_secret, grant_type, username, password)
                .flatMap(new Function<Token, ObservableSource<UserDetail>>() {
                    @Override
                    public ObservableSource<UserDetail> apply(@NonNull Token token) throws Exception {
                        CacheUtil cacheUtil = new CacheUtil(DiycodeApplication.getContext());
                        cacheUtil.saveToken(token);
                        return OkHttpUtils.getRetrofit().create(ApiService.class).getMe();
                    }
                });
        Observer<UserDetail> observer = new Observer<UserDetail>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull UserDetail userDetail) {
                onLoadDatasListener.onSuccess(userDetail);
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
    public void getMe(final OnLoadDatasListener<UserDetail> onLoadDatasListener) {
        Observable<UserDetail> observable = OkHttpUtils.getRetrofit().create(ApiService.class).getMe();
        Observer<UserDetail> observer = new Observer<UserDetail>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull UserDetail userDetail) {
                onLoadDatasListener.onSuccess(userDetail);
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
