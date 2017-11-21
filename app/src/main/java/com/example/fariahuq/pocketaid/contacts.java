package com.example.fariahuq.pocketaid;

/**
 * Created by Faria huq on 09-Nov-17.
 */

public class contacts
{

    long id;
    String title;
    String desc;

    // constructors
    public contacts() {
    }

    public contacts(String image, String title, String desc) {
        this.title = title;
        this.desc=desc;
    }

    public contacts(long id, long fid, String image, String title, String desc) {
        this.id = id;
        this.title = title;
        this.desc=desc;
    }

    // setters
    public void setId(long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDesc(String desc){this.desc = desc;}

    // getters
    public long getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDesc() {return this.desc;}
}
