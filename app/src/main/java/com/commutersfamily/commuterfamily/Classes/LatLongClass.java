package com.commutersfamily.commuterfamily.Classes;

public class LatLongClass {
    private double Lat;
    private double Long;

    public LatLongClass( ) {

    }

    @Override
    public String  toString() {
        return "LatLongClass{" +
                "Lat=" + Lat +
                ", Long=" + Long +
                '}';
    }

    public void setLat(double lat) {
        Lat = lat;
    }

    public void setLong(double aLong) {
        Long = aLong;
    }

    public double getLat() {
        return Lat;
    }

    public double getLong() {
        return Long;
    }
}
