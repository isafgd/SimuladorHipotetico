package executor.recorders;

import lombok.Data;

@Data
public class Accumulator {

    private int accumulator;

    public Accumulator(){
        accumulator = 0;
    }
}
