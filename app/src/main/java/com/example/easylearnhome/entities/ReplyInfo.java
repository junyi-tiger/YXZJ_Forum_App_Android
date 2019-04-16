package com.example.easylearnhome.entities;

import java.sql.Timestamp;

public class ReplyInfo {
    private Long rid;
    private Long cid;
    private Long uid;
    private String uname;
    private String rcontent;
    private Timestamp reply_time;

    public ReplyInfo(Reply_to_comment reply_to_comment, User user){
        rid = reply_to_comment.getRID();
        cid = reply_to_comment.getCID();
        uid = reply_to_comment.getUID();
        uname = user.getUName();
        rcontent = reply_to_comment.getRContent();
        reply_time = reply_to_comment.getReply_time();
    }

    public Timestamp getReply_time() {
        return reply_time;
    }

    public void setReply_time(Timestamp reply_time) {
        this.reply_time = reply_time;
    }

    public Long getRid() {
        return rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getRcontent() {
        return rcontent;
    }

    public void setRcontent(String rcontent) {
        this.rcontent = rcontent;
    }
}
