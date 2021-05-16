package com.tnstc.buspass.Database.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(primaryKeys = {"sctMonthYear", "sctCard"})
public class SctOpeningClosing {

    @ColumnInfo(name = "sctMonthYear")
    public long sctMonthYear;

    @ColumnInfo(name = "sctOpening")
    public int sctOpening;

    @ColumnInfo(name = "sctClosing")
    public int sctClosing;

    @ColumnInfo(name = "sctCard")
    public int sctCard;

    @ColumnInfo(name = "sctSpare")
    public int sctSpare;

    @ColumnInfo(name = "sctKey")
    public String sctKey;

    @ColumnInfo(name = "sctTotal")
    public int sctTotal;

    @ColumnInfo(name = "sctMonth")
    public String sctMonth;

    @ColumnInfo(name = "sctYear")
    public String sctYear;

    @ColumnInfo(name = "sctOpenMax")
    public int sctOpenMax;

    @ColumnInfo(name = "sctCloseMax")
    public int sctCloseMax;

    @ColumnInfo(name = "sctTotalCard")
    public int sctTotalCard;

    @ColumnInfo(name = "sctTotalAmount")
    public int sctTotalAmount;

    @ColumnInfo(name = "sctbalopen")
    public int sctBalOpen;

    @ColumnInfo(name = "sctbalclose")
    public int sctBalClose;

    @ColumnInfo(name = "sctbalcard")
    public int sctBalCard;

    @ColumnInfo(name = "sctbalTotalcard")
    public int sctBalTotalCard;

    public SctOpeningClosing(long sctMonthYear, int sctOpening, int sctClosing, int sctCard, int sctSpare, String sctKey, int sctTotal, String sctMonth, String sctYear, int sctOpenMax, int sctCloseMax, int sctTotalCard, int sctTotalAmount, int sctBalOpen, int sctBalClose, int sctBalCard, int sctBalTotalCard) {
        this.sctMonthYear = sctMonthYear;
        this.sctOpening = sctOpening;
        this.sctClosing = sctClosing;
        this.sctCard = sctCard;
        this.sctSpare = sctSpare;
        this.sctKey = sctKey;
        this.sctTotal = sctTotal;
        this.sctMonth = sctMonth;
        this.sctYear = sctYear;
        this.sctOpenMax = sctOpenMax;
        this.sctCloseMax = sctCloseMax;
        this.sctTotalCard = sctTotalCard;
        this.sctTotalAmount = sctTotalAmount;
        this.sctBalOpen = sctBalOpen;
        this.sctBalClose = sctBalClose;
        this.sctBalCard = sctBalCard;
        this.sctBalTotalCard = sctBalTotalCard;
    }

    public long getSctMonthYear() {
        return sctMonthYear;
    }

    public void setSctMonthYear(long sctMonthYear) {
        this.sctMonthYear = sctMonthYear;
    }

    public int getSctOpening() {
        return sctOpening;
    }

    public void setSctOpening(int sctOpening) {
        this.sctOpening = sctOpening;
    }

    public int getSctClosing() {
        return sctClosing;
    }

    public void setSctClosing(int sctClosing) {
        this.sctClosing = sctClosing;
    }

    public int getSctCard() {
        return sctCard;
    }

    public void setSctCard(int sctCard) {
        this.sctCard = sctCard;
    }

    public int getSctSpare() {
        return sctSpare;
    }

    public void setSctSpare(int sctSpare) {
        this.sctSpare = sctSpare;
    }

    public String getSctKey() {
        return sctKey;
    }

    public void setSctKey(String sctKey) {
        this.sctKey = sctKey;
    }

    public int getSctTotal() {
        return sctTotal;
    }

    public void setSctTotal(int sctTotal) {
        this.sctTotal = sctTotal;
    }

    public String getSctMonth() {
        return sctMonth;
    }

    public void setSctMonth(String sctMonth) {
        this.sctMonth = sctMonth;
    }

    public String getSctYear() {
        return sctYear;
    }

    public void setSctYear(String sctYear) {
        this.sctYear = sctYear;
    }

    public int getSctOpenMax() {
        return sctOpenMax;
    }

    public void setSctOpenMax(int sctOpenMax) {
        this.sctOpenMax = sctOpenMax;
    }

    public int getSctCloseMax() {
        return sctCloseMax;
    }

    public void setSctCloseMax(int sctCloseMax) {
        this.sctCloseMax = sctCloseMax;
    }

    public int getSctTotalCard() {
        return sctTotalCard;
    }

    public void setSctTotalCard(int sctTotalCard) {
        this.sctTotalCard = sctTotalCard;
    }

    public int getSctTotalAmount() {
        return sctTotalAmount;
    }

    public void setSctTotalAmount(int sctTotalAmount) {
        this.sctTotalAmount = sctTotalAmount;
    }

    public int getSctBalOpen() {
        return sctBalOpen;
    }

    public void setSctBalOpen(int sctBalOpen) {
        this.sctBalOpen = sctBalOpen;
    }

    public int getSctBalClose() {
        return sctBalClose;
    }

    public void setSctBalClose(int sctBalClose) {
        this.sctBalClose = sctBalClose;
    }

    public int getSctBalCard() {
        return sctBalCard;
    }

    public void setSctBalCard(int sctBalCard) {
        this.sctBalCard = sctBalCard;
    }

    public int getSctBalTotalCard() {
        return sctBalTotalCard;
    }

    public void setSctBalTotalCard(int sctBalTotalCard) {
        this.sctBalTotalCard = sctBalTotalCard;
    }
}
