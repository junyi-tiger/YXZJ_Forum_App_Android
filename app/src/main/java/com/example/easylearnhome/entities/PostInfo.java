package com.example.easylearnhome.entities;

import java.sql.Timestamp;

public class PostInfo {
    private Long uid;//用户id
    private Long pid;//帖子id
    private String userName;//发帖者用户名
    private String userHead;//发帖者头像
    private String title;//帖子标题
    private String content;//帖子内容
    private int likeNum;//喜欢量
    private int commentNum;//评论量
    private Timestamp postTime;//发表时间
    public PostInfo(Post post, User user){
        uid = user.getUId();
        pid = post.getPID();
        userName = user.getUName();
        userHead = user.getUHead();
        title = post.getPTitle();
        content = post.getPContent();
        likeNum = post.getPLike_num();
        commentNum = post.getPComments();
        postTime = post.getPost_time();
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserHead() {
        return userHead;
    }

    public void setUserHead(String userHead) {
        this.userHead = userHead;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(int likeNum) {
        this.likeNum = likeNum;
    }

    public int getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(int commentNum) {
        this.commentNum = commentNum;
    }

    public Timestamp getPostTime() {
        return postTime;
    }

    public void setPostTime(Timestamp postTime) {
        this.postTime = postTime;
    }
}