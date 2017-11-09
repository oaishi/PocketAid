package com.example.fariahuq.pocketaid;

/**
 * Created by Faria huq on 09-Nov-17.
 */

public class SelfTestItem
{

    int id;
    int fid;
    String image;
    String title;
    String desc;

    // constructors
    public SelfTestItem() {
    }

    public SelfTestItem(String image, String title, String desc) {
        this.title = title;
        this.image = image;
        this.desc=desc;
    }

    public SelfTestItem(int id, int fid, String image, String title, String desc) {
        this.id = id;
        this.fid = fid;
        this.image = image;
        this.title = title;
        this.desc=desc;
    }

    // setters
    public void setId(int id) {
        this.id = id;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setFid(int fid){
        this.fid = fid;
    }

    public void setDesc(String desc){this.desc = desc;}

    // getters
    public long getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getImage() {
        return this.image;
    }

    public int getFid(){
        return this.fid;
    }

    public String getDesc() {return this.desc;}
}
