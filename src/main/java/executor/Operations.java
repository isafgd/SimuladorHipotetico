package executor;
import executor.recorders.Accumulator;
import executor.recorders.ProgramCounter;
import executor.recorders.StackPointer;
import javafx.collections.ObservableList;
import javafx.scene.control.TextArea;


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

    public void next(Memory memory, ObservableList<Registradores> list) {
        ProgramCounter PC = (ProgramCounter) memory.get(14);
        Integer count = PC.getPc();
        count++;
        PC.setPc(count);
        list.set(14, new Registradores("PC", PC.getPc().toString()));
    }

    public void add (Accumulator acc, int opd1, Integer addressMode, Memory memory, ObservableList<Registradores> list) {
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
        list.set(15,new Registradores("ACC", acc.getAcc().toString()));
        next(memory,list);
    }

    public void br (ProgramCounter pc, int opd1, Integer addressMode, Memory memory, ObservableList<Registradores> list) {
        if(addressMode==4){
            int pointer = (Integer) memory.get(opd1);
            pc.setPc((Integer) memory.get(pointer));
        }else {
            pc.setPc((Integer) memory.get(opd1));
        }
        list.set(14, new Registradores("PC", pc.getPc().toString()));
    }

    public void brPos (ProgramCounter pc, Accumulator acc, int opd1, Integer addressMode, Memory memory, ObservableList<Registradores> list) {
        if(acc.getAcc() > 0) {
            if(addressMode==4){
                int pointer = (Integer) memory.get(opd1);
                pc.setPc((Integer) memory.get(pointer));
            }else {
                pc.setPc((Integer) memory.get(opd1));
            }
        }
        list.set(14, new Registradores("PC", pc.getPc().toString()));
    }

    public void brZero (ProgramCounter pc, Accumulator acc, int opd1, Integer addressMode, Memory memory, ObservableList<Registradores> list) {
        if(acc.getAcc() == 0) {
            if(addressMode==4){
                int pointer = (Integer) memory.get(opd1);
                pc.setPc((Integer) memory.get(pointer));
            }else {
                pc.setPc((Integer) memory.get(opd1));
            }
        }
        list.set(14, new Registradores("PC", pc.getPc().toString()));
    }

    public void brNeg (ProgramCounter pc, Accumulator acc, int opd1, Integer addressMode, Memory memory, ObservableList<Registradores> list) {
        if(acc.getAcc() < 0) {
            if(addressMode==4){
                int pointer = (Integer) memory.get(opd1);
                pc.setPc((Integer) memory.get(pointer));
            }else {
                pc.setPc((Integer) memory.get(opd1));
            }
        }
        list.set(14, new Registradores("PC", pc.getPc().toString()));
    }

    public void copy (int opd1, int opd2, Integer addressMode, Memory memory, ObservableList<Registradores> list) {
        if(addressMode == 1){
            //Operando 1 é DIRETO e Operando 2 é IMEDIATO
            memory.set_element((Integer) memory.get(opd1),opd2);
        }else {
            if(addressMode == 2){
                //Operando 1 é DIRETO e Operando 2 é INDIRETO
                memory.set_element((Integer) memory.get(opd1), (Integer) memory.get((Integer) memory.get(opd2)));
            }else{
                if(addressMode == 4){
                    //Operando 1 é INDIRETO e Operando 2 é DIRETO
                    memory.set_element((Integer) memory.get((Integer) memory.get(opd1)), (Integer) memory.get(opd2));
                }else{
                    if(addressMode == 5){
                        //Operando 1 é INDIRETO e Operando 2 é IMEDIATO
                        memory.set_element((Integer) memory.get((Integer) memory.get(opd1)), opd2);
                    }else{
                        if(addressMode == 6){
                            //Operando 1 é INDIRETO e Operando 2 é INDIRETO
                            memory.set_element((Integer) memory.get((Integer) memory.get(opd1)), (Integer) memory.get((Integer) memory.get(opd2)));
                        }else {
                            //Operando 1 é DIRETO e Operando 2 é DIRETO
                            memory.set_element((Integer) memory.get(opd1),(Integer) memory.get(opd2));
                        }
                    }
                }
            }
        }
        next(memory,list);
    }

    public void divide (Accumulator acc, int opd1, Integer addressMode, Memory memory, ObservableList<Registradores> list) {
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
        list.set(15,new Registradores("ACC", acc.getAcc().toString()));
        next(memory,list);
    }

    public void load (Accumulator acc, int opd1, Integer addressMode, Memory memory, ObservableList<Registradores> list) {
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
        list.set(15,new Registradores("ACC", acc.getAcc().toString()));
        next(memory,list);
    }

    public void multi (Accumulator acc, int opd1, Integer addressMode, Memory memory, ObservableList<Registradores> list) {
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
        list.set(15,new Registradores("ACC", acc.getAcc().toString()));
        next(memory,list);
    }

    public void read (Integer addressMode, int opd1, Memory memory, ObservableList<Registradores> list) {
        if(addressMode==4){
            int pointer = (Integer) memory.get(opd1);
            memory.set_element(19, (Integer) memory.get(pointer));
        }else {
            memory.set_element(19, (Integer) memory.get(opd1));
        }
        next(memory,list);
    }

    public void stop (){
        exit(0);
    }

    public void store (Accumulator acc, int opd1, Memory memory, Integer addressMode, ObservableList<Registradores> list) {
        if(addressMode==4){
            int pointer = (Integer) memory.get(acc.getAcc());
            memory.set_element(19, (Integer) memory.get(pointer));
        }else {
            memory.set_element(19, (Integer) memory.get(acc.getAcc()));
        }
        next(memory,list);
    }

    public void sub (Accumulator acc, int opd1, Integer addressMode, Memory memory, ObservableList<Registradores> list) {
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
        list.set(15,new Registradores("ACC", acc.getAcc().toString()));
        next(memory,list);
    }

    public void write (int opd1, Integer addressMode, Memory memory, ObservableList<Registradores> list, TextArea console) {
        if (addressMode==1) {
            System.out.println(opd1);
            console.setText(String.valueOf(opd1));
        }else{
            if(addressMode==4){
                int pointer = (Integer) memory.get(opd1);
                console.setText(String.valueOf(memory.get(pointer)));
            }else{
                console.setText(String.valueOf(memory.get(opd1)));
            }
        }
        next(memory,list);
    }

    public void call (StackPointer sp, ProgramCounter pc, int opd1, Memory memory, Integer addressMode, ObservableList<Registradores> list) {
        if(addressMode==4){
            int pointer = (Integer) memory.get(opd1);
            pc.setPc((Integer) memory.get(pointer));
        }else{
            pc.setPc((Integer) memory.get(opd1));
        }
        memory.push(sp, pc.getPc());
    }

    public void ret (ProgramCounter pc, StackPointer sp, Memory memory, ObservableList<Registradores> list) {
        pc.setPc(memory.pop(sp));
    }

}
