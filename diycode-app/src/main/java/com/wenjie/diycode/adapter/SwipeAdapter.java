package com.wenjie.diycode.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.gcssloop.diycode_sdk.api.topic.bean.Topic;
import com.wenjie.diycode.R;
import com.wenjie.diycode.utils.TimeUtil;
import com.wenjie.diycode.utils.ToastUtils;

import java.util.List;


/**
 * @ProjectName: StudyDemo
 * @PackageName: com.winfo.wenjie.slidingmenu
 * @FileName: com.winfo.wenjie.slidingmenu.TopicAdapter.java
 * @Author: wenjie
 * @Date: 2017-08-01 15:48
 * @Description:
 * @Version:
 */
public class SwipeAdapter extends RecyclerView.Adapter<SwipeAdapter.CustomeHolder> {

    private Context context;
    private List<Topic> datas;
    private RequestOptions options;
    private OnItemChildClickListener onItemChildClickListener;
    private OnItemClickListener onItemClickListener;

    public SwipeAdapter(Context context, List<Topic> datas) {
        this.context = context;
        this.datas = datas;
        options = new RequestOptions().placeholder(R.mipmap.ic_launcher).error(R.mipmap.failure);
    }

    @Override
    public SwipeAdapter.CustomeHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        final CustomeHolder customeHolder = new CustomeHolder(LayoutInflater.from(context).inflate(R.layout.item_swipe, parent, false));
//        customeHolder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (onItemClickListener != null) {
//                    onItemClickListener.onClick(customeHolder.itemView, customeHolder.getLayoutPosition());
//                }
//            }
//        });

        //注意事项，设置item点击，不能对整个holder.itemView设置咯，只能对第一个子View，即原来的content设置，这算是局限性吧。
        customeHolder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onClick(customeHolder.itemView, customeHolder.getLayoutPosition());
                }
            }
        });


        customeHolder.avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemChildClickListener.onItemChildClick(SwipeAdapter.this, view, customeHolder.getLayoutPosition());
            }
        });

        customeHolder.username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemChildClickListener.onItemChildClick(SwipeAdapter.this, view, customeHolder.getLayoutPosition());
            }
        });

        customeHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemChildClickListener.onItemChildClick(SwipeAdapter.this, v, customeHolder.getLayoutPosition());
            }
        });

        customeHolder.btnTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemChildClickListener.onItemChildClick(SwipeAdapter.this, v, customeHolder.getLayoutPosition());
            }
        });

        return customeHolder;
    }

    @Override
    public void onBindViewHolder(final SwipeAdapter.CustomeHolder holder, int position) {
        Topic topic = datas.get(position);
        Glide.with(context).load(topic.getUser().getAvatar_url())
                .apply(options)
                .into(holder.avatar);
        holder.title.setText(topic.getTitle());
        holder.username.setText(topic.getUser().getName());
        long updateTime = TimeUtil.string2time(topic.getUpdated_at());
        holder.time.setText(TimeUtil.getTimeElapse(updateTime));
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onClick(View itemview, int position);
    }


    public interface OnItemChildClickListener {
        void onItemChildClick(SwipeAdapter topicAdapter, View view, int position);
    }

    public void setOnItemChildClickListener(OnItemChildClickListener onItemChildClickListener) {
        this.onItemChildClickListener = onItemChildClickListener;
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class CustomeHolder extends RecyclerView.ViewHolder {

        ImageView avatar;
        TextView username;
        TextView time;
        TextView title;
        Button btnDelete;
        LinearLayout item;
        Button btnTop;

        CustomeHolder(View itemView) {
            super(itemView);
            avatar = (ImageView) itemView.findViewById(R.id.avatar);
            username = (TextView) itemView.findViewById(R.id.username);
            time = (TextView) itemView.findViewById(R.id.time);
            btnDelete = (Button) itemView.findViewById(R.id.btnDelete);
            title = (TextView) itemView.findViewById(R.id.title);
            item = (LinearLayout) itemView.findViewById(R.id.content);
            btnTop = (Button) itemView.findViewById(R.id.btnTop);
        }
    }

    /**
     * 加载更多数据
     *
     * @param topics
     */
    public void loadMore(List<Topic> topics) {
        this.datas.addAll(topics);
        notifyDataSetChanged();
    }

    /**
     * 刷新数据
     *
     * @param topics
     */
    public void referesh(List<Topic> topics) {
        this.datas = topics;
        notifyDataSetChanged();
    }
}
