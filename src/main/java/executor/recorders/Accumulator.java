package executor.recorders;

import lombok.Data;

@Data
public class Accumulator {

    private final int size = 16;
    Integer acc;

    public Accumulator(){
        acc = 0;
    }
}
