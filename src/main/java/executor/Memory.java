package executor;

import executor.recorders.*;
import javafx.collections.ObservableList;
import javafx.scene.control.TextArea;

import java.util.ArrayList;

public class Memory {

    ArrayList<Object> memory = new ArrayList<Object>(500);

    private final int max_size = 500; //Cada posição corresponde à uma palavra de 16bits - No total a memória terá 1KB

    MemoryList memoryList;

    public Memory (ObservableList<MemoryList> list){ //Inicializa toda a memória
       for (Integer i = 0;i<500;i++){
           memory.add(null); //Preenche a pilha com NULL
           list.add(i,new MemoryList(i.toString(),"0"));
        }

        memory.set(2,10); //Cria a base da pilha, na posição de memória 2, nele está salvo o tamanho máximo da pilha
        list.set(2,new MemoryList("2","10"));

        StackPointer SP = new StackPointer(); //Inicializa o registrador com 0
        ProgramCounter PC = new ProgramCounter();
        Accumulator ACC = new Accumulator();
        OperationMode MOP = new OperationMode();
        AddressRecorder RE = new AddressRecorder();
        InstructionRecorder RI = new InstructionRecorder();

        memory.set(13,SP); //Cria o registrador SP na posição 13 da memória
        list.set(13,new MemoryList("SP", SP.getPointer().toString()));

        memory.set(14,PC);
        list.set(14,new MemoryList("PC", PC.getPc().toString()));

        memory.set(15,ACC);
        list.set(15,new MemoryList("ACC", ACC.getAcc().toString()));

        memory.set(16,MOP);
        list.set(16,new MemoryList("MOP",MOP.getMop().toString()));

        memory.set(17,RI);
        list.set(17,new MemoryList("RI", RI.getRi().toString()));

        memory.set(18,RE);
        list.set(18,new MemoryList("RE", RE.getRe().toString()));
    }

    public void set_element (int index, Integer element){
        memory.set(index,element);
    }

    public void set_string (int index, String element){
        memory.set(index,element);
    }

    public Object get(int index) {
        return memory.get(index);
    }

    public void push (StackPointer SP, int element, TextArea console){
        if (SP.getPointer() == 0 && memory.get(3) == null){ //Pilha está vazia
            SP.setPointer(4);
            memory.set(3,element);
        }else{
            if(SP.getPointer() == 13 || SP.getPointer() == 0){
                SP.setPointer(0);
                console.setText("Stack Overflow");
            }else{
                memory.set(SP.getPointer(),element);
                int index = SP.getPointer();
                SP.setPointer(index++);
            }
        }
    }

    public int pop (StackPointer SP, TextArea console){
        if (SP.getPointer() == 0 && memory.get(3) == null || SP.getPointer() == 2){
            console.setText("Empty Stack");
        }else{
            if (SP.getPointer() == 0){ //Veio de um Stack Overflow (Desempilha do topo)
                memory.set(12,null);
                SP.setPointer(11);
            }else{
                memory.set(SP.getPointer(),null);
                int index = SP.getPointer();
                SP.setPointer(index--);
            }
        }
        return SP.getPointer();
    }
}