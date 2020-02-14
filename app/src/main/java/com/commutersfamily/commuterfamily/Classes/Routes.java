package com.commutersfamily.commuterfamily.Classes;

public class Routes {
   private String Date,Number
    ,Day
    ,ETimeFrom
    ,ETimeTo
    ,MTimeFrom
    ,MTimeTo
    ,RouteID
    ,Shift
    ,Time,AdressFrom,AdressTo
           ,PickUp;
private LatLongClass
    LocFrom
    ,LocTo;
    public void setAdressFrom(String adressFrom) {
        AdressFrom = adressFrom;
    }

    public void setAdressTo(String adressTo) {
        AdressTo = adressTo;
    }

    public String getAdressFrom() {
        return AdressFrom;
    }

    public String getAdressTo() {
        return AdressTo;
    }

    public Routes() {
    }


    public Routes(String date, String number, String day, String ETimeFrom, String ETimeTo, String MTimeFrom, String MTimeTo, String routeID, String shift, String time, String adressFrom, String adressTo, String pickUp, LatLongClass locFrom, LatLongClass locTo) {
        Date = date;
        Number = number;
        Day = day;
        this.ETimeFrom = ETimeFrom;
        this.ETimeTo = ETimeTo;
        this.MTimeFrom = MTimeFrom;
        this.MTimeTo = MTimeTo;
        RouteID = routeID;
        Shift = shift;
        Time = time;
        AdressFrom = adressFrom;
        AdressTo = adressTo;
        PickUp = pickUp;
        LocFrom = locFrom;
        LocTo = locTo;
    }

    public void setPickUp(String pickUp) {
        PickUp = pickUp;
    }

    public String getPickUp() {
        return PickUp;
    }

    public void setNumber(String number) {
        Number = number;
    }

    public String getNumber() {
        return Number;
    }

    public void setDate(String date) {
        Date = date;
    }

    public void setDay(String day) {
        Day = day;
    }

    public void setETimeFrom(String ETimeFrom) {
        this.ETimeFrom = ETimeFrom;
    }

    public void setETimeTo(String ETimeTo) {
        this.ETimeTo = ETimeTo;
    }

    public void setLocFrom(LatLongClass locFrom) {
        LocFrom = locFrom;
    }

    public void setLocTo(LatLongClass locTo) {
        LocTo = locTo;
    }

    public void setMTimeFrom(String MTimeFrom) {
        this.MTimeFrom = MTimeFrom;
    }

    public void setMTimeTo(String MTimeTo) {
        this.MTimeTo = MTimeTo;
    }

    public void setRouteID(String routeID) {
        RouteID = routeID;
    }

    public void setShift(String shift) {
        Shift = shift;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getDate() {
        return Date;
    }

    public String getDay() {
        return Day;
    }

    public String getETimeFrom() {
        return ETimeFrom;
    }

    public String getETimeTo() {
        return ETimeTo;
    }

    public LatLongClass getLocFrom() {
        return LocFrom;
    }

    public LatLongClass getLocTo() {
        return LocTo;
    }

    public String getMTimeFrom() {
        return MTimeFrom;
    }

    public String getMTimeTo() {
        return MTimeTo;
    }

    public String getRouteID() {
        return RouteID;
    }

    public String getShift() {
        return Shift;
    }

    public String getTime() {
        return Time;
    }
}
