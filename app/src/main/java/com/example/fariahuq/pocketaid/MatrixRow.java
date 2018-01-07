package com.example.fariahuq.pocketaid;

/**
 * Created by Faria huq on 06-Jan-18.
 */

public class MatrixRow {
    long id;
    long parent_id;
    String name;
    int duration;
    String time;
    String intensity;
    String organ;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getParent_id() {
        return parent_id;
    }

    public void setParent_id(long parent_id) {
        this.parent_id = parent_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getIntensity() {
        return intensity;
    }

    public void setIntensity(String intensity) {
        this.intensity = intensity;
    }

    public String getOrgan() {
        return organ;
    }

    public void setOrgan(String organ) {
        this.organ = organ;
    }

    public int MatchCount(MatrixRow matrixRow)
    {
        int i = 0;
        if(!matrixRow.getName().equals(this.getName()))
            return i;
       if(!matrixRow.getOrgan().equals(this.getOrgan()))
            {
                return i;
            }
       else
            i++;
        if(matrixRow.getIntensity().equals(this.getIntensity()))
            i++;
        if(matrixRow.getDuration()==this.getDuration())
            i++;
        if(matrixRow.getTime().equals(this.getTime()))
            i++;
        return i;
    }
}
