package com.wenjie.diycode.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gcssloop.diycode_sdk.api.sites.bean.Sites;
import com.wenjie.diycode.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: DiycodeApp
 * @PackageName: com.wenjie.diycode.adapter
 * @FileName: com.wenjie.diycode.adapter.SitesAdapter.java
 * @Author: wenjie
 * @Date: 2017-08-17 14:57
 * @Description:
 * @Version:
 */
public class SitesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    public static final int SITES = 0;//标题 跨一列 也就是合并两列
    public static final int SITE = 1;//不跨列
    //所有数据的集合，将标题和数据项，全部装在到这个集合中，在适配器中利用viewtype来区分，并显示不同的布局
    private List<Object> items = new ArrayList<>();
    private Context context;

    public SitesAdapter(Context context, List<Object> items) {
        this.items = items;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(context);//获取mInflater对象
        switch (viewType) {//根据viewtyupe来区分，是标题还是数据项
            case SITES://标题，加载显示标题的item布局，就一个textview显示文本，这里我们自顶一个标题的viewholder->SitesHolder
                final SitesHolder sitesHolder = new SitesHolder(mInflater.inflate(R.layout.item_sites, parent, false));
                //点击事件
                sitesHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(onItemClickListener != null){
                            onItemClickListener.onClick(sitesHolder.itemView , sitesHolder.getLayoutPosition());
                        }
                    }
                });
                return sitesHolder;
            case SITE://数据项，雷同不赘述了，标题和数据项的item布局和veiwholder都不会相互影响的
                final SiteHolder siteHolder = new SiteHolder(mInflater.inflate(R.layout.item_site, parent, false));
                siteHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(onItemClickListener != null){
                            onItemClickListener.onClick(siteHolder.itemView , siteHolder.getLayoutPosition());
                        }
                    }
                });
                return siteHolder;
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        //这个方法很重要，这里根据position取出items集合中的对象，用instanceof判断他是标题还是数据项，来返回对应的标识
        if (items.get(position) instanceof Sites) {//根据items数据类型的不同来判断他是标题还是数据项
            return SITES;//标题
        } else if (items.get(position) instanceof Sites.Site) {
            return SITE;//数据项
        } else {
            return -1;
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        //根据getItemViewType绑定view进行赋值显示
        List s = new ArrayList();
        switch (holder.getItemViewType()) {
            case SITES://标题
                SitesHolder sitesHolder = (SitesHolder) holder;
                sitesHolder.name.setText(((Sites) items.get(position)).getName());
                break;
            case SITE://数据项
                SiteHolder siteHolder = (SiteHolder) holder;
                siteHolder.name.setText(((Sites.Site) items.get(position)).getName());
                Glide.with(context).load(((Sites.Site) items.get(position)).getAvatar_url()).into(siteHolder.icon);
                break;
        }
    }

    /**
     * 公布点击事件出去
     */
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void onClick(View itemview , int position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    /**
     * 数据项的viewholder  一个文本textview一个cion  imageview
     */
    private class SiteHolder extends RecyclerView.ViewHolder {

        TextView name;
        ImageView icon;

        SiteHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            icon = (ImageView) itemView.findViewById(R.id.icon);
        }
    }

    /**
     * 标题的viewholder  只有一个textview
     */
    private class SitesHolder extends RecyclerView.ViewHolder {

        TextView name;

        SitesHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
        }
    }

    /**
     * 刷新数据
     *
     * @param items
     */
    public void refreshDatas(List<Object> items) {
        this.items = items;
        notifyDataSetChanged();
    }
}
