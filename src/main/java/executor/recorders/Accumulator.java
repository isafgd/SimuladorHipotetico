package executor.recorders;

import lombok.Data;

@Data
public class Accumulator {

    private float accumulator;

    public Accumulator(){
        accumulator = Float.parseFloat(null);
    }
}
