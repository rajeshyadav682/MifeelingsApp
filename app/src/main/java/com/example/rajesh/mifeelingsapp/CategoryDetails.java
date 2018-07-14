package com.example.rajesh.mifeelingsapp;

public class CategoryDetails {
    public CategoryDetails( String totalFollowers ,String image,String cat_name,int userfolow) {
        this.image =image;
        this.totalFollowers = totalFollowers;
        this.cat_name= cat_name;
        this.userfolow = userfolow;
    }

    public String getImage() {
        return image;
    }

   
    public String getTotalFollowers() {
        return totalFollowers;
    }

   

    private String image;
    private String totalFollowers;

    public String getCat_name() {
        return cat_name;
    }

    private String cat_name;

    public int getUserfolow() {
        return userfolow;
    }

    private int userfolow;


}
