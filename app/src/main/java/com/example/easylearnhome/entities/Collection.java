package com.example.easylearnhome.entities;

import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;

public class Collection {
    @SerializedName("id")
    private Long id;
    @SerializedName("uid")
    private Long UID;//收藏者id
    @SerializedName("pid")
    private Long PID;//帖子id
    @SerializedName("collect_time")
    private Timestamp collect_time;
    public Collection(Long UID, Long PID){
        this.UID = UID;
        this.PID = PID;
//        collect_time = new Timestamp(System.currentTimeMillis());
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

    public Timestamp getCollect_time() {
        return collect_time;
    }

    public void setCollect_time(Timestamp collect_time) {
        this.collect_time = collect_time;
    }
}
