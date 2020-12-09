package assembler;

public class Instruction {
    private String instruction;
    private Integer operandos;
    private String bin;

    public Instruction (String instruction, Integer operandos, String bin){
        this.instruction = instruction;
        this.operandos = operandos;
        this.bin = bin;
    }

}
