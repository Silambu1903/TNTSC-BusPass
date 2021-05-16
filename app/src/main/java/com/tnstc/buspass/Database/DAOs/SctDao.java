package com.tnstc.buspass.Database.DAOs;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.tnstc.buspass.Database.Entity.MstEntity;
import com.tnstc.buspass.Database.Entity.PassEntity;
import com.tnstc.buspass.Database.Entity.SctEntity;

import java.util.List;

@Dao
public interface SctDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSctEntry(SctEntity... SctEntity);

    @Query("SELECT *FROM sctentity WHERE date=:date")
    List<SctEntity> currentDatesct(String date);

    @Query("SELECT SUM(total) FROM sctentity WHERE date BETWEEN :startDate AND :endDate")
    int totalSalesCardsct(String startDate, String endDate);

    @Query("SELECT SUM(sctTotalAmount) FROM sctentity WHERE date BETWEEN :startDate AND :endDate")
    int sctTotalAmount(String startDate, String endDate);

    @Query("SELECT min(opening) FROM sctentity WHERE month=:month and card=:card200 ")
    int sctMonthOpen200(String month, int card200);

    @Query("SELECT max(closing) FROM sctentity WHERE month=:month and card=:card200 ")
    int sctMonthCloseMax200(String month, int card200);

    @Query("SELECT max(closing) FROM sctentity WHERE month=:month and card=:card200 ")
    int sctMonthClose200(String month, int card200);

    @Query("SELECT sum(total) FROM sctentity WHERE month=:month and card=:card200 ")
    int sctMonthTotalCard200(String month, int card200);


    @Query("SELECT sum(sctTotalAmount) FROM sctentity WHERE month=:month and card=:card200 ")
    int sctMonthTotalAmount200(String month, int card200);



}
