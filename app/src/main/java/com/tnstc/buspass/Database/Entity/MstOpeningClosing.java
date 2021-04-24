package com.tnstc.buspass.Database.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(primaryKeys = {"mstMonthYear", "mstCard"})
public class MstOpeningClosing {

    @ColumnInfo(name = "mstMonthYear")
    public long mstMonthYear;

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

    @ColumnInfo(name = "mstOpenMax")
    public int mstOpenMax;

    @ColumnInfo(name = "mstCloseMax")
    public int mstCloseMax;

    @ColumnInfo(name = "mstTotalCard")
    public int mstTotalCard;

    @ColumnInfo(name = "mstTotalAmount")
    public int mstTotalAmount;

    @ColumnInfo(name = "mstbalopen")
    public int mstBalOpen;

    @ColumnInfo(name = "mstbalclose")
    public int mstBalClose;

    @ColumnInfo(name = "mstbalcard")
    public int mstBalCard;

    @ColumnInfo(name = "mstbalTotalcard")
    public int mstBalTotalCard;

    public MstOpeningClosing(long mstMonthYear, int mstOpening, int mstClosing, int mstCard, int mstSpare, String mstKey, int mstTotal, String mstMonth, String mstYear, int mstOpenMax, int mstCloseMax, int mstTotalCard, int mstTotalAmount, int mstBalOpen, int mstBalClose, int mstBalCard, int mstBalTotalCard) {
        this.mstMonthYear = mstMonthYear;
        this.mstOpening = mstOpening;
        this.mstClosing = mstClosing;
        this.mstCard = mstCard;
        this.mstSpare = mstSpare;
        this.mstKey = mstKey;
        this.mstTotal = mstTotal;
        this.mstMonth = mstMonth;
        this.mstYear = mstYear;
        this.mstOpenMax = mstOpenMax;
        this.mstCloseMax = mstCloseMax;
        this.mstTotalCard = mstTotalCard;
        this.mstTotalAmount = mstTotalAmount;
        this.mstBalOpen = mstBalOpen;
        this.mstBalClose = mstBalClose;
        this.mstBalCard = mstBalCard;
        this.mstBalTotalCard = mstBalTotalCard;
    }

    public long getMstMonthYear() {
        return mstMonthYear;
    }

    public void setMstMonthYear(long mstMonthYear) {
        this.mstMonthYear = mstMonthYear;
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

    public int getMstOpenMax() {
        return mstOpenMax;
    }

    public void setMstOpenMax(int mstOpenMax) {
        this.mstOpenMax = mstOpenMax;
    }

    public int getMstCloseMax() {
        return mstCloseMax;
    }

    public void setMstCloseMax(int mstCloseMax) {
        this.mstCloseMax = mstCloseMax;
    }

    public int getMstTotalCard() {
        return mstTotalCard;
    }

    public void setMstTotalCard(int mstTotalCard) {
        this.mstTotalCard = mstTotalCard;
    }

    public int getMstTotalAmount() {
        return mstTotalAmount;
    }

    public void setMstTotalAmount(int mstTotalAmount) {
        this.mstTotalAmount = mstTotalAmount;
    }

    public int getMstBalOpen() {
        return mstBalOpen;
    }

    public void setMstBalOpen(int mstBalOpen) {
        this.mstBalOpen = mstBalOpen;
    }

    public int getMstBalClose() {
        return mstBalClose;
    }

    public void setMstBalClose(int mstBalClose) {
        this.mstBalClose = mstBalClose;
    }

    public int getMstBalCard() {
        return mstBalCard;
    }

    public void setMstBalCard(int mstBalCard) {
        this.mstBalCard = mstBalCard;
    }

    public int getMstBalTotalCard() {
        return mstBalTotalCard;
    }

    public void setMstBalTotalCard(int mstBalTotalCard) {
        this.mstBalTotalCard = mstBalTotalCard;
    }
}
