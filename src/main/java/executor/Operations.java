package executor;
import executor.recorders.Accumulator;
import executor.recorders.ProgramCounter;
import executor.recorders.StackPointer;


import java.util.*;

import static java.lang.System.exit;

/*AdressMode {
    1: Operando 1 ou 2 é Imediato
    2: Operando 1 é Direto e Operando 2 é Indireto
    4: Operando 1 é Indireto e Operando 2 é Direto
    5: Operando 1 é Indireto e Operando 2 é Imediato
    6: Operando 1 e 2 são Indiretos
    Default: Direto
}*/

public class Operations {

    public void add (Accumulator acc, int opd1, Integer addressMode, Memory memory) {
        if (addressMode==1) {
            acc.setAcc(acc.getAcc() + opd1);
        }else{
            if(addressMode==4){
                int pointer = (Integer) memory.get(opd1);
                acc.setAcc(acc.getAcc() + (Integer) memory.get(pointer));
            }else{
                acc.setAcc(acc.getAcc() + (Integer) memory.get(opd1));
            }
        }
    }

/*    public void addi (ProgramCounter pc, Accumulator acc, int opd1, Integer addressMode, Memory memory) { // PC guarda o endereço da prox instrução a ser executada
        int result = pc.getPc() + opd1;
        pc.setPc(result);
        //return pc + opd1;
    }*/

    public void br (ProgramCounter pc, int opd1, Integer addressMode, Memory memory) {
        if(addressMode==4){
            int pointer = (Integer) memory.get(opd1);
            pc.setPc((Integer) memory.get(pointer));
        }else {
            pc.setPc((Integer) memory.get(opd1));
        }
    }

    public void brPos (ProgramCounter pc, Accumulator acc, int opd1, Integer addressMode, Memory memory) {
        if(acc.getAcc() > 0) {
            if(addressMode==4){
                int pointer = (Integer) memory.get(opd1);
                pc.setPc((Integer) memory.get(pointer));
            }else {
                pc.setPc((Integer) memory.get(opd1));
            }
        }
    }

    public void brZero (ProgramCounter pc, Accumulator acc, int opd1, Integer addressMode, Memory memory) {
        if(acc.getAcc() == 0) {
            if(addressMode==4){
                int pointer = (Integer) memory.get(opd1);
                pc.setPc((Integer) memory.get(pointer));
            }else {
                pc.setPc((Integer) memory.get(opd1));
            }
        }
    }

    public void brNeg (ProgramCounter pc, Accumulator acc, int opd1, Integer addressMode, Memory memory) {
        if(acc.getAcc() < 0) {
            if(addressMode==4){
                int pointer = (Integer) memory.get(opd1);
                pc.setPc((Integer) memory.get(pointer));
            }else {
                pc.setPc((Integer) memory.get(opd1));
            }
        }
    }

/*    public int copy (int opd1, int opd2, Integer addressMode, Memory memory) {
        return opd1 = opd2;
    }*/

    public void divide (Accumulator acc, int opd1, Integer addressMode, Memory memory) {
        if (addressMode==1) {
            acc.setAcc(acc.getAcc() / opd1);
        }else{
            if(addressMode==4){
                int pointer = (Integer) memory.get(opd1);
                acc.setAcc(acc.getAcc() / (Integer) memory.get(pointer));
            }else{
                acc.setAcc(acc.getAcc() / (Integer) memory.get(opd1));
            }
        }
    }

    public void load (Accumulator acc, int opd1, Integer addressMode, Memory memory) {
        if (addressMode==1) {
            acc.setAcc(opd1);
        }else{
            if(addressMode==4){
                int pointer = (Integer) memory.get(opd1);
                acc.setAcc((Integer) memory.get(pointer));
            }else{
                acc.setAcc((Integer) memory.get(opd1));
            }
        }
    }

    public void multi (Accumulator acc, int opd1, Integer addressMode, Memory memory) {
        if (addressMode==1) {
            acc.setAcc(acc.getAcc() * opd1);
        }else{
            if(addressMode==4){
                int pointer = (Integer) memory.get(opd1);
                acc.setAcc(acc.getAcc() * (Integer) memory.get(pointer));
            }else{
                acc.setAcc(acc.getAcc() * (Integer) memory.get(opd1));
            }
        }
    }

/*    public Scanner read (int opd1, Integer addressMode, Memory memory) {
        Scanner userInput = new Scanner(System.in);
        return userInput;
    }*/

    public void stop () {
        exit(0);
    }

    public void store (Accumulator acc, int opd1, Memory memory, Integer addressMode) {
        if(addressMode==4){
            int pointer = (Integer) memory.get(acc.getAcc());
            memory.set_element(19, (Integer) memory.get(pointer));
        }else {
            memory.set_element(19, (Integer) memory.get(acc.getAcc()));
        }
    }

    public void sub (Accumulator acc, int opd1, Integer addressMode, Memory memory) {
        if (addressMode==1) {
            acc.setAcc(acc.getAcc() - opd1);
        }else{
            if(addressMode==4){
                int pointer = (Integer) memory.get(opd1);
                acc.setAcc(acc.getAcc() - (Integer) memory.get(pointer));
            }else{
                acc.setAcc(acc.getAcc() - (Integer) memory.get(opd1));
            }
        }
    }

    public void write (int opd1, Integer addressMode, Memory memory) {
        if (addressMode==1) {
            System.out.println(opd1);
        }else{
            if(addressMode==4){
                int pointer = (Integer) memory.get(opd1);
                System.out.println(memory.get(pointer));
            }else{
                System.out.println(memory.get(opd1));
            }
        }
    }

    public void call (StackPointer sp, ProgramCounter pc, int opd1, Memory memory, Integer addressMode) {
        if(addressMode==4){
            int pointer = (Integer) memory.get(opd1);
            pc.setPc((Integer) memory.get(pointer));
        }else{
            pc.setPc((Integer) memory.get(opd1));
        }
        memory.push(sp, pc.getPc());
    }

    public void ret (ProgramCounter pc, StackPointer sp, Memory memory) {
        pc.setPc(memory.pop(sp));
    }

}
