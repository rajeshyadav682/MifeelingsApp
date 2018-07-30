package com.example.rajesh.mifeelingsapp.category;

public class CategoryEvents_Details {

    public CategoryEvents_Details(String id, String uid, String feed, String cat_id, String cat_name, String feed_date, int post_type, String user_like, String like){
        this.id=id;
        this.uid=uid;
        this.feed = feed;
        this.cat_id=cat_id;
        this.cat_name =cat_name;
        this.feed_date = feed_date;
        this.post_type =post_type;
  //      this.comment_desc=comment_decc;
        this.user_like=user_like;
        this.like = like;
 }

 private String id;
 private String uid;
 private String feed;
 private String cat_id;
 private  String cat_name;
 private String feed_date;
 private int post_type;

    public String getId() {
        return id;
    }

    public String getUid() {
        return uid;
    }

    public String getFeed() {
        return feed;
    }

    public String getCat_id() {
        return cat_id;
    }

    public String getCat_name() {
        return cat_name;
    }

    public String getFeed_date() {
        return feed_date;
    }

    public int getPost_type() {
        return post_type;
    }

    public String getComment_desc() {
        return comment_desc;
    }

    public String getUser_like() {
        return user_like;
    }

    public String getLike() {
        return like;
    }

    private String comment_desc;
 private String user_like;
 private String like;
}
