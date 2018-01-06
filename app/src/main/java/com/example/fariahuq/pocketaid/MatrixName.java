package com.example.fariahuq.pocketaid;

import java.util.ArrayList;

/**
 * Created by Faria huq on 06-Jan-18.
 */

public class MatrixName {
    long id;
    String name;
    ArrayList<MatrixRow> items;

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
}
