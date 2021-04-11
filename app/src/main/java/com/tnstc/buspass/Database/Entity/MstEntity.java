package com.tnstc.buspass.Database.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class MstEntity {

    @PrimaryKey
    public int sec;
    @ColumnInfo(name = "card")
    public int card;

    @ColumnInfo(name = "spare")
    public int spare;

    @ColumnInfo(name = "key")
    public String key;

    @ColumnInfo(name = "opening")
    public int opening;

    @ColumnInfo(name = "closing")
    public int closing;

    @ColumnInfo(name = "month")
    public String month;

    @ColumnInfo(name = "year")
    public String year;


    @ColumnInfo(name = "date")
    public String date;

    public MstEntity(int sec, int card, int spare, String key, int opening, int closing, String month, String year, String date) {
        this.sec = sec;
        this.card = card;
        this.spare = spare;
        this.key = key;
        this.opening = opening;
        this.closing = closing;
        this.month = month;
        this.year = year;
        this.date = date;
    }

    public int getSec() {
        return sec;
    }

    public void setSec(int sec) {
        this.sec = sec;
    }

    public int getCard() {
        return card;
    }

    public void setCard(int card) {
        this.card = card;
    }

    public int getSpare() {
        return spare;
    }

    public void setSpare(int spare) {
        this.spare = spare;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getOpening() {
        return opening;
    }

    public void setOpening(int opening) {
        this.opening = opening;
    }

    public int getClosing() {
        return closing;
    }

    public void setClosing(int closing) {
        this.closing = closing;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
