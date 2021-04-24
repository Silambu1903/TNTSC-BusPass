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

    @Query("SELECT COUNT(sNo) FROM passentity WHERE date BETWEEN :startDate AND :endDate")
    int getDailySales(String startDate, String endDate);

    @Query("SELECT COUNT(sNo) FROM passentity order by 1 desc limit 1 ")
    int getTotalSales();

    @Query("UPDATE passentity SET iNo = :ino  WHERE sNo = :sno")
    int updateino(int ino, int sno);

    @Query("UPDATE passentity SET repno =:repno WHERE sNo = :sno")
    int updaterRepNo(int repno, int sno);

    @Query("UPDATE passentity SET newOld =:newOld WHERE sNo = :sno")
    int updateNewOld(String newOld, int sno);

    @Query("UPDATE passentity SET date =:date  WHERE sNo = :sno")
    int updateDate(String date, int sno);


    @Query("UPDATE passentity SET name =:name   WHERE sNo = :sno")
    int updateName(String name, int sno);

    @Query("UPDATE passentity SET formArea = :formarea  WHERE sNo = :sno")
    int updatefrom(String formarea, int sno);

    @Query("UPDATE passentity SET toArea =:toarea WHERE sNo = :sno")
    int updateTo(String toarea, int sno);

    @Query("UPDATE passentity SET busFare =:busFare  WHERE sNo = :sno")
    int busFare(int busFare, int sno);

    @Query("UPDATE passentity SET amount =:amount  WHERE sNo = :sno")
    int amount(int amount, int sno);

    @Query("UPDATE passentity SET expDel =:expDel  WHERE sNo = :sno")
    int expDel(String expDel, int sno);

    @Query("UPDATE passentity SET cellNumber =:cellNumber  WHERE sNo = :sno")
    int cellNumber(String cellNumber, int sno);


    @Query("SELECT * FROM passentity where iNo=:idno")
    List<PassEntity> getIdData(int idno);

    @Query("SELECT *FROM passentity WHERE month=:monthwise and year=:yearwise")
    List<PassEntity> getMonthWise(String monthwise, String yearwise);

    @Query("SELECT SUM(amount) FROM passentity WHERE month=:monthwise and year=:yearwise")
    int monthWiseTotal(String monthwise, String yearwise);

    @Query("SELECT COUNT(sNo) FROM passentity WHERE month=:monthwise and year=:yearwise")
    int getMonthWiseTotalSales(String monthwise, String yearwise);

    @Query("SELECT *FROM passentity WHERE month=:monthwise and year=:yearwise")
    List<PassEntity> currentMonth(String monthwise, String yearwise);

    @Query("SELECT   year FROM passentity")
    List<String> getYear();

    @Query("SELECT   month FROM passentity")
    List<String> getMonth();

    @Update
    void update(PassEntity passEntity);

    @Delete
    void delete(PassEntity passEntity);


}
