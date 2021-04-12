package com.tnstc.buspass.Database.DAOs;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import com.tnstc.buspass.Database.Entity.MstEntity;

@Dao
public interface MstDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMstEntry(MstEntity... MstEntity);
}
