package executor;
import java.util.*;

public class Instructions {

    //executa uma soma entre o acumulador e o valor informado pelo operando
    //e devolve o valor desse somatório
    public  int add (int acc, int opd1) {
        return acc += opd1;
    }
    public  int addi (int pc, int acc, int opd1) {
        return pc + opd1;
    }

    //faz um pulo no prugrama para o lugar do valor indicado pelo operando
    public int br (int pc, int opd1) {                //eu acho que aqui a gente vai ter calcular o lugar onde vai ser o pulo
        return opd1;
    }

    //executa o pulo para o valor informado no operador
    //se o valor do acumulador for positivo/maior que zero
    public int brpos (int pc, int acc, int opd1) {    //eu acho que aqui a gente vai ter calcular o lugar onde vai ser o pulo
        if(acc > 0) {
            return opd1;
        }
        else return pc;
    }

    //faz o pulo para o valor informado no operador
    //se o valor do acumulador foi igual a 0
    public int brzero (int pc, int acc, int opd1) {    //eu acho que aqui a gente vai ter calcular o lugar onde vai ser o pulo
        if(acc == 0) {
            return opd1;
        }
        else return pc;
    }

    //copia a informação dentro do operador 1 para dentro do operador 2
    //e retorna o valor dentro do operador 2
    public int copy (int opd1, int opd2) {
        return opd1;
    }

    //faz a divisão do valor dentro do acumulador pelo valor do operando informado
    //o valor é armazenado novamente dentro do acumulador
    public int divide (int acc, int opd1) {
        acc = acc/opd1;
        return acc;
    }

    public int load (int acc, int opd1) {
        return opd1;
    }

    public int mult (int acc, int opd1) {
        acc = acc * opd1;
        return acc;
    }

    public Scanner read (int opd1) {                     //COMO SE FAZ LEITURA EM JAVA SOCORRO
        Scanner userInput = new Scanner(System.in);      //vamos ter que pensar nisso
        return userInput;
    }

    public void stop (int acc) {
        //como mata o programa?????
    }

    public int store (int acc, int opd1) {
        return acc;
    }

    public int sub (int acc, int opd1) {
        return acc -=opd1;
    }

    public void write (int opd1) {
        System.out.println(opd1);
    }

    public void call (int sp, int pc, int opd1) {
        //não sei o que isso faz GIU HELP
    }

    public void ret (int pc, int sp) {
        //também não sei o que isso faz HELP
    }

}