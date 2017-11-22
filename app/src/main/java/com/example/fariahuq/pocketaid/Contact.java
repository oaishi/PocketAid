package com.example.fariahuq.pocketaid;

/**
 * Created by Faria huq on 09-Nov-17.
 */

public class Contact
{

    long id;
    String title;
    String desc;

    // constructors
    public Contact() {
    }

    public Contact(String title, String desc) {
        this.title = title;
        this.desc=desc;
    }

    public Contact(long id, long fid, String image, String title, String desc) {
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

    //name
    public String getTitle() {
        return this.title;
    }

    //phone
    public String getDesc() {return this.desc;}
}
