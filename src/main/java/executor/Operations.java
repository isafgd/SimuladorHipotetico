package executor;
import executor.recorders.Accumulator;
import executor.recorders.AddressRecorder;
import executor.recorders.ProgramCounter;
import executor.recorders.StackPointer;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.control.TextArea;

import static java.lang.System.exit;

/*
                               address
String instruction = "[00000000[000][00000]][0000000000000000][0000000000000000]"
                                     opcode        opd1               opd2
*/

/*AdressMode {
    4: Operando 1 ou 2 é Imediato
    2: Operando 1 é Direto e Operando 2 é Indireto
    1: Operando 1 é Indireto e Operando 2 é Direto
    5: Operando 1 é Indireto e Operando 2 é Imediato
    3: Operando 1 e 2 são Indiretos
    Default: Direto
}*/

/*
Classe que contem cada operacao
*/

public class Operations {

    //Incrementa o PC e o RE
    public void next(Memory memory, ObservableList<MemoryList> list) {
        ProgramCounter PC = (ProgramCounter) memory.get(14);
        PC.setPc(PC.getPc() + 1);
        list.set(14, new MemoryList("PC", PC.getPc().toString()));
        AddressRecorder RE = (AddressRecorder) memory.get(18);
        RE.setRe(RE.getRe() + 1);
        list.set(18, new MemoryList("RE", RE.getRe().toString()));
    }

    public void add (Accumulator acc, int opd1, Integer addressMode, Memory memory, ObservableList<MemoryList> list) {
        if (addressMode==4) {
            acc.setAcc(acc.getAcc() + opd1);
        }else{
            if(addressMode==1){
                int pointer = (Integer) memory.get(opd1);
                acc.setAcc(acc.getAcc() + (Integer) memory.get(pointer));
            }else{
                acc.setAcc(acc.getAcc() + (Integer) memory.get(opd1));
            }
        }
        list.set(15,new MemoryList("ACC", acc.getAcc().toString()));
        next(memory,list);
    }

    public void br (ProgramCounter pc, int opd1, Integer addressMode, Memory memory, ObservableList<MemoryList> list) {
        if(addressMode==1){
            int pointer = (Integer) memory.get(opd1);
            pc.setPc((Integer) memory.get(pointer));
        }else {
            pc.setPc((Integer) memory.get(opd1));
        }
        list.set(14, new MemoryList("PC", pc.getPc().toString()));
    }

    public void brPos (ProgramCounter pc, Accumulator acc, int opd1, Integer addressMode, Memory memory, ObservableList<MemoryList> list) {
        if(acc.getAcc() > 0) {
            if(addressMode==1){
                int pointer = (Integer) memory.get(opd1);
                pc.setPc((Integer) memory.get(pointer));
            }else {
                pc.setPc((Integer) memory.get(opd1));
            }
            list.set(14, new MemoryList("PC", pc.getPc().toString()));
        }else{
            next(memory,list);
        }
    }

    public void brZero (ProgramCounter pc, Accumulator acc, int opd1, Integer addressMode, Memory memory, ObservableList<MemoryList> list) {
        if(acc.getAcc() == 0) {
            if(addressMode==1){
                int pointer = (Integer) memory.get(opd1);
                pc.setPc((Integer) memory.get(pointer));
            }else {
                pc.setPc((Integer) memory.get(opd1));
            }
            list.set(14, new MemoryList("PC", pc.getPc().toString()));
        }else{
            next(memory,list);
        }
    }

    public void brNeg (ProgramCounter pc, Accumulator acc, int opd1, Integer addressMode, Memory memory, ObservableList<MemoryList> list) {
        if(acc.getAcc() < 0) {
            if(addressMode==1){
                int pointer = (Integer) memory.get(opd1);
                pc.setPc((Integer) memory.get(pointer));
            }else {
                pc.setPc((Integer) memory.get(opd1));
            }
            list.set(14, new MemoryList("PC", pc.getPc().toString()));
        }else{
            next(memory,list);
        }
    }

    public void copy (int opd1, Integer opd2, Integer addressMode, Memory memory, ObservableList<MemoryList> list) {
        if(addressMode == 4){
            //Operando 1 é DIRETO e Operando 2 é IMEDIATO
            memory.set_element((Integer) memory.get(opd1),opd2);
            list.set((Integer) memory.get(opd1),new MemoryList(memory.get(opd1).toString(), opd2.toString()));
        }else {
            if(addressMode == 2){
                //Operando 1 é DIRETO e Operando 2 é INDIRETO
                memory.set_element((Integer) memory.get(opd1), (Integer) memory.get((Integer) memory.get(opd2)));
                list.set((Integer) memory.get(opd1),new MemoryList(memory.get(opd1).toString(), memory.get((Integer) memory.get(opd2)).toString()));
            }else{
                if(addressMode == 1){
                    //Operando 1 é INDIRETO e Operando 2 é DIRETO
                    memory.set_element((Integer) memory.get((Integer) memory.get(opd1)), (Integer) memory.get(opd2));
                    list.set((Integer) memory.get((Integer) memory.get(opd1)),new MemoryList(memory.get((Integer) memory.get(opd1)).toString(),memory.get(opd2).toString()));
                }else{
                    if(addressMode == 5){
                        //Operando 1 é INDIRETO e Operando 2 é IMEDIATO
                        memory.set_element((Integer) memory.get((Integer) memory.get(opd1)), opd2);
                        list.set((Integer) memory.get((Integer) memory.get(opd1)),new MemoryList(memory.get((Integer) memory.get(opd1)).toString(), opd2.toString()));
                    }else{
                        if(addressMode == 3){
                            //Operando 1 é INDIRETO e Operando 2 é INDIRETO
                            memory.set_element((Integer) memory.get((Integer) memory.get(opd1)), (Integer) memory.get((Integer) memory.get(opd2)));
                            list.set((Integer) memory.get((Integer) memory.get(opd1)),new MemoryList(memory.get((Integer) memory.get(opd1)).toString(), memory.get((Integer) memory.get(opd2)).toString()));
                        }else {
                            //Operando 1 é DIRETO e Operando 2 é DIRETO
                            memory.set_element((Integer) memory.get(opd1),(Integer) memory.get(opd2));
                            list.set((Integer) memory.get(opd1),new MemoryList(memory.get(opd1).toString(),  memory.get(opd2).toString()));
                        }
                    }
                }
            }
        }
        next(memory,list);
    }

    public void divide (Accumulator acc, int opd1, Integer addressMode, Memory memory, ObservableList<MemoryList> list) {
        if (addressMode==4) {
            acc.setAcc(acc.getAcc() / opd1);
        }else{
            if(addressMode==1){
                int pointer = (Integer) memory.get(opd1);
                acc.setAcc(acc.getAcc() / (Integer) memory.get(pointer));
            }else{
                acc.setAcc(acc.getAcc() / (Integer) memory.get(opd1));
            }
        }
        list.set(15,new MemoryList("ACC", acc.getAcc().toString()));
        next(memory,list);
    }

    public void load (Accumulator acc, int opd1, Integer addressMode, Memory memory, ObservableList<MemoryList> list) {
        if (addressMode==4) {
            acc.setAcc(opd1);
        }else{
            if(addressMode==1){
                int pointer = (Integer) memory.get(opd1);
                acc.setAcc((Integer) memory.get(pointer));
            }else{
                acc.setAcc((Integer) memory.get(opd1));
            }
        }
        list.set(15,new MemoryList("ACC", acc.getAcc().toString()));
        next(memory,list);
    }

    public void multi (Accumulator acc, int opd1, Integer addressMode, Memory memory, ObservableList<MemoryList> list) {
        if (addressMode==4) {
            acc.setAcc(acc.getAcc() * opd1);
        }else{
            if(addressMode==1){
                int pointer = (Integer) memory.get(opd1);
                acc.setAcc(acc.getAcc() * (Integer) memory.get(pointer));
            }else{
                acc.setAcc(acc.getAcc() * (Integer) memory.get(opd1));
            }
        }
        list.set(15,new MemoryList("ACC", acc.getAcc().toString()));
        next(memory,list);
    }

    public void read (Integer addressMode, int opd1, Memory memory, ObservableList<MemoryList> list, String input) {
        if(addressMode==1){
            int pointer = (Integer) memory.get(opd1);
            memory.set_string((Integer) memory.get(pointer), input);
            list.set((Integer) memory.get(pointer),new MemoryList(memory.get(pointer).toString(), input));
        }else {
            memory.set_string((Integer) memory.get(opd1), input);
            list.set((Integer) memory.get(opd1),new MemoryList(memory.get(opd1).toString(),input));
        }
        next(memory,list);
    }

    public void stop (){
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    Thread.sleep(10000);
                } catch (Exception e) {
                }
                exit(0);
                return null;
            }
        };
        new Thread(task).start();
    }

    public void store (Accumulator acc, Integer opd1, Memory memory, Integer addressMode, ObservableList<MemoryList> list) {
        if(addressMode==1){
            memory.set_element((Integer) memory.get(opd1), acc.getAcc());
            list.set((Integer) memory.get(opd1),new MemoryList(memory.get(opd1).toString(), acc.getAcc().toString()));
        }else {
            memory.set_element(opd1, acc.getAcc());
            list.set(opd1,new MemoryList(opd1.toString(), acc.getAcc().toString()));
        }
        next(memory,list);
    }

    public void sub (Accumulator acc, int opd1, Integer addressMode, Memory memory, ObservableList<MemoryList> list) {
        if (addressMode==4) {
            acc.setAcc(acc.getAcc() - opd1);
        }else{
            if(addressMode==1){
                int pointer = (Integer) memory.get(opd1);
                acc.setAcc(acc.getAcc() - (Integer) memory.get(pointer));
            }else{
                acc.setAcc(acc.getAcc() - (Integer) memory.get(opd1));
            }
        }
        list.set(15,new MemoryList("ACC", acc.getAcc().toString()));
        next(memory,list);
    }

    public void write (int opd1, Integer addressMode, Memory memory, ObservableList<MemoryList> list, TextArea console) {
        if (addressMode==4) {
            System.out.println(opd1);
            console.setText(String.valueOf(opd1));
        }else{
            if(addressMode==1){
                int pointer = (Integer) memory.get(opd1);
                console.setText(String.valueOf(memory.get(pointer)));
            }else{
                console.setText(String.valueOf(memory.get(opd1)));
            }
        }
        next(memory,list);
    }

    public void call (StackPointer sp, ProgramCounter pc, int opd1, Memory memory, Integer addressMode, ObservableList<MemoryList> list, TextArea console) {
        int back = pc.getPc();
        if(addressMode==1){
            int pointer = (Integer) memory.get(opd1);
            pc.setPc((Integer) memory.get(pointer));
        }else{
            pc.setPc((Integer) memory.get(opd1));
        }
        memory.push(sp, pc.getPc(), console, list);
        list.set(14, new MemoryList("PC", pc.getPc().toString()));
        back++;
        pc.setPc(back);
        list.set(14, new MemoryList("PC", pc.getPc().toString()));
    }

    public void ret (ProgramCounter pc, StackPointer sp, Memory memory, ObservableList<MemoryList> list, TextArea console) {
        int back = pc.getPc();
        pc.setPc(memory.pop(sp,console, list));
        list.set(14, new MemoryList("PC", pc.getPc().toString()));
        back++;
        pc.setPc(back);
        list.set(14, new MemoryList("PC", pc.getPc().toString()));
    }

}
