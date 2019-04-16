package com.example.easylearnhome.utils;

import com.example.easylearnhome.entities.Post;
import com.example.easylearnhome.entities.ReplyInfo;
import com.example.easylearnhome.entities.Reply_to_comment;
import com.example.easylearnhome.entities.User;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ReplyHelper {
    /**
     * 获取某评论的所有回复
     * @param cid 评论id
     * @return
     * @throws IOException
     */
    public static List<ReplyInfo> getAllRepliesOfComment(Long cid) throws IOException{
        String url = URL_helper.prefix + "/comments/" + cid + "/replies_to_comment";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();
        try{
            if (response.isSuccessful()){
                JsonParser parser = new JsonParser();
                JsonElement element = parser.parse(response.body().string());
                JsonArray jsonArray = element.getAsJsonObject().get("_embedded").getAsJsonObject().get("reply_to_commentList").getAsJsonArray();
                Gson gson = new Gson();
                List<Reply_to_comment> replies = gson.fromJson(jsonArray,new TypeToken<List<Reply_to_comment>>() {}.getType());
                List<ReplyInfo> infos = new ArrayList<>();
                for (Reply_to_comment reply_to_comment:replies){
                    Long uid = reply_to_comment.getUID();
                    User user = UserHelper.getUser(uid);
                    ReplyInfo info = new ReplyInfo(reply_to_comment,user);
                    infos.add(info);
                }
                return infos;
            }
        } catch (Exception e){
            return null;
        }
        return null;
    }

    /**
     * 发表新回复
     * @param cid 评论id
     * @param uid 用户id
     * @param content 回复内容
     * @throws Exception
     */
    public static void newReply_to_Comment(Long cid, Long uid, String content) throws Exception{
        Reply_to_comment reply_to_comment = new Reply_to_comment(cid,uid,content);
        Gson gson = new Gson();
        String s = gson.toJson(reply_to_comment);
        OkHttpClient client = new OkHttpClient();
        String url = URL_helper.prefix + "/reply_to_comments";
        Request request = new Request.Builder().url(url)
                .post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), s))
                .build();
        client.newCall(request).execute();
    }

    /**
     * 删除回复
     * @param rid 回复id
     * @throws Exception
     */
    public static void deleteReply_to_Comment(Long rid) throws Exception{
        OkHttpClient client = new OkHttpClient();
        String url = URL_helper.prefix + "/reply_to_comments/" + rid;
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).execute();
    }
}