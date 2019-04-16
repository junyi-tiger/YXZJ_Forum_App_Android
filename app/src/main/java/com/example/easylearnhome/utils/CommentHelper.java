package com.example.easylearnhome.utils;

import com.example.easylearnhome.entities.Comment;
import com.example.easylearnhome.entities.CommentInfo;
import com.example.easylearnhome.entities.User;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CommentHelper {

    /**
     * 获取评论信息
     * @param cid 评论id
     * @return
     * @throws Exception
     */
    public static Comment getComment(Long cid) throws Exception{
        String url = URL_helper.prefix + "/comments/" + cid;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();
        String data = response.body().string();
        Gson gson = new Gson();
        Comment comment = gson.fromJson(data,Comment.class);
        return comment;
    }

    /**
     * 获取某帖子的所有评论信息
     * @param pid
     * @return
     * @throws Exception
     */
    public static List<CommentInfo> getAllCommentOfPost(Long pid) throws Exception{
        String url = URL_helper.prefix + "/posts/"+pid+"/comments";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();
        String data = response.body().string();
        JsonParser parser = new JsonParser();
        JsonElement jsonElement = parser.parse(data);
        if (jsonElement!=null&&jsonElement.isJsonObject()){
            JsonArray jsonArray = jsonElement.getAsJsonObject().get("_embedded").getAsJsonObject().get("commentList").getAsJsonArray();
            Gson gson = new Gson();
            List<Comment> comments = gson.fromJson(jsonArray, new TypeToken<List<Comment>>(){}.getType());
            List<User> users = new ArrayList<>();
            List<CommentInfo> infos = new ArrayList<>();
            for (Comment comment:comments){
                Long uid = comment.getUID();
                User user = UserHelper.getUser(uid);
                infos.add(new CommentInfo(comment,user));
            }
            return infos;
        }
        return null;
    }

    /**
     * 发表新评论
     * @param uid 用户id
     * @param pid 帖子id
     * @param content 评论内容
     * @throws IOException
     */
    public static void newComment(Long uid, Long pid, String content) throws IOException {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("uid", uid);
            jsonObject.put("pid", pid);
            jsonObject.put("ccontent", content);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String url = URL_helper.prefix + "/comments";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).
                post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString()))
                .build();
        client.newCall(request).execute();
    }

    /**
     * 删除评论
     * @param cid 回复id
     * @throws IOException
     */
    public static void deleteComment(Long cid) throws IOException{
        String url = URL_helper.prefix + "/comments";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).execute();
    }

}