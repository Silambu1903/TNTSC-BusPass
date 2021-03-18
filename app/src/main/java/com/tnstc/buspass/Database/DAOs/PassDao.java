package com.tnstc.buspass.Database.DAOs;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.tnstc.buspass.Database.Entity.PassEntity;

import java.util.List;

public interface PassDao {

    @Query("Select* FROM PassEntity")
    List<PassEntity> getAllList();

    @Insert
    void insert(PassEntity passEntity);

    @Update
    void update(PassEntity passEntity);

    @Delete
    void delete(PassEntity passEntity);
}
