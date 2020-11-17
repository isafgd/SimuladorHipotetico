package executor.recorders;

import lombok.Data;

@Data
public class OperationMode {

    private int mode;

    public OperationMode(){
        mode = 0;
    }
}
