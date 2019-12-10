package pl.edu.agh.qtictactoe.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UnderlinedInteger {
    private int value;
    private boolean underlined;
    private boolean isSelected;

    public UnderlinedInteger(int value) {
        this.value = value;
    }
}
