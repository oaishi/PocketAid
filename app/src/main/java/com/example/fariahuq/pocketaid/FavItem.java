package com.example.fariahuq.pocketaid;

/**
 * Created by Faria huq on 09-Nov-17.
 */

public class FavItem
{

    long id;
    long fid;

    // constructors
    public FavItem() {
    }

    public FavItem(long id, long fid) {
        this.id = id;
        this.fid = fid;
    }

    // setters
    public void setId(long id) {
        this.id = id;
    }

    public void setFid(long fid){
        this.fid = fid;
    }

   // getters
    public long getId() {
        return this.id;
    }
    public long getFid(){
        return this.fid;
    }

}
