package pl.edu.agh.qtictactoe.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import pl.edu.agh.qtictactoe.database.entity.IPAdd;


@Dao
public interface IPDao {
    @Insert
    void insertOnlySingleIp(IPAdd ip);

    @Query("SELECT * FROM ipAddTable")
    List<IPAdd> fetchAll();

}
