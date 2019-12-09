package pl.edu.agh.qtictactoe;

import android.os.AsyncTask;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import java.io.IOException;
import java.util.ArrayList;

import pl.edu.agh.qtictactoe.network.Network;

public class GameServer {
    private Server server;
    private ArrayList<PlayerConnection> players = new ArrayList<>();

    public GameServer(ServerStartedInterface startedInterface) {
        server = new Server() {
            protected Connection newConnection() {
                return new PlayerConnection();
            }
        };
        Network.register(server);
        server.addListener(new Listener() {
            @Override
            public void connected(Connection connection) {
                players.add((PlayerConnection) connection);
                sendTCP(new Network.StartGame());
                super.connected(connection);
            }

            @Override
            public void disconnected(Connection connection) {
                super.disconnected(connection);
            }

            @Override
            public void received(Connection connection, Object o) {
//                players.get(0).sendTCP(new GameState()); // example
                super.received(connection, o);
            }
        });
        try {
            server.bind(Network.GAME_PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        server.start();
        startedInterface.onStarted();
    }


    private void sendTCP(Object o) {
        new SendTCP().execute(o);
    }

    private void sendToTCP(int i, Object o) {
        new SendToTCP().execute(i, o);
    }

    private class SendTCP extends AsyncTask<Object, Void, Void> {
        SendTCP() {
        }

        protected Void doInBackground(Object... data) {
            server.sendToAllTCP(data[0]);
            return null;
        }
    }

    private class SendToTCP extends AsyncTask<Object, Void, Void> {
        SendToTCP() {
        }

        protected Void doInBackground(Object... data) {
            server.sendToTCP(Integer.parseInt(data[0].toString()), data[1]);
            return null;
        }
    }


    private static class PlayerConnection extends Connection {
        int id;
    }
}
