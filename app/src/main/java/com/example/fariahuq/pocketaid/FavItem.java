package com.example.fariahuq.pocketaid;

/**
 * Created by Faria huq on 09-Nov-17.
 */

public class FavItem
{

    int id;
    int fid;

    // constructors
    public FavItem() {
    }

    public FavItem(int id, int fid) {
        this.id = id;
        this.fid = fid;
    }

    // setters
    public void setId(int id) {
        this.id = id;
    }

    public void setFid(int fid){
        this.fid = fid;
    }

   // getters
    public long getId() {
        return this.id;
    }
    public int getFid(){
        return this.fid;
    }

}
