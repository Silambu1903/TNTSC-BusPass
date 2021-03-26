package com.tnstc.buspass.Database.DAOs;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.TypeConverter;
import androidx.room.Update;
import androidx.room.Dao;

import com.tnstc.buspass.Database.Entity.PassEntity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Dao
public interface PassDao {


    @Query("select * FROM passentity")
    List<PassEntity> getAllList();

    @Query("SELECT SUM(amount)FROM passentity")
    int getTotalAmount();

    @Query("SELECT SUM(amount)FROM passentity WHERE date = date")
    int getDailyTotalAmount();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(PassEntity... passEntities);


    @Query("delete from passentity where sNo IN  (:uiDs)")
    void deleteEmployees(String[] uiDs);

    @Update
    void update(PassEntity passEntity);

    @Delete
    void delete(PassEntity passEntity);


}
