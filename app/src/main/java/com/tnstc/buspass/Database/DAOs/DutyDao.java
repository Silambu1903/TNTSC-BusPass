package com.tnstc.buspass.Database.DAOs;

import android.database.Cursor;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.tnstc.buspass.Database.Entity.DutyEntity;
import com.tnstc.buspass.Database.Entity.PassEntity;

import java.util.List;

@Dao
public interface DutyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDuty(DutyEntity... dutyEntity);

    @Query("select * FROM dutyentity")
    List<DutyEntity> getAllList();

    @Query("DELETE  FROM DutyEntity where dutyDate=:dutyDate")
    void deleteDuty(String dutyDate);

    @Query("SELECT   year FROM DutyEntity")
    List<String> getYear();

    @Query("SELECT   month FROM DutyEntity")
    List<String> getMonth();

    @Query("SELECT *FROM DutyEntity WHERE month=:monthwise and year=:yearwise")
    List<DutyEntity> getMonthWise(String monthwise, String yearwise);

}
