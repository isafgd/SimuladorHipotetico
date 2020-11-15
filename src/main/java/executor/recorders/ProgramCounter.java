package executor.recorders;

import lombok.Data;

@Data
public class ProgramCounter {
    private final int size = 16;
    int pc;
    public ProgramCounter() { // construtor padrão
        this.pc = 0;
    }

    public int get() {
        return this.pc;
    }

    public void set(int newPC) {
        this.pc = newPC;
    }
}
