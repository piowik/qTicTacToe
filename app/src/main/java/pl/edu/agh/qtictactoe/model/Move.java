package pl.edu.agh.qtictactoe.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Move implements Comparable, Serializable {

    int number;
    int cell1;
    int cell2;

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
