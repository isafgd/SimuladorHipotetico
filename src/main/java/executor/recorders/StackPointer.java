package executor.recorders;

import lombok.Data;

@Data
public class StackPointer {

    private final int size = 16;
    int pointer;

    public StackPointer() {
        pointer = 0;
    }
}