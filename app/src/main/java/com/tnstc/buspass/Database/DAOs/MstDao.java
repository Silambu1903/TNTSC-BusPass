package com.tnstc.buspass.Database.DAOs;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.tnstc.buspass.Database.Entity.MstEntity;


import java.util.List;

@Dao
public interface MstDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMstEntry(MstEntity... MstEntity);

    @Query("SELECT *FROM mstentity WHERE date=:date")
    List<MstEntity> currentDate(String date);

    @Query("SELECT SUM(total) FROM mstentity WHERE date BETWEEN :startDate AND :endDate")
    int totalSalesCard(String startDate, String endDate);

    @Query("SELECT SUM(mstTotalAmount) FROM mstentity WHERE date BETWEEN :startDate AND :endDate")
    int mstTotalAmount(String startDate, String endDate);

    @Query("SELECT min(opening) FROM mstentity WHERE month=:month and card=:card200 ")
    int mstMonthOpen200(String month, int card200);

    @Query("SELECT max(closing) FROM mstentity WHERE month=:month and card=:card200 ")
    int mstMonthCloseMax200(String month, int card200);

    @Query("SELECT max(closing) FROM mstentity WHERE month=:month and card=:card200 ")
    int mstMonthClose200(String month, int card200);

    @Query("SELECT sum(total) FROM mstentity WHERE month=:month and card=:card200 ")
        int mstMonthTotalCard200(String month, int card200);


    @Query("SELECT sum(mstTotalAmount) FROM mstentity WHERE month=:month and card=:card200 ")
    int mstMonthTotalAmount200(String month, int card200);



}
