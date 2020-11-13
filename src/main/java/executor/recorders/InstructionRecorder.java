package executor.recorders;

import lombok.Data;

/*String bin = "0111";
  int numero = Integer.parseInt(bin, 2);//Nome da variavel e tipo, 2 = binary. Converte o binario para int
  System.out.println(numero);*/

@Data
public class InstructionRecorder {

    private int opcode;

    public InstructionRecorder(){
        opcode = Integer.parseInt(null);
    }
}
