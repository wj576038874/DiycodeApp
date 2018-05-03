package com.wenjie.diycode.entity;

import com.chad.library.adapter.base.entity.SectionEntity;

/**
 * @ProjectName: DiycodeApp
 * @PackageName: com.wenjie.diycode.mvp.view.sites
 * @FileName: com.wenjie.diycode.entity.CoolSites.java
 * @Author: wenjie
 * @Date: 2017-08-25 16:40
 * @Description:
 * @Version:
 */
public class CoolSites extends SectionEntity<SubCoolSites> {

    public CoolSites(SubCoolSites t){
        super(t);
    }

    public CoolSites(boolean isHeader, String header) {
        super(isHeader, header);
    }

}
