package com.wenjie.diycode.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wenjie.diycode.R;
import com.wenjie.diycode.entity.CoolSites;
import com.wenjie.diycode.entity.SubCoolSites;

import java.util.List;

/**
 * @ProjectName: DiycodeApp
 * @PackageName: com.wenjie.diycode.adapter
 * @FileName: com.wenjie.diycode.adapter.NewSiteAdapter.java
 * @Author: wenjie
 * @Date: 2017-08-25 16:39
 * @Description:
 * @Version:
 */
public class NewSiteAdapter extends BaseSectionQuickAdapter<CoolSites, BaseViewHolder> {


    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param layoutResId      The layout resource id of each item.
     * @param sectionHeadResId The section head layout id for each item
     * @param data             A new list is created out of this one to avoid mutable list
     */
    public NewSiteAdapter(int layoutResId, int sectionHeadResId, List<CoolSites> data) {
        super(layoutResId, sectionHeadResId, data);
    }

    @Override
    protected void convertHead(BaseViewHolder holder, CoolSites coolSites) {
        holder.setText(R.id.name , coolSites.header);

    }

    @Override
    protected void convert(BaseViewHolder holder, CoolSites sites) {
        SubCoolSites subCoolSites = sites.t;
        Glide.with(mContext).load(subCoolSites.getAvatar_url()).into((ImageView) holder.getView(R.id.icon));
        holder.setText(R.id.name , subCoolSites.getName());
    }

    public void refresh(List<CoolSites> sites){
        setNewData(sites);
    }
}
