package executor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

//Main Class
public class CPU {

    public static void main(String[] args) {

        try {
            FileReader arq = new FileReader("/Users/bela/student/PS/teste.txt");
            BufferedReader lerArq = new BufferedReader(arq);

            String linha = lerArq.readLine(); //lê a primeira linha
            while (linha != null) { //lê as próximas linhas
                System.out.printf("%s\n", linha);
                linha = lerArq.readLine();
            }
            arq.close();
        } catch (IOException e) {
            System.err.printf("Erro na abertura do arquivo: %s.\n",
                    e.getMessage());
        }

        String opcode = "0000000000000010";                                 // Vai vir em binário da leitura de arquivo. 16 bits-> 1 palavra de memória
        int opcodeDecimal = Integer.parseInt(opcode,2);               // Converte o binario para int
        System.out.println(opcodeDecimal);
        //String bin2 = Integer.toBinaryString(9);//Converter um valor int para binario e atribui o valor a um tipo string
        //System.out.println(bin2);

        switch (opcodeDecimal) {
            case 0:
                System.out.println("Você escolheu BR");                    // Chamar as funções
                break;
            case 1:
                System.out.println("Você escolheu BRPOS");
                break;
            case 2:
                // ADD
                System.out.println("Você escolheu ADD");
                break;
            case 3:
                System.out.println("Você escolheu LOAD");
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