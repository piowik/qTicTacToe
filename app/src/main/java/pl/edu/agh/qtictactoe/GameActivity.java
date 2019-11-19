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

public class GameActivity extends AppCompatActivity implements SquareClickInterface {
    private GameAdapter gameAdapter;
    private List<GameSquare> gameSquares;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        ButterKnife.bind(this);
        init();
    }

    private void init() {
        gameSquares = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            GameSquare gameSquare = new GameSquare();
            List<Integer> list = new ArrayList<>();
            for (int j = 0; j < 9; j++) {
                list.add(0);
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

    @Override
    public void onSquareClicked(int position) {
        Log.i("GameActivity", "onSquareClicked: " + position);
        List<Integer> dataSet = gameSquares.get(position).getDataset();
        dataSet.set(position,position);
        gameAdapter.notifyItemChanged(position);
    }
}
