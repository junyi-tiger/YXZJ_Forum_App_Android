package com.example.easylearnhome.entities;

import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;

public class LikeComment {
    @SerializedName("id")
    private Long id;
    @SerializedName("uid")
    private Long UID;
    @SerializedName("cid")
    private Long CID;
    @SerializedName("like_time")
    Timestamp like_time;
    public LikeComment(Long UID, Long CID){
        this.UID = UID;
        this.CID = CID;
//        like_time = new Timestamp(System.currentTimeMillis());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUID() {
        return UID;
    }

    public void setUID(Long UID) {
        this.UID = UID;
    }

    public Long getCID() {
        return CID;
    }

    public void setCID(Long CID) {
        this.CID = CID;
    }

    public Timestamp getLike_time() {
        return like_time;
    }

    public void setLike_time(Timestamp like_time) {
        this.like_time = like_time;
    }
}
