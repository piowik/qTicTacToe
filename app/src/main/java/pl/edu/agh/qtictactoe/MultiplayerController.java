package pl.edu.agh.qtictactoe;

import android.os.AsyncTask;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import java.io.IOException;

import pl.edu.agh.qtictactoe.model.Move;
import pl.edu.agh.qtictactoe.network.Network;

public class MultiplayerController extends BaseController {
    private Client client;

    public MultiplayerController(String ip) {
        client = new Client();
        client.start();
        client.addListener(new Listener() {
            @Override
            public void connected(Connection connection) {
                super.connected(connection);
            }

            @Override
            public void disconnected(Connection connection) {
                super.disconnected(connection);
            }

            @Override
            public void received(Connection connection, Object o) {
                super.received(connection, o);
            }
        });
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    client.connect(5000, ip, Network.GAME_PORT);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();

    }

    @Override
    public void onLoopSolved(int position) {

    }

    @Override
    public void onMove(Move move) {

    }
}
