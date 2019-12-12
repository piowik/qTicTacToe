package pl.edu.agh.qtictactoe.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameSquare {
    public static final int SELECTED_NONE = 0;
    public static final int SELECTED_X = 1;
    public static final int SELECTED_0 = 2;

    private List<UnderlinedInteger> dataset;
    private int selection = SELECTED_NONE;
}
