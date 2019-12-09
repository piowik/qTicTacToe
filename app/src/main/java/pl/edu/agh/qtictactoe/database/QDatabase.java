package pl.edu.agh.qtictactoe.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import pl.edu.agh.qtictactoe.database.dao.IPDao;
import pl.edu.agh.qtictactoe.database.entity.IPAdd;

@Database(entities = {IPAdd.class}, version = 1, exportSchema = false)

public abstract class QDatabase extends RoomDatabase {
    public abstract IPDao daoAccess();

    private static QDatabase INSTANCE;

    public static QDatabase getAppDatbase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), QDatabase.class, "QDB").build();
        }
        return INSTANCE;
    }
}