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


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(PassEntity... passEntities);

    @Query("SELECT* FROM passentity WHERE sNo=(SELECT max(sNo)  FROM passentity)")
    int lastSno();


    @Query("SELECT iNo FROM passentity order by 1 desc limit 1 ")
    int lastIno();

    @Query("SELECT repNo FROM passentity order by 1 desc limit 1 ")
    int lastRepno();


    @Query("SELECT SUM(amount) FROM passentity WHERE date BETWEEN :startDate AND :endDate")
    int getDailyTotalAmount(String startDate, String endDate);

    @Query("SELECT max(sNo) FROM passentity WHERE date BETWEEN :startDate AND :endDate")
    int getDailySales(String startDate, String endDate);

    @Query("SELECT sNo FROM passentity order by 1 desc limit 1 ")
    int getTotalSales();

    @Query("UPDATE passentity SET iNo = :ino ,repno =:repno , newOld =:newOld ,date =:date ," +
            "name =:name ,formArea = :formarea ,toArea =:toarea,busFare =:busFare ,amount =:amount" +
            " ,expDel =:expDel, cellNumber =:cellNumber WHERE sNo = :sno")
    int updateino(int ino, int repno, String newOld, String date, String name,
                  String formarea, String toarea, int busFare, int amount, String expDel, String cellNumber, int sno);


    @Update
    void update(PassEntity passEntity);

    @Delete
    void delete(PassEntity passEntity);


}
