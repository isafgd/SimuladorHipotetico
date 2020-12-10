package executor;

import executor.recorders.*;
import javafx.collections.ObservableList;
import javafx.scene.control.TextArea;

import java.util.ArrayList;

public class Memory {

    ArrayList<Object> memory = new ArrayList<Object>(500);

    private final int max_size = 500; //Cada posição corresponde à uma palavra de 16bits - No total a memória terá 1KB

    MemoryList memoryList;

    /*Construtor da memoria*/
    public Memory (ObservableList<MemoryList> list){ //Inicializa toda a memória
       for (Integer i = 0;i<500;i++){
           memory.add(null); //Preenche a pilha com NULL
           list.add(i,new MemoryList(i.toString(),"0"));
        }

        memory.set(2,10); //Cria a base da pilha, na posição de memória 2, nele está salvo o tamanho máximo da pilha
        list.set(2,new MemoryList("2","10"));

        StackPointer SP = new StackPointer();
        ProgramCounter PC = new ProgramCounter();
        Accumulator ACC = new Accumulator();
        OperationMode MOP = new OperationMode();
        AddressRecorder RE = new AddressRecorder();
        InstructionRecorder RI = new InstructionRecorder();

        memory.set(13,SP);
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

    /*Funcao da pilha*/
    public void push (StackPointer SP, Integer element, TextArea console, ObservableList<MemoryList> list){
        if (SP.getPointer() == 0 && memory.get(3) == null){ //Pilha está vazia
            memory.set(3,element);
            list.set(3,new MemoryList("3", element.toString()));
            SP.setPointer(4);
            list.set(13,new MemoryList("SP", SP.getPointer().toString()));
        }else{
            if(SP.getPointer() == 13 || SP.getPointer() == 0){
                SP.setPointer(0);
                list.set(13,new MemoryList("SP", SP.getPointer().toString()));
                console.setText("Stack Overflow");
            }else{
                memory.set(SP.getPointer(),element);
                list.set(SP.getPointer(),new MemoryList(SP.getPointer().toString(), element.toString()));
                int index = SP.getPointer();
                index++;
                SP.setPointer(index);
                list.set(13,new MemoryList("SP", SP.getPointer().toString()));
            }
        }
    }

    /*Funcao da pilha*/
    public int pop (StackPointer SP, TextArea console, ObservableList<MemoryList> list){
        if (SP.getPointer() == 0 && memory.get(3) == null || SP.getPointer() == 2){
            console.setText("Empty Stack");
        }else{
            if (SP.getPointer() == 0 || SP.getPointer() == 13){ //Veio de um Stack Overflow (Desempilha do topo)
                memory.set(12,null);
                list.set(12,new MemoryList("12", "0"));
                SP.setPointer(11);
                list.set(13,new MemoryList("SP", SP.getPointer().toString()));
            }else{
                memory.set(SP.getPointer(),null);
                list.set(SP.getPointer(),new MemoryList(SP.getPointer().toString(), "0"));
                int index = SP.getPointer();
                index--;
                SP.setPointer(index);
                list.set(13,new MemoryList("SP", SP.getPointer().toString()));
            }
        }
        return SP.getPointer();
    }
}