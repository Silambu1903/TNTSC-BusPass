package com.tnstc.buspass.Database.DAOs;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.tnstc.buspass.Database.Entity.MstOpeningClosing;
import com.tnstc.buspass.Database.Entity.SctOpeningClosing;

import java.util.List;


@Dao
public interface SctOpeningDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSctMonth(SctOpeningClosing... SctOpeningClosing);

    @Query("SELECT max(sctOpening) FROM sctopeningclosing WHERE sctMonth=:month and sctCard=:card200 ")
    int sctMonthBalOpen200(String month, int card200);

    @Query("SELECT max(sctClosing) FROM sctopeningclosing WHERE sctMonth=:month and sctCard=:card200 ")
    int sctMonthBalClose200(String month, int card200);

    @Query("SELECT (sctMonthYear) FROM sctopeningclosing WHERE sctMonth=:month and sctCard=:card200 ")
    int sctMonthYear(String month, int card200);

    @Query("SELECT (sctOpening) FROM sctopeningclosing WHERE sctMonth=:month and sctCard=:card200 ")
    int sctOpening(String month, int card200);

    @Query("SELECT (sctClosing) FROM sctopeningclosing WHERE sctMonth=:month and sctCard=:card200 ")
    int sctClosing(String month, int card200);

    @Query("SELECT (sctCard) FROM sctopeningclosing WHERE sctMonth=:month and sctCard=:card200 ")
    int sctCard(String month, int card200);

    @Query("SELECT (sctSpare) FROM sctopeningclosing WHERE sctMonth=:month and sctCard=:card200 ")
    int sctSpare(String month, int card200);


    @Query("SELECT (sctTotal) FROM sctopeningclosing WHERE sctMonth=:month and sctCard=:card200 ")
    int sctTotal(String month, int card200);

    @Query("SELECT (sctKey) FROM sctopeningclosing WHERE sctMonth=:month and sctCard=:card200 ")
    String sctKey(String month, int card200);

    @Query("SELECT   sctYear FROM sctopeningclosing")
    List<String> sctGetYear();

    @Query("SELECT   sctMonth FROM sctopeningclosing")
    List<String> sctGetMonth();

    @Query("SELECT *FROM sctopeningclosing WHERE sctMonth=:monthwise and sctYear=:yearwise")
    List<SctOpeningClosing> getMonthWisesct(String monthwise, String yearwise);
}
