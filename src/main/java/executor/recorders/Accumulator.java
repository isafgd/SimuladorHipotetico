package executor.recorders;

import lombok.Data;

@Data
public class Accumulator {
    private final int size = 16;
    int acc;
    public Accumulator() { // construtor padrão
        this.acc = 0;
    }

    public int getACC() {
        return this.acc;
    }

    public void setACC(int newACC) {
        this.acc = newACC;
    }
}
