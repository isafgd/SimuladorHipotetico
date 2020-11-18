package executor.recorders;

import lombok.Data;

@Data
public class ProgramCounter {

    private final int size = 16;
    int pc;

    public ProgramCounter(){}

}
