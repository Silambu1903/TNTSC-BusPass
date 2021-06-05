package com.tnstc.buspass.Database.DAOs;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;


import com.tnstc.buspass.Database.Entity.MstOpeningClosing;
import com.tnstc.buspass.Database.Entity.PassEntity;


import java.util.List;

@Dao
public interface MstOpeningDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMstMonth(MstOpeningClosing... MstOpeningClosing);


    @Query("SELECT max(mstOpening) FROM mstopeningclosing WHERE mstMonth=:month and mstCard=:card200 ")
    int mstMonthBalOpen200(String month, int card200);

    @Query("SELECT max(mstClosing) FROM mstopeningclosing WHERE mstMonth=:month and mstCard=:card200 ")
    int mstMonthBalClose200(String month, int card200);

    @Query("SELECT (mstMonthYear) FROM mstopeningclosing WHERE mstMonth=:month and mstCard=:card200 ")
    int mstMonthYear(String month, int card200);

    @Query("SELECT (mstOpening) FROM mstopeningclosing WHERE mstMonth=:month and mstCard=:card200 ")
    int mstOpening(String month, int card200);

    @Query("SELECT (mstClosing) FROM mstopeningclosing WHERE mstMonth=:month and mstCard=:card200 ")
    int mstClosing(String month, int card200);

    @Query("SELECT (mstCard) FROM mstopeningclosing WHERE mstMonth=:month and mstCard=:card200 ")
    int mstCard(String month, int card200);

    @Query("SELECT (mstSpare) FROM mstopeningclosing WHERE mstMonth=:month and mstCard=:card200 ")
    int mstSpare(String month, int card200);


    @Query("SELECT (mstTotal) FROM mstopeningclosing WHERE mstMonth=:month and mstCard=:card200 ")
    int mstTotal(String month, int card200);

    @Query("SELECT (mstKey) FROM mstopeningclosing WHERE mstMonth=:month and mstCard=:card200 ")
    String mstKey(String month, int card200);

    @Query("SELECT   mstYear FROM MstOpeningClosing")
    List<String> mstGetYear();

    @Query("SELECT   mstMonth FROM MstOpeningClosing")
    List<String> mstGetMonth();

    @Query("SELECT *FROM mstopeningclosing WHERE mstMonth=:monthwise and mstYear=:yearwise")
    List<MstOpeningClosing> getMonthWiseMst(String monthwise, String yearwise);


    @Query("SELECT (mstMonth) FROM mstopeningclosing WHERE mstMonth=:month")
    String mstMonthEmptyCheck(String month);


    void delete(MstOpeningClosing mstOpeningClosing);

}
