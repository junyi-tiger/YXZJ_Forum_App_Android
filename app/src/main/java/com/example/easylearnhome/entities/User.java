package com.example.easylearnhome.entities;

import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;

public class User {
    @SerializedName("uid")
    private Long UId;//用户唯一id
    @SerializedName("uname")
    private String UName;//用户名
    @SerializedName("upassword")
    private String UPassword;//用户密码
    @SerializedName("uemail")
    private String UEmail;//用户电子邮箱
    @SerializedName("usex")
    private int USex;//用户性别（0：女，1：男，2：未知）
    @SerializedName("uhead")
    private String UHead;//用户头像地址
    @SerializedName("ustate")
    private int UState;//用户状态
    @SerializedName("urole")
    private int URole;//用户角色
    private Timestamp register_time;//用户注册时间

    public User(String UName, String UPassword, String UEmail, int USex){
        this.UName = UName;
        this.UPassword = UPassword;
        this.UEmail = UEmail;
        this.USex = USex;
        register_time = new Timestamp(System.currentTimeMillis());
    }

    public Long getUId() {
        return UId;
    }

    public void setUId(Long UId) {
        this.UId = UId;
    }

    public String getUName() {
        return UName;
    }

    public void setUName(String UName) {
        this.UName = UName;
    }

    public String getUPassword() {
        return UPassword;
    }

    public void setUPassword(String UPassword) {
        this.UPassword = UPassword;
    }

    public String getUEmail() {
        return UEmail;
    }

    public void setUEmail(String UEmail) {
        this.UEmail = UEmail;
    }

    public int getUSex() {
        return USex;
    }

    public void setUSex(int USex) {
        this.USex = USex;
    }

    public String getUHead() {
        return UHead;
    }

    public void setUHead(String UHead) {
        this.UHead = UHead;
    }

    public int getUState() {
        return UState;
    }

    public void setUState(int UState) {
        this.UState = UState;
    }

    public int getURole() {
        return URole;
    }

    public void setURole(int URole) {
        this.URole = URole;
    }

    public Timestamp getRegister_time() {
        return register_time;
    }

    public void setRegister_time(Timestamp register_time) {
        this.register_time = register_time;
    }
}
