package com.tnstc.buspass.Database;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.tnstc.buspass.Database.DAOs.DutyDao;
import com.tnstc.buspass.Database.DAOs.MstDao;
import com.tnstc.buspass.Database.DAOs.MstOpeningDao;
import com.tnstc.buspass.Database.DAOs.PassDao;
import com.tnstc.buspass.Database.DAOs.SctDao;
import com.tnstc.buspass.Database.DAOs.SctOpeningDao;
import com.tnstc.buspass.Database.Entity.DutyEntity;
import com.tnstc.buspass.Database.Entity.MstEntity;
import com.tnstc.buspass.Database.Entity.MstOpeningClosing;
import com.tnstc.buspass.Database.Entity.PassEntity;
import com.tnstc.buspass.Database.Entity.SctEntity;
import com.tnstc.buspass.Database.Entity.SctOpeningClosing;

@Database(entities = {PassEntity.class, MstEntity.class, MstOpeningClosing.class, SctEntity.class, SctOpeningClosing.class, DutyEntity.class}, version = 2, exportSchema = false)
@TypeConverters(Converters.class)

public abstract class TnstcBusPassDB extends RoomDatabase {
    public static volatile TnstcBusPassDB INSTANCE;
    static Migration migration_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE PassEntity ADD DEPARMENT TEXT");

        }
    };

    public static TnstcBusPassDB getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (TnstcBusPassDB.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TnstcBusPassDB.class, "TnstcBussPass")
                            .allowMainThreadQueries()
                            .addMigrations(migration_1_2)
                            .build();

                }
            }
        }
        return INSTANCE;
    }

    public abstract MstOpeningDao OpeningDao();

    public abstract PassDao passDao();

    public abstract MstDao mstDao();

    public abstract SctDao sctDao();

    public abstract SctOpeningDao sctOpeningDao();

    public abstract DutyDao dutyDao();

}
