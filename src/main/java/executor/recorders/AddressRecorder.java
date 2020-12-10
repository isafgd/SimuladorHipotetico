package executor.recorders;

import lombok.Data;

@Data
public class AddressRecorder {

    private final int size = 16;
    Integer re;

    public AddressRecorder(){
        re = 240;
    }
}
