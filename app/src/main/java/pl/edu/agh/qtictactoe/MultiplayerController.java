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
        Network.register(client);
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
                if (o instanceof Network.StartGame) {
                    System.out.println("Game Started");
                    gameActivityInterface.startGame();
                }

                if (o instanceof Move) {
                    Move move = (Move) o;
                    gameActivityInterface.onMove(move);

                }
                if (o instanceof Network.YouLoose) {
                    gameActivityInterface.onWin(false);

                }
                if (o instanceof Network.YouWin) {
                    gameActivityInterface.onWin(true);

                }
                if (o instanceof Network.Draw) {
                    gameActivityInterface.onDraw();

                }
                if (o instanceof Network.YourTurn) {
                    gameActivityInterface.yourTurn(((Network.YourTurn) o).getTurnNumber());
                }
                if (o instanceof Network.ConflictSolution) {
                    int[] x = ((Network.ConflictSolution) o).getSelectedX();
                    int[] y = ((Network.ConflictSolution) o).getSelectedY();
                    gameActivityInterface.onResolvedConflict(x,y);
                }

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
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                client.sendTCP(move);
                return null;
            }
        }.execute();

    }
}
