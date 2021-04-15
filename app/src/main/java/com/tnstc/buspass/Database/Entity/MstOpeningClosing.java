package com.tnstc.buspass.Database.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(primaryKeys = {"mstMonthYear"})
public class MstOpeningClosing {

    @ColumnInfo(name = "mstMonthYear")
    public int mstMonthYear;

    @ColumnInfo(name = "mstOpening")
    public int mstOpening;

    @ColumnInfo(name = "mstClosing")
    public int mstClosing;

    @ColumnInfo(name = "mstCard")
    public int mstCard;

    @ColumnInfo(name = "mstSpare")
    public int mstSpare;

    @ColumnInfo(name = "mstKey")
    public String mstKey;

    @ColumnInfo(name = "mstTotal")
    public int mstTotal;

    @ColumnInfo(name = "mstMonth")
    public String mstMonth;

    @ColumnInfo(name = "mstYear")
    public String mstYear;

    public MstOpeningClosing(int mstOpening, int mstClosing, int mstCard, int mstSpare, String mstKey, int mstTotal, String mstMonth, String mstYear) {
        this.mstOpening = mstOpening;
        this.mstClosing = mstClosing;
        this.mstCard = mstCard;
        this.mstSpare = mstSpare;
        this.mstKey = mstKey;
        this.mstTotal = mstTotal;
        this.mstMonth = mstMonth;
        this.mstYear = mstYear;
    }

    public int getMstOpening() {
        return mstOpening;
    }

    public void setMstOpening(int mstOpening) {
        this.mstOpening = mstOpening;
    }

    public int getMstClosing() {
        return mstClosing;
    }

    public void setMstClosing(int mstClosing) {
        this.mstClosing = mstClosing;
    }

    public int getMstCard() {
        return mstCard;
    }

    public void setMstCard(int mstCard) {
        this.mstCard = mstCard;
    }

    public int getMstSpare() {
        return mstSpare;
    }

    public void setMstSpare(int mstSpare) {
        this.mstSpare = mstSpare;
    }

    public String getMstKey() {
        return mstKey;
    }

    public void setMstKey(String mstKey) {
        this.mstKey = mstKey;
    }

    public int getMstTotal() {
        return mstTotal;
    }

    public void setMstTotal(int mstTotal) {
        this.mstTotal = mstTotal;
    }

    public String getMstMonth() {
        return mstMonth;
    }

    public void setMstMonth(String mstMonth) {
        this.mstMonth = mstMonth;
    }

    public String getMstYear() {
        return mstYear;
    }

    public void setMstYear(String mstYear) {
        this.mstYear = mstYear;
    }
}
