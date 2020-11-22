package executor.recorders;

import lombok.Data;

@Data
public class InstructionRecorder {

    private final int size = 16;
    Integer ri;

    public InstructionRecorder() {ri = 0;}
}
