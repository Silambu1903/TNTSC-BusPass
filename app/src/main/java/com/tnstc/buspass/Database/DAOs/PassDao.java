package com.tnstc.buspass.Database.DAOs;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Dao;

import com.tnstc.buspass.Database.Entity.PassEntity;

import java.util.List;
@Dao
public interface PassDao {

    @Query("select * FROM passentity")
    List<PassEntity> getAllList();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(PassEntity... passEntities);
    @Update
    void update(PassEntity passEntity);

    @Delete
    void delete(PassEntity passEntity);
}
