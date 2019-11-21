package pl.edu.agh.qtictactoe.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Move {

    int number;
    int cell1;
    int cell2;
}
