package executor;

//import com.sun.xml.internal.bind.v2.runtime.output.StAXExStreamWriterOutput;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.SQLOutput;

//Main Class
public class CPU extends Application {

    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Simulador");
        primaryStage.setScene(new Scene(root, 1000, 900));
        primaryStage.show();
    }

    public static void main(String[] args) {
        Memory memory = new Memory();
        Operations operation = new Operations();

        int opd1=0, opd2=0;

        String instruction = "0000000000000011";                                             // Vai vir em binário da leitura de arquivo. 16 bits-> 1 palavra de memória

        String opcode = instruction.substring(0,4);                                          // Pega a substring do espaço 0 até 3
        int decimalOpcode = Integer.parseInt(opcode,2);                                // Converte o binario para int
        String operationCode = instruction.substring(4,7);                                         // Pega a substring do espaço 4 até 6
        int decimalOperationCode = Integer.parseInt(operationCode,2);                        // Converte o binario para int
        System.out.println("opcode convertido para decimal: ");
        System.out.println(decimalOpcode);
        String operand1 = instruction.substring(7,11);                                       // Pega a substring do espaço 8 até 15
        int decimalOperand1 = Integer.parseInt(operand1,2);                              // Converte o binario para int
        String operand2 = instruction.substring(12,16);                                       // Pega a substring do espaço 8 até 15
        int decimalOperand2 = Integer.parseInt(operand2,2);                              // Converte o binario para int
        //Tá sobrando um bit, será que é pro sinal? (são 9 bits pra 2 operandos, só da se dividir ao meio um bit)
        //String bin2 = Integer.toBinaryString(9);//Converter um valor int para binario e atribui o valor a um tipo string
        //System.out.println(bin2);

//        ir.setIR(decimalOpcode);                                                          //setta os valores decimais para os
//        opm.setOPM(decimalOperationCode);                                                //registradores
//
//        //Atribui o valor correto ao operando
//        //se for direto: pega o valor do operando da instrução em decimal e acessa o espaço de memória pra pegar o dado
//        //se for indireto: pega o valor do operando da instrução em decimal, acessa o espaço de memória e depois acessa o espaço
//        // de memória armanezado ali pra pegar o dado
//        //se for imediato: pega o valor do operando decimal como o próprio dado a ser usado
//        switch (opm.getOPM()) {
//            case 0:
//                //direto
//                opd1 = (Integer) memory.get_element(decimalOperand1);
//                break;
//            case 1:
//                //imediato
//                opd1 = opm.getOPM();
//                break;
//            case 2:
//                //copy
//                //segundo operador é indireto
//                //não sei como funciona o endereçamento de um copy
//                opd1 = (Integer) memory.get_element(decimalOperand1);
//                opd2 = (Integer) memory.get_element((Integer) memory.get_element(decimalOperand2));
//                break;
//            case 4:
//                //primeiro operando é indireto
//                opd1 = (Integer) memory.get_element((Integer) memory.get_element(decimalOperand1));
//                break;
//            case 6:
//                //copy
//                //primeiro e segundo operando são indiretos
//                //não sei como funciona endereçamento de um copy
//                opd1 = (Integer) memory.get_element((Integer) memory.get_element(decimalOperand1));
//                opd2 = (Integer) memory.get_element((Integer) memory.get_element(decimalOperand2));
//                break;
//            default:
//                System.out.println("Não está de acordo");
//        }
//
//
//
//        // OPERANDOS VEM DE ONDE???
//        //vem do código da instrução
//        //int opd1 = 4 /*os "9" últimos bits da instrução*/, opd2 = 2 /*esse eu não sei de onde veio exatamente*/ ;
//        //int testeDasInstrucoes = 14; // pra usar no switch case e nao precisar ficar convertendo binário
//
//        switch (ir.getIR()) {
//            case 0:
//                System.out.println("Você escolheu BR");                    // Chamar as funções
//                operation.br(pc, opd1);
//                // Essa instrução temos que conversar sobre como calcularemos o pulo
//                break;
//            case 1:
//                System.out.println("Você escolheu BRPOS");
//                operation.brPos(pc, acc, opd1);
//                break;
//            case 2:
//                // ADD
//                System.out.println("Você escolheu ADD");
//                operation.add(acc, opd1);                 // Raquel, basicamente é chamar a instrução aqui e printar o que ela deu set/get
//                System.out.println("Valor no acumulador: ");
//                System.out.println(acc.getACC());              // Aqui por exemplo add deu set no acc, entao aqui faço um get pra ver o que tem no meu acc
//                break;
//            case 3:
//                System.out.println("Você escolheu LOAD");
//                operation.load(acc, opd1);
//                System.out.println("Valor no acumulador: ");
//                System.out.println(acc.getACC());
//                break;
//            case 4:
//                System.out.println("Você escolheu BRZERO");
//                operation.brZero(pc, acc, opd1);
//                break;
//            case 5:
//                System.out.println("Você escolheu BRNEG");
//                operation.brNeg(pc, acc, opd1);
//                break;
//            case 6:
//                System.out.println("Você escolheu SUB");
//                operation.sub(acc, opd1);
//                break;
//            case 7:
//                System.out.println("Você escolheu STORE");
//                operation.store(acc, opd1, memory);
//                break;
//            case 8:
//                System.out.println("Você escolheu WRITE");
//                operation.write(opd1);
//                break;
//            case 9:
//                //vamo ter que dar mais uma olhada aqui também
//                System.out.println("Você escolheu RET");
//                operation.ret(pc, sp, memory);
//                break;
//            case 10:
//                System.out.println("Você escolheu DIVIDE");
//                operation.divide(acc, opd1);
//                break;
//            case 11:
//                System.out.println("Você escolheu STOP");
//                operation.stop(acc);
//                break;
//            case 12:
//                System.out.println("Você escolheu READ");
//                operation.read(opd1);
//                break;
//            case 13:
//                System.out.println("Você escolheu COPY");
//                opd1 = operation.copy(opd1, opd2);
//                break;
//            case 14:
//                System.out.println("Você escolheu MULT");
//                operation.mult(acc, opd1);
//                System.out.println(acc.getACC()); // vai dar 0 porque o acc tá 0
//                break;
//            case 15:
//                System.out.println("Você escolheu CALL");
//                operation.call(sp, pc, opd1, memory);
//                System.out.println(pc.get());
//                System.out.println(sp.pop()); // pc tá vazio entao tem que ser 0 o print
//                break;
//            default:                                                // Terá valor default?
//                System.out.println("Número inválido");
//        }
    }

}
