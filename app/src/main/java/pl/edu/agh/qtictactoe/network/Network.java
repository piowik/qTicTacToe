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
        kryo.register(ResolveConflict.class);
        kryo.register(YouLoose.class);
        kryo.register(StartGame.class);
        kryo.register(SelectedCell.class);
        kryo.register(ConflictSolution.class);
    }

    static public class YourTurn {

    }

    static public class ResolveConflict {

    }

    static public class YouLoose {

    }

    static public class YouWin {

    }

    static public class Draw {

    }


    static public class StartGame {

    }

    static public class SelectedCell {
        int selectedCell;

        public int getSelectedCell() {
            return selectedCell;
        }

        public void setSelectedCell(int selectedCell) {
            this.selectedCell = selectedCell;
        }
    }

    static public class ConflictSolution {
        int[] selectedX;
        int[] selectedY;

        public ConflictSolution(int[] selectedX, int[] selectedY) {
            this.selectedX = selectedX;
            this.selectedY = selectedY;
        }

        public int[] getSelectedX() {
            return selectedX;
        }

        public int[] getSelectedY() {
            return selectedY;
        }
    }
}
