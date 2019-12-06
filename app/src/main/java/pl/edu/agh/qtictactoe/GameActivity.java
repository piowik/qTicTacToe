package pl.edu.agh.qtictactoe;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.edu.agh.qtictactoe.adapter.GameAdapter;
import pl.edu.agh.qtictactoe.callback.SquareClickInterface;
import pl.edu.agh.qtictactoe.model.GameSquare;
import pl.edu.agh.qtictactoe.model.Move;
import pl.edu.agh.qtictactoe.model.UnderlinedInteger;

public class GameActivity extends AppCompatActivity implements SquareClickInterface, GameActivityInterface {
    private GameAdapter gameAdapter;
    private List<GameSquare> gameSquares;
    private BaseController controller;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private int moveCounter;
    private int movesLeft;
    private ArrayList<Integer> selectedCells = new ArrayList<>();
    private boolean isSolvingLoop;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        ButterKnife.bind(this);
        init();

        String hostIp = getIntent().getExtras().getString("ip", null);
        boolean isSingleplayer = hostIp == null;
        if (isSingleplayer) {
            controller = new SingleplayerController();
        } else {
            controller = new MultiplayerController(hostIp);
        }
        controller.attach(this);
    }

    @Override
    public void onSquareClicked(int position) {
        Log.i("GameActivity", "onSquareClicked: " + position);
        selectedSquare(position);
    }

    private void init() {
        initUI();
        initGame();
    }

    private void initUI() {
        gameSquares = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            GameSquare gameSquare = new GameSquare();
            List<UnderlinedInteger> list = new ArrayList<>();
            for (int j = 0; j < 9; j++) {
                list.add(new UnderlinedInteger(-1));
            }
            gameSquare.setDataset(list);
            gameSquares.add(gameSquare);
        }
        gameAdapter = new GameAdapter(gameSquares, this);
        recyclerView.setAdapter(gameAdapter);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void initGame() {
        movesLeft = 2;
        isSolvingLoop = false;
    }

    private void selectedSquare(int position) {
        if (moveCounter == 10) // crashes when moveCounter == 10 (win/draw condition will be met until then, so just a sanity check)
            return;
        int cellNumber = position;
        if (isSolvingLoop) {
            controller.onLoopSolved(position);
        } else {
            if (selectedCells.contains(cellNumber)) // already selected by player this round
                return;
            selectedCells.add(cellNumber);
            List<UnderlinedInteger> dataSet = gameSquares.get(position).getDataset();
            dataSet.set(moveCounter, new UnderlinedInteger(moveCounter, false));
            gameAdapter.notifyItemChanged(position);
            movesLeft--;
            if (movesLeft == 0) {
                Move move = new Move(moveCounter, selectedCells.get(0), selectedCells.get(1));
                recyclerView.setEnabled(false);
                recyclerView.setClickable(false);
                controller.onMove(move);
            }
        }
    }


    @Override
    public void updateGame() {
        //
    }

    @Override
    public void yourTurn(int turnNumber) {
        moveCounter = turnNumber;
        selectedCells.clear();
        movesLeft = 2;
        recyclerView.setEnabled(true);
        recyclerView.setClickable(true);
        isSolvingLoop = false;
        // TODO: display some info O/X's turn?
    }

    @Override
    public void solveLoop(Move move) {
        isSolvingLoop = true;
        moveCounter = move.getNumber();
        GameSquare gameSquare1 = gameSquares.get(move.getCell1());
        GameSquare gameSquare2 = gameSquares.get(move.getCell2());
        gameSquare1.getDataset().get(moveCounter).setUnderlined(true);
        gameSquare2.getDataset().get(moveCounter).setUnderlined(true);
        gameAdapter.notifyItemChanged(move.getCell1());
        gameAdapter.notifyItemChanged(move.getCell2());
    }

    @Override
    public void onWin(boolean hasWon) {
        finish();
    }
}
