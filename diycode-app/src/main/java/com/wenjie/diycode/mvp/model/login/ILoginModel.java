package com.wenjie.diycode.mvp.model.login;

import com.gcssloop.diycode_sdk.api.user.bean.UserDetail;
import com.wenjie.diycode.mvp.model.OnLoadDatasListener;

/**
 * @ProjectName: DiycodeApp
 * @PackageName: com.wenjie.diycode.mvp.model.login
 * @FileName: com.wenjie.diycode.mvp.model.login.ILoginModel.java
 * @Author: wenjie
 * @Date: 2017-08-07 13:50
 * @Description:
 * @Version:
 */
public interface ILoginModel {
    void login(String client_id, String client_secret, String grant_type, String username, String password, OnLoadDatasListener<UserDetail> onLoadDatasListener);

    void getMe(OnLoadDatasListener<UserDetail> onLoadDatasListener);

}
