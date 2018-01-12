package com.example.fariahuq.pocketaid;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Faria huq on 06-Jan-18.
 */

public class MatrixName {
    long id;
    String name;
    ArrayList<MatrixRow> items;

    public MatrixName() {
    }

    public MatrixName(String name) {
        this.name = name;
    }

    public MatrixName(ArrayList<MatrixRow> items) {
        this.items = items;
    }

    public MatrixName(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public MatrixName(long id, String name, ArrayList<MatrixRow> items) {
        this.id = id;
        this.name = name;
        this.items = items;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<MatrixRow> getItems() {
        return items;
    }

    public void setItems(ArrayList<MatrixRow> items) {
        this.items = items;
    }

    public int getSize(){return items.size();}

    public double SimilarityCount(MatrixName matrixName)
    {
        int i=0;
        ArrayList<MatrixRow> input = matrixName.getItems();
        int sizeinput , size;
        size = items.size();
        sizeinput = input.size();
        for(int init=0;init<sizeinput;init++)
        {
            for(int init1=0;init1<size;init1++)
            {
                i= i+ input.get(init).MatchCount(items.get(init1));
            }
        }
        Log.i("checkup",Integer.toString(i));
        double in = (i / (double) (size*4)) * 100;
        return in;
    }

}
