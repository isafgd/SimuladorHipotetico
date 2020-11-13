package executor.recorders;

import lombok.Data;

@Data
public class ProgramCounter {

    private int next_instruction;

    public ProgramCounter(){
        next_instruction = 19; //Endereço da primeira instrução
    }
}
