package executor;

import com.sun.xml.internal.bind.v2.runtime.output.StAXExStreamWriterOutput;
import executor.recorders.Accumulator;
import executor.recorders.ProgramCounter;
import executor.recorders.StackPointer;

import java.sql.SQLOutput;

//Main Class
public class CPU {

    public static void main(String[] args) {
        // Depois de testar as instruções, mudar no switch de testeDasInstrucoes para opcodeDecimal!!!

        String opcode = "0000000000000011";                                 // Vai vir em binário da leitura de arquivo. 16 bits-> 1 palavra de memória
        int opcodeDecimal = Integer.parseInt(opcode,2);               // Converte o binario para int
        System.out.println("opcode convertido para decimal: ");
        System.out.println(opcodeDecimal);
        //String bin2 = Integer.toBinaryString(9);//Converter um valor int para binario e atribui o valor a um tipo string
        //System.out.println(bin2);

        Instructions instruction = new Instructions();
        Accumulator acc = new Accumulator();
        ProgramCounter pc = new ProgramCounter();
        StackPointer sp = new StackPointer();

        // OPERANDOS VEM DE ONDE???
        int opd1 = 11;
        int testeDasInstrucoes = 0; // pra usar no switch case e nao precisar ficar convertendo binário

        switch (testeDasInstrucoes) {
            case 0:
                System.out.println("Você escolheu BR");                    // Chamar as funções
                instruction.br(pc, opd1);
                // Essa instrução temos que conversar sobre como calcularemos o pulo
                break;
            case 1:
                System.out.println("Você escolheu BRPOS");
                break;
            case 2:
                // ADD
                System.out.println("Você escolheu ADD");
                instruction.add(acc, opd1);                 // Raquel, basicamente é chamar a instrução aqui e printar o que ela deu set/get
                System.out.println("Valor no acumulador: ");
                System.out.println(acc.getACC());              // Aqui por exemplo add deu set no acc, entao aqui faço um get pra ver o que tem no meu acc
                break;
            case 3:
                System.out.println("Você escolheu LOAD");
                instruction.load(acc, opd1);
                System.out.println("Valor no acumulador: ");
                System.out.println(acc.getACC());
                break;
            case 4:
                System.out.println("Você escolheu BRZERO");
                break;
            case 5:
                System.out.println("Você escolheu BRNEG");
                break;
            case 6:
                System.out.println("Você escolheu SUB");
                break;
            case 7:
                System.out.println("Você escolheu STORE");
                break;
            case 8:
                System.out.println("Você escolheu WRITE");
                break;
            case 9:
                System.out.println("Você escolheu RET");
                break;
            case 10:
                System.out.println("Você escolheu DIVIDE");
                break;
            case 11:
                System.out.println("Você escolheu STOP");
                break;
            case 12:
                System.out.println("Você escolheu READ");
                break;
            case 13:
                System.out.println("Você escolheu COPY");
                break;
            case 14:
                System.out.println("Você escolheu MULT");
                break;
            case 15:
                System.out.println("Você escolheu CALL");
                break;
            default:                                                // Terá valor default?
                System.out.println("Número inválido");
        }
    }
}
