package pl.edu.agh.qtictactoe;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import java.util.ArrayList;
import java.util.List;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        GridLayout gridLayout = findViewById(R.id.gameGridLayout);
        List<List<TextView>> board = createGameBoard(gridLayout);
        board.forEach(e -> e.forEach(v -> System.out.println(v.getId())));

    }

    private List<List<TextView>> createGameBoard(GridLayout gridLayout) {
        List<List<TextView>> board = new ArrayList<>();

        for (int i = 0; i < 9; i++) {
            List<TextView> row = new ArrayList<>();
            for (int j = 0; j < 9; j++) {
                TextView textView = createTextView(gridLayout, i, j);
                row.add(textView);
            }
            board.add(row);
        }
        return board;
    }

    private TextView createTextView(GridLayout gridLayout, int row, int column) {
        TextView textView = new TextView(this.getApplicationContext());
        textView.setHeight(0);
        textView.setWidth(0);
        textView.setId(10 * row + column);
        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        textView.setGravity(View.TEXT_ALIGNMENT_CENTER);
        textView.setText("X");

        Drawable backgroundColor =
                AppCompatResources.getDrawable(getApplicationContext(), R.color.colorPrimaryDark);
        textView.setBackground(backgroundColor);
        textView.setTextColor(Color.WHITE);
        textView.setWidth(gridLayout.getWidth() / 9);
        textView.setHeight(gridLayout.getHeight() / 9);

        gridLayout.addView(textView, new GridLayout.LayoutParams(
                GridLayout.spec(row, GridLayout.CENTER),
                GridLayout.spec(column, GridLayout.CENTER)));

        return textView;
    }
}
