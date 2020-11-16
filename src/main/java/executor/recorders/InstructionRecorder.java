package executor.recorders;

import lombok.Data;

@Data
public class InstructionRecorder {

    private final int size = 16;
    int IR;

    public InstructionRecorder() {
        this.IR = 0;
    }

    public int getIR() {
        return this.IR;
    }

    public void setIR(int IR) {
        this.IR = IR;
    }
}
