package executor.recorders;

import lombok.Data;

@Data
public class OperationMode {

    private final int size = 8;
    int mop;

    public OperationMode(){mop = -1;}

}
