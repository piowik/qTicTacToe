package pl.edu.agh.qtictactoe.database.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ipAddTable")
public class IPAdd {
    @NonNull
    @PrimaryKey
    private String ipAdd;

    public IPAdd(String ipAdd) {
        this.ipAdd= ipAdd;
    }

    public String getIpAdd() {
        return ipAdd;
    }

    public void setIpAdd(String ipAdd) {
        this.ipAdd=ipAdd;
    }

}
