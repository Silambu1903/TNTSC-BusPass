package com.tnstc.buspass.Database.DAOs;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.tnstc.buspass.Database.Entity.MstEntity;
import com.tnstc.buspass.Database.Entity.PassEntity;

import java.util.List;

@Dao
public interface MstDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMstEntry(MstEntity... MstEntity);

    @Query("SELECT *FROM mstentity WHERE date=:date")
    List<MstEntity> currentDate(String date);
}
