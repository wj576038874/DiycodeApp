package com.wenjie.diycode.mvp.view.project;

import com.gcssloop.diycode_sdk.api.project.bean.Project;

import java.util.List;

/**
 * @ProjectName: DiycodeApp
 * @PackageName: com.wenjie.diycode.mvp.view.project
 * @FileName: com.wenjie.diycode.mvp.view.project.IProjectView.java
 * @Author: wenjie
 * @Date: 2017-08-04 13:48
 * @Description:
 * @Version:
 */
public interface IProjectView {
    void setProjectsList(List<Project> projects);

    void setRefreshData(List<Project> projects);

    Integer getLimit();

    Integer getOffset();

    void showFailure();

    void showLoading();

    void hideLoading();

    void setProjectsListMore(List<Project> projects);
}
