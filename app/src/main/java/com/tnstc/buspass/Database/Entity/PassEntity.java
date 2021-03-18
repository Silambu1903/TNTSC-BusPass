package com.tnstc.buspass.Database.Entity;

import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class PassEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "sNo")
    public int sno;

    @ColumnInfo(name = "iNo")
    public int iNo;

    @ColumnInfo(name = "repNo")
    public int repNo;

    @ColumnInfo(name = "newOld")
    public String newOld;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name="formArea")
    public  String fromArea;

    @ColumnInfo(name = "toArea")
    public String toArea;

    @ColumnInfo(name ="date")
    public String date;

    @ColumnInfo(name="busFare")
    public int busFare;

    @ColumnInfo(name = "amount")
    public  int amount;

    @ColumnInfo(name = "expDel")
    public String expDel;

    @ColumnInfo(name = "cellNumber")
    public String cellNumber;


    public PassEntity(int sno, int iNo, int repNo, String newOld, String name, String fromArea, String toArea, String date, int busFare, int amount, String expDel, String cellNumber) {
        this.sno = sno;
        this.iNo = iNo;
        this.repNo = repNo;
        this.newOld = newOld;
        this.name = name;
        this.fromArea = fromArea;
        this.toArea = toArea;
        this.date = date;
        this.busFare = busFare;
        this.amount = amount;
        this.expDel = expDel;
        this.cellNumber = cellNumber;
    }

    public int getSno() {
        return sno;
    }

    public void setSno(int sno) {
        this.sno = sno;
    }

    public int getiNo() {
        return iNo;
    }

    public void setiNo(int iNo) {
        this.iNo = iNo;
    }

    public int getRepNo() {
        return repNo;
    }

    public void setRepNo(int repNo) {
        this.repNo = repNo;
    }

    public String getNewOld() {
        return newOld;
    }

    public void setNewOld(String newOld) {
        this.newOld = newOld;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFromArea() {
        return fromArea;
    }

    public void setFromArea(String fromArea) {
        this.fromArea = fromArea;
    }

    public String getToArea() {
        return toArea;
    }

    public void setToArea(String toArea) {
        this.toArea = toArea;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getBusFare() {
        return busFare;
    }

    public void setBusFare(int busFare) {
        this.busFare = busFare;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getExpDel() {
        return expDel;
    }

    public void setExpDel(String expDel) {
        this.expDel = expDel;
    }

    public String getCellNumber() {
        return cellNumber;
    }

    public void setCellNumber(String cellNumber) {
        this.cellNumber = cellNumber;
    }
}
