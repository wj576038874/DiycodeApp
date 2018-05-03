package com.wenjie.diycode.http;

import com.gcssloop.diycode_sdk.api.login.bean.Token;
import com.gcssloop.diycode_sdk.api.news.bean.New;
import com.gcssloop.diycode_sdk.api.project.bean.Project;
import com.gcssloop.diycode_sdk.api.sites.bean.Sites;
import com.gcssloop.diycode_sdk.api.topic.bean.Topic;
import com.gcssloop.diycode_sdk.api.topic.bean.TopicContent;
import com.gcssloop.diycode_sdk.api.user.bean.UserDetail;
import com.gcssloop.diycode_sdk.utils.Constant;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @ProjectName: StudyDemo
 * @PackageName: com.winfo.wenjie.rxjavatwo.http
 * @FileName: com.winfo.wenjie.rxjavatwo.http.ApiService.java
 * @Author: wenjie
 * @Date: 2017-07-28 16:24
 * @Description:
 * @Version:
 */
public interface ApiService {


    /**
     * 获取 topic 列表
     *
     * @param type    类型，默认 last_actived，可选["last_actived", "recent", "no_reply", "popular", "excellent"]
     * @param node_id 如果你需要只看某个节点的，请传此参数, 如果不传 则返回全部
     * @param offset  偏移数值，默认值 0
     * @param limit   数量极限，默认值 20，值范围 1..150
     * @return topic 列表
     */
    @GET("topics.json")
    Observable<List<Topic>> loadTopicList(@Query("type") String type, @Query("node_id") Integer node_id, @Query("offset") Integer offset, @Query("limit") Integer limit);


    /**
     * 获取 project 列表
     *
     * @param node_id 如果你需要只看某个节点的，请传此参数, 如果不传 则返回全部
     * @param offset  偏移数值，默认值 0
     * @param limit   数量极限，默认值 20，值范围 1..150
     * @return project 列表
     */
    @GET("projects.json")
    Observable<List<Project>> getProjectsList(@Query("node_id") Integer node_id, @Query("offset") Integer offset, @Query("limit") Integer limit);


    @GET("news.json")
    Observable<List<New>> loadNewsList(@Query("limit") Integer limit);


    /**
     * 获取 topic 内容
     *
     * @param id topic 的 id
     * @return 内容详情
     */
    @GET("topics/{id}.json")
    Observable<TopicContent> loadTopicContent(@Path("id") Integer id);


    @GET("sites.json")
    Observable<List<Sites>> loadSites();


    /**
     * 获取 Token (一般在登录时调用)
     *
     * @param client_id     客户端 id
     * @param client_secret 客户端私钥
     * @param grant_type    授权方式 - 密码
     * @param username      用户名
     * @param password      密码
     * @return Token 实体类
     */
    @POST(Constant.OAUTH_URL)
    @FormUrlEncoded
    Observable<Token> getToken(
            @Field("client_id") String client_id, @Field("client_secret") String client_secret,
            @Field("grant_type") String grant_type, @Field("username") String username,
            @Field("password") String password);

    /**
     * 获取当前登录者的详细资料
     *
     * @return 用户详情
     */
    @GET("users/me.json")
    Observable<UserDetail> getMe();

    /**
     * 获取用户创建的话题列表
     *
     * @param login_name 登录用户名(非昵称)
     * @param order      类型 默认 recent，可选["recent", "likes", "replies"]
     * @param offset     偏移数值，默认值 0
     * @param limit      数量极限，默认值 20，值范围 1..150
     * @return 话题列表
     */
    @GET("users/{login}/topics.json")
    Observable<List<Topic>> getUserCreateTopicList(@Path("login") String login_name, @Query("order") String order,
                                                   @Query("offset") Integer offset, @Query("limit") Integer limit);


    /**
     * 用户收藏的话题列表
     *
     * @param login_name 登录用户名(非昵称)
     * @param offset     偏移数值，默认值 0
     * @param limit      数量极限，默认值 20，值范围 1..150
     * @return 话题列表
     */
    @GET("users/{login}/favorites.json")
    Observable<List<Topic>> getUserCollectionTopicList(@Path("login") String login_name,
                                                       @Query("offset") Integer offset, @Query("limit") Integer limit);
}
