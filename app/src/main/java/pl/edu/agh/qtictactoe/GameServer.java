package pl.edu.agh.qtictactoe;

import android.os.AsyncTask;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

import pl.edu.agh.qtictactoe.logic.GameLogic;
import pl.edu.agh.qtictactoe.model.GameState;
import pl.edu.agh.qtictactoe.model.Move;
import pl.edu.agh.qtictactoe.model.Winner;
import pl.edu.agh.qtictactoe.network.Network;

public class GameServer {
    private Server server;
    private GameLogic gameLogic = new GameLogic(new GameState());
    private ArrayList<PlayerConnection> players = new ArrayList<>();
    Move lastMove = new Move();

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
                if (o instanceof Move) {
                    System.out.println("Move received");
                    Move move = (Move) o;

                    Connection sendTo = players.stream()
                            .filter(e -> e.getID() != connection.getID())
                            .collect(Collectors.toList()).get(0);
                    sendToTCP(sendTo.getID(), o);

                    boolean isQuantumLoop = gameLogic.isQuantumLoop(move.getCell1(), move, move.getNumber());
                    if (isQuantumLoop) {
                        lastMove = move;
                        sendToTCP(sendTo.getID(), new Network.ResolveConflict());
                    } else {
                        sendToTCP(sendTo.getID(), new Network.YourTurn(gameLogic.getActualGameState().getRoundNumber()+1));
                    }

                } else if (o instanceof Network.SelectedCell) {
                    Connection nextPlayer = connection.getID() == players.get(0).getID() ? players.get(1) : players.get(0);

                    Network.SelectedCell selectedCell = (Network.SelectedCell) o;
                    gameLogic.resolveQuantumLoop(lastMove, selectedCell.getSelectedCell());
                    int[] selectedX = gameLogic.getActualGameState().getSelectedX().stream().mapToInt(i -> i).toArray();
                    int[] selectedO = gameLogic.getActualGameState().getSelectedO().stream().mapToInt(i -> i).toArray();
                    Network.ConflictSolution conflictSolution = new Network.ConflictSolution(selectedX, selectedO);
                    sendToTCP(players.get(0).getID(), conflictSolution);
                    sendToTCP(players.get(1).getID(), conflictSolution);

                    Winner winner = gameLogic.whoWins();
                    if (winner.equals(Winner.O_WINS)) {
                        sendToTCP(players.get(0).getID(), new Network.YouWin());
                        sendToTCP(players.get(1).getID(), new Network.YouLoose());
                    } else if (winner.equals(Winner.X_WINS)) {
                        sendToTCP(players.get(0).getID(), new Network.YouLoose());
                        sendToTCP(players.get(1).getID(), new Network.YouWin());
                    } else if (winner.equals(Winner.DRAW)) {
                        sendToTCP(players.get(0).getID(), new Network.Draw());
                        sendToTCP(players.get(0).getID(), new Network.Draw());
                    } else if (winner.equals(Winner.NOBODY)) {
                        sendToTCP(nextPlayer.getID(), new Network.YourTurn(gameLogic.getActualGameState().getRoundNumber()));
                    }

                }
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
        boolean isX;
    }
}
