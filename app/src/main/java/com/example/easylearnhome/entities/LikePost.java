package com.example.easylearnhome.entities;

import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;

public class LikePost {
    private Long id;
    @SerializedName("uid")
    private Long UID;
    @SerializedName("pid")
    private Long PID;
    private Timestamp like_time;
    public LikePost(Long UID, Long PID){
        this.UID = UID;
        this.PID = PID;
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

    public Long getPID() {
        return PID;
    }

    public void setPID(Long PID) {
        this.PID = PID;
    }

    public Timestamp getLike_time() {
        return like_time;
    }

    public void setLike_time(Timestamp like_time) {
        this.like_time = like_time;
    }
}
