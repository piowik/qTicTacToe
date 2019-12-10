package pl.edu.agh.qtictactoe.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameSquare {
    private List<UnderlinedInteger> dataset;
    private boolean isSolved = false;
}
