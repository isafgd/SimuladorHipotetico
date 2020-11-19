package executor.recorders;

import lombok.Data;

@Data
public class InstructionRecorder {

    private final int size = 16;
    int ri;

    public InstructionRecorder() {ri = 0;}
}
