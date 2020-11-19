package executor.recorders;

import lombok.Data;

@Data
public class Accumulator {

    private final int size = 16;
    int acc;

    public Accumulator(){
        acc = 0;
    }
}
