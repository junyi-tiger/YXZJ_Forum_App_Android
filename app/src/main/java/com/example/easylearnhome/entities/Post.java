package com.example.easylearnhome.entities;

import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;

public class Post {
    @SerializedName("pid")
    private Long PID;//帖子唯一id
    @SerializedName("uid")
    private Long UId;//帖子所属用户的id
    @SerializedName("ptitle")
    private String PTitle;//帖子主题
    @SerializedName("pcontent")
    private String PContent;//帖子内容
    @SerializedName("plike_num")
    private int PLike_num;//帖子点赞数
    @SerializedName("pcomments")
    private int PComments;//帖子评论数
    @SerializedName("pcollection_num")
    private int PCollection_num;//帖子收藏数
    @SerializedName("pstate")
    private int PState;//帖子状态
    @SerializedName("ptag")
    private int PTag;//帖子标签
    @SerializedName("post_time")
    private Timestamp Post_time;

    public Post(Long UId, String PTitle, String PContent) {
        this.UId = UId;
        this.PTitle = PTitle;
        this.PContent = PContent;
        Post_time = new Timestamp(System.currentTimeMillis());
    }

    public void setPLike_num(int PLike_num) {
        this.PLike_num = PLike_num;
    }

    public void setPComments(int PComments) {
        this.PComments = PComments;
    }

    public void setPState(int PState) {
        this.PState = PState;
    }

    public void setPTag(int PTag) {
        this.PTag = PTag;
    }

    public int getPCollection_num() {
        return PCollection_num;
    }

    public void setPCollection_num(int PCollection_num) {
        this.PCollection_num = PCollection_num;
    }

    public int getPLike_num() {
        return PLike_num;
    }

    public int getPComments() {
        return PComments;
    }

    public int getPState() {
        return PState;
    }

    public int getPTag() {
        return PTag;
    }

    public String getPTitle() {
        return PTitle;
    }

    public String getPContent() {
        return PContent;
    }

    public Long getPID() {
        return PID;
    }

    public Long getUId() {
        return UId;
    }

    public void setPTitle(String PTitle) {
        this.PTitle = PTitle;
    }

    public void setPContent(String PContent) {
        this.PContent = PContent;
    }

    public void setPID(Long PID) {
        this.PID = PID;
    }

    public void setUId(Long UId) {
        this.UId = UId;
    }

    public Timestamp getPost_time() {
        return Post_time;
    }

    public void setPost_time(Timestamp post_time) {
        Post_time = post_time;
    }
}