package executor;
import executor.recorders.Accumulator;
import executor.recorders.ProgramCounter;
import executor.recorders.StackPointer;
import executor.Memory;

import java.util.*;

public class Operations {

    //executa uma soma entre o acumulador e o valor informado pelo operando
    //e devolve o valor desse somatório
    public  void add (Accumulator acc, int opd1) {
        int ac = acc.getAcc() + opd1;
        acc.setAcc(ac);
        //return acc += opd1;
    }

    public  void addi (ProgramCounter pc, Accumulator acc, int opd1) { // PC guarda o endereço da prox instrução a ser executada
        int result = pc.getPc() + opd1;
        pc.setPc(result);
        //return pc + opd1;
    }



    //faz um pulo no programa para o lugar do valor indicado pelo operando
    public void br (ProgramCounter pc, int opd1) {                //eu acho que aqui a gente vai ter calcular o lugar onde vai ser o pulo
        pc.setPc(opd1);
       // return pc = opd1;
    }

    //executa o pulo para o valor informado no operador
    //se o valor do acumulador for positivo/maior que zero
    public void brPos (ProgramCounter pc, Accumulator acc, int opd1) {    //eu acho que aqui a gente vai ter calcular o lugar onde vai ser o pulo
        if(acc.getAcc() > 0) {
            pc.setPc(opd1);
            //return opd1;
        }
    }

    //faz o pulo para o valor informado no operador
    //se o valor do acumulador foi igual a 0
    public void brZero (ProgramCounter pc, Accumulator acc, int opd1) {    //eu acho que aqui a gente vai ter calcular o lugar onde vai ser o pulo
        if(acc.getAcc() == 0) {
            pc.setPc(opd1);
        }
    }

    public void brNeg (ProgramCounter pc, Accumulator acc, int opd1) {                //eu acho que aqui a gente vai ter calcular o lugar onde vai ser o pulo
        if(acc.getAcc() < 0) {
            pc.setPc(opd1);
        }
    }

    //copia a informação dentro do operador 1 para dentro do operador 2
    //e retorna o valor dentro do operador 2
    public int copy (int opd1, int opd2) {
        return opd1 = opd2;
    }

    //faz a divisão do valor dentro do acumulador pelo valor do operando informado
    //o valor é armazenado novamente dentro do acumulador
    public void divide (Accumulator acc, int opd1) {
        acc.setAcc(acc.getAcc()/opd1);
    }

    public void load (Accumulator acc, int opd1) {
        acc.setAcc(opd1);
    }

    public void mult (Accumulator acc, int opd1) {
        acc.setAcc(acc.getAcc() * opd1);
    }

    public Scanner read (int opd1) {                     //COMO SE FAZ LEITURA EM JAVA SOCORRO
        Scanner userInput = new Scanner(System.in);      //vamos ter que pensar nisso
        return userInput;
    }

    public void stop (Accumulator acc) {
        return ;
        //como mata o programa?????
    }

    public void store (Accumulator acc, int opd1, Memory memory) {
        //direto
        memory.set_element(opd1, acc.getAcc());
        //indireto
    }

    public void sub (Accumulator acc, int opd1) {
        acc.setAcc(acc.getAcc() - opd1);
    }

    public void write (int opd1) {
        System.out.println(opd1);
    }

    public void call (StackPointer sp, ProgramCounter pc, int opd1, Memory memory) {
        memory.push(sp, pc.getPc());
        pc.setPc(opd1);
    }

    public void ret (ProgramCounter pc, StackPointer sp, Memory memory) { memory.pop(sp);}

}
