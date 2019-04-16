package com.example.easylearnhome.utils;

import com.example.easylearnhome.entities.Collection;
import com.example.easylearnhome.entities.LikeComment;
import com.example.easylearnhome.entities.Post;
import com.example.easylearnhome.entities.PostInfo;
import com.google.gson.Gson;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class CollectionHelper {

    //查询用户是否收藏
    public static boolean hasCollection(Long uid,Long pid) throws Exception{
        List<PostInfo> postInfos = PostHelper.getAllCollection(uid);
        for(PostInfo postInfo:postInfos){
            if (pid==postInfo.getPid())
                return true;
        }
        return false;
    }

    /**
     * 添加收藏
     * @param uid 用户id
     * @param pid 帖子id
     * @throws Exception
     */
    public static void newCollection(Long uid, Long pid) throws Exception{
        Collection collection = new Collection(uid,pid);
        OkHttpClient client = new OkHttpClient();
        Gson gson = new Gson();
        String url = URL_helper.prefix + "/collections";
        Request request = new Request.Builder().url(url)
                .post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"),gson.toJson(collection)))
                .build();
        client.newCall(request).execute();
    }

    /**
     * 删除用户收藏
     * @param uid 用户id
     * @param pid 帖子id
     * @throws Exception
     */
    public static void deleteCollection(Long uid, Long pid) throws Exception{
        Collection collection = new Collection(uid,pid);
        OkHttpClient client = new OkHttpClient();
        Gson gson = new Gson();
        String url = URL_helper.prefix + "/collections";
        Request request = new Request.Builder().url(url)
                .delete(RequestBody.create(MediaType.parse("application/json; charset=utf-8"),gson.toJson(collection)))
                .build();
        client.newCall(request).execute();
    }
}
