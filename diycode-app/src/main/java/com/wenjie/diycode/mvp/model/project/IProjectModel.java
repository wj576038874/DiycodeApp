package com.wenjie.diycode.mvp.model.project;

import com.gcssloop.diycode_sdk.api.project.bean.Project;
import com.wenjie.diycode.mvp.model.OnLoadDatasListener;

import java.util.List;

/**
 * @ProjectName: DiycodeApp
 * @PackageName: com.wenjie.diycode.mvp.model.project
 * @FileName: com.wenjie.diycode.mvp.model.project.IProjectModel.java
 * @Author: wenjie
 * @Date: 2017-08-04 13:49
 * @Description:
 * @Version:
 */
public interface IProjectModel {

    void getProjectsList(Integer node_id, Integer offset, Integer limit, OnLoadDatasListener<List<Project>> onLoadDatasListener);
}
