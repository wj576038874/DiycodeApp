package com.wenjie.diycode.mvp.view.login;

import com.gcssloop.diycode_sdk.api.user.bean.UserDetail;

/**
 * @ProjectName: DiycodeApp
 * @PackageName: com.wenjie.diycode.mvp.view.login
 * @FileName: com.wenjie.diycode.mvp.view.login.ILoginView.java
 * @Author: wenjie
 * @Date: 2017-08-07 13:46
 * @Description:
 * @Version:
 */
public interface ILoginView {
    String getUserName();

    String getPwd();

    void loginSuccess(UserDetail userDetail);

    void showLoading();

    void hideLoading();
}
