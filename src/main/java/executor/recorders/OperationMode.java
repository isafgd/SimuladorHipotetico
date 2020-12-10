package executor.recorders;

import lombok.Data;

@Data
public class OperationMode {

    private final int size = 8;
    Integer mop;

    public OperationMode(){mop = -1;}

}
