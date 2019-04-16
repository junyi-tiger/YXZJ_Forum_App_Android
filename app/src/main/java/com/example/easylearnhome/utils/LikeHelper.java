package com.example.easylearnhome.utils;

import com.example.easylearnhome.entities.LikeComment;
import com.example.easylearnhome.entities.LikePost;
import com.google.gson.Gson;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class LikeHelper {
    /**
     * 新增给帖子的点赞
     * @param uid
     * @param pid
     * @throws Exception
     */
    public static void newLikePost(Long uid, Long pid) throws Exception{
        LikePost likePost = new LikePost(uid,pid);
        Gson gson = new Gson();
        OkHttpClient client = new OkHttpClient();
        String url = URL_helper.prefix + "/likeposts";
        Request request = new Request.Builder().url(url)
                .post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"),gson.toJson(likePost)))
                .build();
        client.newCall(request).execute();
    }

    /**
     * 取消给帖子点赞
     * @param pid 帖子id
     * @param uid 用户id
     * @throws Exception
     */
    public static void deleteLikePost(Long uid, Long pid) throws Exception{
        LikePost likePost = new LikePost(uid,pid);
        Gson gson = new Gson();
        OkHttpClient client = new OkHttpClient();
        String url = URL_helper.prefix + "/likeposts";
        Request request = new Request.Builder().url(url)
                .delete(RequestBody.create(MediaType.parse("application/json; charset=utf-8"),gson.toJson(likePost)))
                .build();
        client.newCall(request);
    }

    /**
     * 新增给评论的点赞
     * @param uid 用户id
     * @param cid 评论id
     * @throws Exception
     */
    public static void newLikeComment(Long uid, Long cid) throws Exception{
        LikeComment likeComment = new LikeComment(uid,cid);
        Gson gson = new Gson();
        OkHttpClient client = new OkHttpClient();
        String url = URL_helper.prefix + "/likecomments";
        Request request = new Request.Builder().url(url)
                .post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"),gson.toJson(likeComment)))
                .build();
        client.newCall(request).execute();
    }

    /**
     * 取消给评论的点赞
     * @param uid 用户id
     * @param cid 评论id
     * @throws Exception
     */
    public static void deleteLikeComment(Long uid, Long cid) throws Exception{
        LikeComment likeComment = new LikeComment(uid,cid);
        Gson gson = new Gson();
        OkHttpClient client = new OkHttpClient();
        String url = URL_helper.prefix + "/likecomments";
        Request request = new Request.Builder().url(url)
                .delete(RequestBody.create(MediaType.parse("application/json; charset=utf-8"),gson.toJson(likeComment)))
                .build();
        client.newCall(request);
    }
}
