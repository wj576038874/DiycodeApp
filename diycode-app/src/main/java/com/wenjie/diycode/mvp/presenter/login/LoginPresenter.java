package com.wenjie.diycode.mvp.presenter.login;

import android.content.Context;

import com.gcssloop.diycode_sdk.api.base.bean.OAuth;
import com.gcssloop.diycode_sdk.api.user.bean.UserDetail;
import com.wenjie.diycode.mvp.model.OnLoadDatasListener;
import com.wenjie.diycode.mvp.model.login.ILoginModel;
import com.wenjie.diycode.mvp.model.login.LoginModel;
import com.wenjie.diycode.mvp.view.login.ILoginView;
import com.wenjie.diycode.utils.ToastUtils;

/**
 * @ProjectName: DiycodeApp
 * @PackageName: com.wenjie.diycode.mvp.presenter.login
 * @FileName: com.wenjie.diycode.mvp.presenter.login.LoginPresenter.java
 * @Author: wenjie
 * @Date: 2017-08-07 13:50
 * @Description:
 * @Version:
 */
public class LoginPresenter {

    private ILoginModel loginModel;
    private ILoginView loginView;

    public LoginPresenter(ILoginView loginView) {
        this.loginModel = new LoginModel();
        this.loginView = loginView;
    }


    public void login() {
        loginView.showLoading();
        loginModel.login(OAuth.client_id, OAuth.client_secret, OAuth.GRANT_TYPE_LOGIN, loginView.getUserName(), loginView.getPwd(), new OnLoadDatasListener<UserDetail>() {
            @Override
            public void onSuccess(UserDetail userDetail) {
                loginView.hideLoading();
                loginView.loginSuccess(userDetail);
            }

            @Override
            public void onFailure(String eroor) {
                loginView.hideLoading();
                ToastUtils.showToast((Context) loginView, eroor);
            }
        });
    }


    public void getMe() {

        loginModel.getMe(new OnLoadDatasListener<UserDetail>() {
            @Override
            public void onSuccess(UserDetail userDetail) {
                loginView.loginSuccess(userDetail);
            }

            @Override
            public void onFailure(String eroor) {

            }
        });
    }
}
