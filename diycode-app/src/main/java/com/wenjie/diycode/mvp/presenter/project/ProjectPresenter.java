package com.wenjie.diycode.mvp.presenter.project;

import com.gcssloop.diycode_sdk.api.project.bean.Project;
import com.wenjie.diycode.mvp.model.OnLoadDatasListener;
import com.wenjie.diycode.mvp.model.project.IProjectModel;
import com.wenjie.diycode.mvp.model.project.ProjectModel;
import com.wenjie.diycode.mvp.view.project.IProjectView;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @ProjectName: DiycodeApp
 * @PackageName: com.wenjie.diycode.mvp.presenter.project
 * @FileName: com.wenjie.diycode.mvp.presenter.project.ProjectPresenter.java
 * @Author: wenjie
 * @Date: 2017-08-04 13:54
 * @Description:
 * @Version:
 */
public class ProjectPresenter {
    private IProjectModel projectModel;
    private IProjectView projectView;

    public ProjectPresenter(IProjectView projectView) {
        this.projectModel = new ProjectModel();
        this.projectView = projectView;
    }

    public void getProjectsList() {
        projectView.showLoading();
        projectModel.getProjectsList(null, projectView.getOffset(), projectView.getLimit(), new OnLoadDatasListener<List<Project>>() {
            @Override
            public void onSuccess(List<Project> projects) {
                projectView.hideLoading();
                projectView.setProjectsList(projects);
            }

            @Override
            public void onFailure(String eroor) {
                projectView.hideLoading();
                projectView.showFailure();
            }
        });
    }

    public void getProjectsListMore() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
            }
        }, 3000);
        projectModel.getProjectsList(null, projectView.getOffset(), projectView.getLimit(), new OnLoadDatasListener<List<Project>>() {
            @Override
            public void onSuccess(List<Project> projects) {
                projectView.setProjectsListMore(projects);
            }

            @Override
            public void onFailure(String eroor) {
                projectView.showFailure();
            }
        });
    }

    public void refreshData() {
        projectModel.getProjectsList(null, projectView.getOffset(), projectView.getLimit(), new OnLoadDatasListener<List<Project>>() {
            @Override
            public void onSuccess(List<Project> projects) {
                projectView.setRefreshData(projects);
            }

            @Override
            public void onFailure(String eroor) {
                projectView.showFailure();
            }
        });
    }
}