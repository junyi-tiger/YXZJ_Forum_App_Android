package com.example.easylearnhome.entities;

import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;

public class Reply_to_comment {
    @SerializedName("rid")
    private Long RID;//唯一Id
    @SerializedName("cid")
    private Long CID;//评论的id
    @SerializedName("uid")
    private Long UID;//回复者的id
    @SerializedName("rcontent")
    private String RContent;//回复的内容
    private Timestamp reply_time ;//回复发表的时间

    public Reply_to_comment(Long CID, Long UID, String RContent){
        this.CID = CID;
        this.UID = UID;
        this.RContent = RContent;
//        reply_time = new Timestamp(System.currentTimeMillis());
    }

    public Long getRID() {
        return RID;
    }

    public void setRID(Long RID) {
        this.RID = RID;
    }

    public Long getCID() {
        return CID;
    }

    public void setCID(Long CID) {
        this.CID = CID;
    }

    public Long getUID() {
        return UID;
    }

    public void setUID(Long UID) {
        this.UID = UID;
    }

    public String getRContent() {
        return RContent;
    }

    public void setRContent(String RContent) {
        this.RContent = RContent;
    }

    public Timestamp getReply_time() {
        return reply_time;
    }

    public void setReply_time(Timestamp reply_time) {
        this.reply_time = reply_time;
    }
}
