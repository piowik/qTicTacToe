package pl.edu.agh.qtictactoe.network;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

import pl.edu.agh.qtictactoe.model.Move;

public class Network {
    static public final int GAME_PORT = 54557;

    static public void register(EndPoint endPoint) {
        Kryo kryo = endPoint.getKryo();
        kryo.register(Move.class);
        kryo.register(YourTurn.class);
        kryo.register(Conflict.class);
        kryo.register(IsFinished.class);
        kryo.register(StartGame.class);
        kryo.register(ConfictSolution.class);
    }

    static public class YourTurn {

    }

    static public class Conflict {

    }

    static public class IsFinished {

    }

    static public class StartGame {

    }

    static public class ConfictSolution {
        int selectedCell;
    }

}
