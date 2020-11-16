package executor.recorders;

import lombok.Data;

@Data
public class OperationMode {

    private final int size = 8;
    int OPM;

    public OperationMode() {
    }

    public int getOPM() {
        return this.OPM;
    }

    public void setOPM(int OPM) {
        this.OPM = OPM;
/*
        switch (OPM) {
            case 0:
                //direto
                break;
            case 1:
                //imediato
                break;
            case 2:
                //copy
                //segundo operador é indireto
                break;
            case 4:
                //primeiro operando é indireto
                break;
            case 6:
                //copy
                //primeiro e segundo operando são indiretos
                break;
            default:
                System.out.println("Não está de acordo");
        }
 */
    }
}
