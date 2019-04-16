package com.example.easylearnhome.entities;

import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;

public class Comment {
    /**
     * 帖子评论表，存储帖子的评论信息
     */
    @SerializedName("cid")
    private Long CID;//帖子评论唯一id
    @SerializedName("pid")
    private Long PID;//评论的帖子id
    @SerializedName("uid")
    private Long UID;//发表评论的用户id
    @SerializedName("ccontent")
    private String CContent;//评论内容
    @SerializedName("clike_num")
    private int CLike_num;//评论的点赞数
    @SerializedName("c_reply_num")
    private int C_reply_num;//评论的回复数
    @SerializedName("comment_time")
    private Timestamp comment_time ;

    public Comment(Long PID, Long UID ,String CContent){
        this.PID = PID;
        this.UID = UID;
        this.CContent = CContent;
        comment_time = new Timestamp(System.currentTimeMillis());
    }

    public Long getCID() {
        return CID;
    }

    public void setCID(Long CID) {
        this.CID = CID;
    }

    public Long getPID() {
        return PID;
    }

    public void setPID(Long PID) {
        this.PID = PID;
    }

    public Long getUID() {
        return UID;
    }

    public void setUID(Long UID) {
        this.UID = UID;
    }

    public String getCContent() {
        return CContent;
    }

    public void setCContent(String CContent) {
        this.CContent = CContent;
    }

    public int getCLike_num() {
        return CLike_num;
    }

    public void setCLike_num(int CLike_num) {
        this.CLike_num = CLike_num;
    }

    public int getC_reply_num() {
        return C_reply_num;
    }

    public void setC_reply_num(int c_reply_num) {
        C_reply_num = c_reply_num;
    }

    public Timestamp getComment_time() {
        return comment_time;
    }

    public void setComment_time(Timestamp comment_time) {
        this.comment_time = comment_time;
    }
}

