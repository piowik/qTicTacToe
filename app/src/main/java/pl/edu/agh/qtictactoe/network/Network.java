package pl.edu.agh.qtictactoe.network;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

import pl.edu.agh.qtictactoe.model.GameState;

public class Network {
    static public final int GAME_PORT = 54557;

    static public void register(EndPoint endPoint) {
        Kryo kryo = endPoint.getKryo();
        kryo.register(GameState.class);
    }
}
