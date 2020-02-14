package com.commutersfamily.commuterfamily.Classes;

import java.util.ArrayList;

public class RecyclerBean {

    private ArrayList<Routes> arrayList;

    public RecyclerBean(ArrayList<Routes> arrayList) {
        this.arrayList = arrayList;
    }

    public ArrayList<Routes> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<Routes> arrayList) {
        this.arrayList = arrayList;
    }
}
