package com.tnstc.buspass.Database.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(primaryKeys = {"date"})
public class DutyEntity {

    @ColumnInfo(name = "dutyDate")
    String  dutyDate;
    @ColumnInfo(name = "workDuty")
    String workDuty;
    @ColumnInfo(name = "month")
    String month;
    @ColumnInfo(name = "year")
    String year;
    @ColumnInfo(name = "date")
    long date;

    public DutyEntity(String dutyDate, String workDuty, String month, String year, long date) {
        this.dutyDate = dutyDate;
        this.workDuty = workDuty;
        this.month = month;
        this.year = year;
        this.date = date;
    }

    public String getDutyDate() {
        return dutyDate;
    }

    public void setDutyDate(String dutyDate) {
        this.dutyDate = dutyDate;
    }

    public String getWorkDuty() {
        return workDuty;
    }

    public void setWorkDuty(String workDuty) {
        this.workDuty = workDuty;
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

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
