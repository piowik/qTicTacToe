package pl.edu.agh.qtictactoe.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import pl.edu.agh.qtictactoe.database.entity.IPAdd;


@Dao
public interface IPDao {
    @Insert
    void insertOnlySingleIp(IPAdd ip);

    @Query("SELECT * FROM ipAddTable")
    List<IPAdd> fetchAll();

    @Update
    void updateMovie(IPAdd ipAdd);

    @Delete
    void deleteMovie(IPAdd ipAdd);
}
