package com.example.easylearnhome.entities;

import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;

public class CommentInfo {
    @SerializedName("cid")
    private Long CID;//帖子评论唯一id
    @SerializedName("pid")
    private Long PID;//评论的帖子id
    @SerializedName("uid")
    private Long UID;//发表评论的用户id
    @SerializedName("uname")
    private String UName;//用户名
    @SerializedName("uhead")
    private String UHead;//用户头像
    @SerializedName("ccontent")
    private String CContent;//评论内容
    @SerializedName("clike_num")
    private int CLike_num;//评论的点赞数
    @SerializedName("c_reply_num")
    private int C_reply_num;//评论的回复数
    @SerializedName("comment_time")
    private Timestamp comment_time ;
    public CommentInfo(Comment comment, User user){
        CID = comment.getCID();
        PID = comment.getPID();
        UID = comment.getUID();
        UHead = user.getUHead();
        UName = user.getUName();
        CContent = comment.getCContent();
        CLike_num = comment.getCLike_num();
        C_reply_num = comment.getC_reply_num();
        comment_time = comment.getComment_time();
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

    public String getUName() {
        return UName;
    }

    public void setUName(String UName) {
        this.UName = UName;
    }

    public String getUHead() {
        return UHead;
    }

    public void setUHead(String UHead) {
        this.UHead = UHead;
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
