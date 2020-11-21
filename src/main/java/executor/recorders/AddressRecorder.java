package executor.recorders;

import lombok.Data;

@Data
public class AddressRecorder {

    private final int size = 16;
    int re;

    public AddressRecorder(){
        re = 19;
    }

    public void increment(){
        if (re<500) {
            re++;
        } //Sinalizar que a memória está cheia
    }
}
