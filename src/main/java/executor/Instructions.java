package executor;

public class Instructions {
    public  int add (int acc, int opd1) {
        return acc += opd1;
    }
    public  int addi (int pc, int opd1) {
        return pc + opd1;
    }
}
