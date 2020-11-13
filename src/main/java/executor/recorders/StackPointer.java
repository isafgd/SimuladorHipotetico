package executor.recorders;

import lombok.Data;

@Data
public class StackPointer {

    private int pointer;

    public StackPointer(){
        pointer = 0;
    }
}
