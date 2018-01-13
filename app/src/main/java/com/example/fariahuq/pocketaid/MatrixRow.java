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
    int spinnerpos;
    int spinner1pos;
    int spinner2pos;
    int spinner3pos;
    int spinner4pos;

    public MatrixRow()
    {
    }

    public MatrixRow(long id, String name, int duration, String time, String intensity, String organ) {
        this.id = id;
        this.name = name;
        this.duration = duration;
        this.time = time;
        this.intensity = intensity;
        this.organ = organ;
    }

    public MatrixRow(int spinnerpos, int spinner1pos, int spinner2pos, int spinner3pos, int spinner4pos) {
        this.spinnerpos = spinnerpos;
        this.spinner1pos = spinner1pos;
        this.spinner2pos = spinner2pos;
        this.spinner3pos = spinner3pos;
        this.spinner4pos = spinner4pos;
        this.setTime("None");
        this.setOrgan("None");
        this.setDuration(0);
        this.setIntensity("None");
        this.setName("None");
    }

    public int getSpinnerpos() {
        return spinnerpos;
    }

    public void setSpinnerpos(int spinnerpos) {
        this.spinnerpos = spinnerpos;
    }

    public int getSpinner1pos() {
        return spinner1pos;
    }

    public void setSpinner1pos(int spinner1pos) {
        this.spinner1pos = spinner1pos;
    }

    public int getSpinner2pos() {
        return spinner2pos;
    }

    public void setSpinner2pos(int spinner2pos) {
        this.spinner2pos = spinner2pos;
    }

    public int getSpinner3pos() {
        return spinner3pos;
    }

    public void setSpinner3pos(int spinner3pos) {
        this.spinner3pos = spinner3pos;
    }

    public int getSpinner4pos() {
        return spinner4pos;
    }

    public void setSpinner4pos(int spinner4pos) {
        this.spinner4pos = spinner4pos;
    }

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
