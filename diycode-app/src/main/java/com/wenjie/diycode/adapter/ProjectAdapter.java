package com.wenjie.diycode.adapter;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gcssloop.diycode_sdk.api.project.bean.Project;
import com.sackcentury.shinebuttonlib.ShineButton;
import com.wenjie.diycode.R;
import com.wenjie.diycode.utils.TimeUtil;
import com.wenjie.diycode.widget.GlideRoundTransform;

import java.util.List;

/**
 * @ProjectName: DiycodeApp
 * @PackageName: com.wenjie.diycode.adapter
 * @FileName: com.wenjie.diycode.adapter.ProjectAdapter.java
 * @Author: wenjie
 * @Date: 2017-08-04 13:45
 * @Description:
 * @Version:
 */
public class ProjectAdapter extends BaseQuickAdapter<Project , ProjectAdapter.CustomeHolder> {

    private List<Project> projects;
//    private Context context;
    private RequestOptions options;

    public ProjectAdapter(List<Project> projects){
        super(R.layout.item_project , projects);
        this.projects = projects;
        options = new RequestOptions();
        options.placeholder(R.mipmap.ic_launcher).error(R.mipmap.failure).transform(new GlideRoundTransform());
    }

//    public ProjectAdapter(Context context, List<Project> projects) {
//        this.projects = projects;
//        this.context = context;
//        options = new RequestOptions();
//        options.placeholder(R.mipmap.ic_launcher).error(R.mipmap.failure).transform(new GlideRoundTransform());
//    }

//    @Override
//    public CustomeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        final CustomeHolder customeHolder = new CustomeHolder(LayoutInflater.from(mContext).inflate(R.layout.item_project, parent, false));
//        customeHolder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(onItemClickListener != null){
//                    onItemClickListener.onClick(customeHolder.itemView , customeHolder.getLayoutPosition());
//                }
//            }
//        });
//        return customeHolder;
//    }

    @Override
    protected void convert(ProjectAdapter.CustomeHolder holder, Project project) {
        Glide.with(mContext).load(project.getProject_cover_url())
                .apply(options)
                .into(holder.avatar);
        holder.username.setText(project.getName());
        holder.time.setText(TimeUtil.getTimeElapse(TimeUtil.string2time(project.getLast_updated_at())));
        holder.title.setText(project.getDescription());
        holder.category.setText(project.getCategory().getName());
        holder.sub_category.setText(project.getSub_category().getName());
        holder.star_num.setText(project.getStar() + "");
        holder.addOnClickListener(R.id.username);
    }


//    @Override
//    public void onBindViewHolder(final CustomeHolder holder,int position) {
//        Project project = projects.get(position);
//        Glide.with(context).load(project.getProject_cover_url())
//                .apply(options)
//                .into(holder.avatar);
//        holder.username.setText(project.getName());
//        holder.time.setText(TimeUtil.getTimeElapse(TimeUtil.string2time(project.getLast_updated_at())));
//        holder.title.setText(project.getDescription());
//        holder.category.setText(project.getCategory().getName());
//        holder.sub_category.setText(project.getSub_category().getName());
//        holder.star_num.setText(project.getStar() + "");
//    }

//    private OnItemClickListener onItemClickListener;
//
//    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
//        this.onItemClickListener = onItemClickListener;
//    }
//
//    public interface OnItemClickListener{
//        void onClick(View itemview , int position);
//    }

    @Override
    public int getItemCount() {
        return projects.size();
    }


    class CustomeHolder extends BaseViewHolder {

        ImageView avatar;
        TextView username;
        TextView time;
        TextView title;
        TextView category;
        TextView sub_category;
        TextView star_num;
        ShineButton star;

        CustomeHolder(View itemView) {
            super(itemView);
            avatar = (ImageView) itemView.findViewById(R.id.avatar);
            username = (TextView) itemView.findViewById(R.id.username);
            time = (TextView) itemView.findViewById(R.id.time);
            title = (TextView) itemView.findViewById(R.id.title);
            category = (TextView) itemView.findViewById(R.id.catergory);
            sub_category = (TextView) itemView.findViewById(R.id.sub_category);
            star_num = (TextView) itemView.findViewById(R.id.star_num);
            star = (ShineButton) itemView.findViewById(R.id.star);
            star.init((Activity) mContext);
        }
    }

    /**
     * 加载更多数据
     * @param projects
     */
    public void loadMore(List<Project> projects) {
        addData(projects);
    }


    /**
     * 刷新数据
     *
     * @param projects
     */
    public void refreshDatas(List<Project> projects) {
        setNewData(projects);
    }
}
