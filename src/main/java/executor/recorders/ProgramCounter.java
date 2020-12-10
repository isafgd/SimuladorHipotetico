package executor.recorders;

import lombok.Data;

@Data
public class ProgramCounter {

    private final int size = 16;
    Integer pc;

    public ProgramCounter(){pc = 19;}

}
