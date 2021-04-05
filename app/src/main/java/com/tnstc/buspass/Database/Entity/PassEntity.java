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


    @ColumnInfo(name = "date")
    public String date;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "formArea")
    public String fromArea;

    @ColumnInfo(name = "toArea")
    public String toArea;


    @ColumnInfo(name = "busFare")
    public int busFare;

    @ColumnInfo(name = "amount")
    public int amount;

    @ColumnInfo(name = "expDel")
    public String expDel;

    @ColumnInfo(name = "cellNumber")
    public String cellNumber;

    @ColumnInfo(name = "month")
    public String month;

    @ColumnInfo(name = "year")
    public String year;

    public PassEntity(int sno, int iNo, int repNo, String newOld, String date, String name, String fromArea, String toArea, int busFare, int amount, String expDel, String cellNumber, String month, String year) {
        this.sno = sno;
        this.iNo = iNo;
        this.repNo = repNo;
        this.newOld = newOld;
        this.date = date;
        this.name = name;
        this.fromArea = fromArea;
        this.toArea = toArea;
        this.busFare = busFare;
        this.amount = amount;
        this.expDel = expDel;
        this.cellNumber = cellNumber;
        this.month = month;
        this.year = year;
    }





    public int getSno() {
        return sno;
    }

    public int getiNo() {
        return iNo;
    }

    public int getRepNo() {
        return repNo;
    }

    public String getNewOld() {
        return newOld;
    }

    public String getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public String getFromArea() {
        return fromArea;
    }

    public String getToArea() {
        return toArea;
    }

    public int getBusFare() {
        return busFare;
    }

    public int getAmount() {
        return amount;
    }

    public String getExpDel() {
        return expDel;
    }

    public String getCellNumber() {
        return cellNumber;
    }

    public String getMonth() {
        return month;
    }

    public String getYear() {
        return year;
    }
}
