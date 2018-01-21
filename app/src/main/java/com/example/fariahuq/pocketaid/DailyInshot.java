package com.example.fariahuq.pocketaid;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Faria huq on 21-Jan-18.
 */

public class DailyInshot implements Parcelable {

    private String title;
    private String image;
    private int id;
    private String desc;

    public DailyInshot(){

    }

    public DailyInshot(String title, String image, String desc) {
        this.title = title;
        this.image = image;
        this.desc = desc;
    }

    public DailyInshot(String title, String image, int id, String desc) {
        this.title = title;
        this.image = image;
        this.id = id;
        this.desc = desc;
    }

    protected DailyInshot(Parcel in) {
        title = in.readString();
        image = in.readString();
        id = in.readInt();
        desc = in.readString();
    }

    public static final Creator<DailyInshot> CREATOR = new Creator<DailyInshot>() {
        @Override
        public DailyInshot createFromParcel(Parcel in) {
            return new DailyInshot(in);
        }

        @Override
        public DailyInshot[] newArray(int size) {
            return new DailyInshot[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(image);
        parcel.writeInt(id);
        parcel.writeString(desc);
    }
}
