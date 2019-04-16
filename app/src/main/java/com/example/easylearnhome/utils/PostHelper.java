package com.example.easylearnhome.utils;

import com.example.easylearnhome.entities.Collection;
import com.example.easylearnhome.entities.Post;
import com.example.easylearnhome.entities.PostInfo;
import com.example.easylearnhome.entities.User;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
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

import static com.example.easylearnhome.utils.UserHelper.getUser;

public class PostHelper {

    /**
     * 根据帖子id获取帖子信息
     *
     * @param id
     * @return
     * @throws Exception
     */
    public static Post getPost(Long id) throws Exception {
        String url = URL_helper.prefix + "/posts/" + id;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();
        String data = response.body().string();
        Gson gson = new Gson();
        Post post = gson.fromJson(data, Post.class);
        return post;
    }

    /**
     * 获取到所有的帖子
     *
     * @return
     * @throws Exception
     */
    public static List<PostInfo> getAllPosts() throws Exception {
        String url = URL_helper.prefix + "/posts";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();
        String responseData = response.body().string();
        JsonParser parser = new JsonParser();
        Gson gson = new Gson();
        JsonElement jsonElement = parser.parse(responseData);
        JsonArray jsonArray = jsonElement.getAsJsonObject().get("_embedded").getAsJsonObject().get("postList").getAsJsonArray();
        List<Post> posts = gson.fromJson(jsonArray, new TypeToken<List<Post>>() {}.getType());
        List<PostInfo> infos = new ArrayList<>();
        for (Post post : posts) {
            User user = getUser(post.getUId());
            infos.add(new PostInfo(post, user));
        }
        return infos;
    }

    /**
     * 获取某用户收藏的所有帖子信息
     * @param uid 用户id
     * @return
     * @throws Exception
     */
    public static List<PostInfo> getAllCollection(Long uid) throws Exception {
        String url = URL_helper.prefix + "/" + uid + "/collections";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();
        String data = response.body().string();
        JsonParser parser = new JsonParser();
        JsonElement jsonElement = parser.parse(data);
        if (jsonElement!=null&&jsonElement.isJsonObject()) {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            JsonElement json1 = jsonObject.get("_embedded");
            JsonElement jsonElement1 = json1.getAsJsonObject().get("collectionList");
            JsonArray jsonArray = jsonElement1.getAsJsonArray();
            Gson gson = new Gson();
            List<Collection> collections = gson.fromJson(jsonArray, new TypeToken<List<Collection>>() {
            }.getType());
            List<User> users = new ArrayList<>();
            List<Post> posts = new ArrayList<>();
            List<PostInfo> infos = new ArrayList<>();
            for (int i = 0; i < collections.size(); i++) {
                users.add(getUser(collections.get(i).getUID()));
                posts.add(getPost(collections.get(i).getPID()));
                infos.add(new PostInfo(posts.get(i), users.get(i)));
            }
            return infos;
        }
        return null;
    }

    /**
     * 获取某用户发表的所有帖子
     * @param uid 用户id
     * @return
     * @throws Exception
     */
    public static List<PostInfo> getAllPostsOfUser(Long uid) throws Exception {
        List<PostInfo> all = getAllPosts();
        List<PostInfo> infos = new ArrayList<>();
        for (PostInfo postInfo : all) {
            if (postInfo.getUid().longValue() == uid.longValue()) infos.add(postInfo);
        }
        return infos;
    }


    /**
     * 发表新帖子
     */
    public static void newPost(Long uid, String title, String content) throws IOException {
        JSONObject jsonObject = new JSONObject();
        try {
            Gson gson = new Gson();
            Post post = new Post(uid,title,content);
            gson.toJson(post,Post.class);
            jsonObject.put("uid", uid);
            jsonObject.put("ptitle", title);
            jsonObject.put("pcontent", content);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String url = URL_helper.prefix + "/posts";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).
                post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString()))
                .build();
        client.newCall(request).execute();
    }

    /**
     * 删除帖子
     * @param pid 帖子id
     * @throws IOException
     */
    public static void deletePost(Long pid) throws IOException{
        String url = URL_helper.prefix + "/posts/" +pid;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).method("delete",null).build();
        client.newCall(request).execute();
    }
}