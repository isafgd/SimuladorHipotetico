package executor.recorders;

import lombok.Data;

@Data
public class AddressRecorder {

    private int address;

    public AddressRecorder(){
        address = 0;
    }
}
