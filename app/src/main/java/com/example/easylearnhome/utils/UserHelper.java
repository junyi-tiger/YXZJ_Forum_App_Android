package com.example.easylearnhome.utils;

import android.util.Log;

import com.example.easylearnhome.entities.User;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.Random;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UserHelper {

    /**
     * 根据id获取用户信息
     * @param uid
     * @return
     * @throws Exception
     */
    public static User getUser(Long uid) throws Exception{
        String url = URL_helper.prefix+"/users/" + uid;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();
        String data = response.body().string();
        Gson gson = new Gson();
        User user = gson.fromJson(data,User.class);
        return user;
    }

    /**
     * 用户登录
     * @param username_or_email
     * @param password
     * @return
     * @throws Exception
     */
    public static User UserLogin(String username_or_email, String password) throws Exception {
        String url = URL_helper.prefix+"/users/login";
        //1.用户名+密码登录
        OkHttpClient client = new OkHttpClient();
        Request.Builder requestBuilder = new Request.Builder();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name",username_or_email);
        jsonObject.put("password",password);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),jsonObject.toString());
        Request request = requestBuilder.url(url).post(requestBody).build();
        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()){
            //不成功换种方式：2.邮箱+密码登录
            jsonObject.remove("name");
            jsonObject.put("email",username_or_email);
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),jsonObject.toString());
            request = requestBuilder.url(url).post(requestBody).build();
            response = client.newCall(request).execute();
        }
        if (response.isSuccessful()){
            //成功
            String data = response.body().string();
            Gson gson = new Gson();
            User user = gson.fromJson(data,User.class);
            Log.d("UserHelper","查询到user信息如下："+user.toString()+" uname:"+user.getUName() + " upassword:" + user.getUPassword());
            return user;
        }
        //登录不成功，返回null
        return null;
    }

    /**
     * 用户注册
     * @param uname
     * @param uemail
     * @param upassword
     * @param usex
     * @return
     * @throws Exception
     */
    public static User UserRegister(String uname,String uemail,String upassword,int usex) throws Exception{
        String url = URL_helper.prefix+"/users/register";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("uname",uname);
        jsonObject.put("uemail",uemail);
        jsonObject.put("upassword",upassword);
        jsonObject.put("usex",usex);
        Random random = new Random();
        jsonObject.put("uhead", random.nextInt(12)+1);
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),jsonObject.toString());
        Request request = new Request.Builder().url(url).post(requestBody).build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()){
            //成功
            String data = response.body().string();
            Gson gson = new Gson();
            User user = gson.fromJson(data,User.class);
            return user;
        }
        return null;
    }
}
