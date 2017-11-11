package com.example.fariahuq.pocketaid;

/**
 * Created by Faria huq on 09-Nov-17.
 */

public class SymptomsItem
{

    private long id;
    private long fid;
    private String image;
    private String title;
    private String desc;

    // constructors
    public SymptomsItem() {
    }

    public SymptomsItem(String image, String title, String desc) {
        this.title = title;
        this.image = image;
        this.desc=desc;
    }

    public SymptomsItem(long id, long fid, String image, String title, String desc) {
        this.id = id;
        this.fid = fid;
        this.image = image;
        this.title = title;
        this.desc=desc;
    }

    // setters
    public void setId(long id) {
        this.id = id;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setFid(long fid){
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

    public long getFid(){
        return this.fid;
    }

    public String getDesc() {return this.desc;}
}
