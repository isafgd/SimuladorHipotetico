package executor;

import executor.recorders.*;
import java.util.ArrayList;

public class Memory {

    ArrayList<Object> memory = new ArrayList<Object>(500);

    private final int max_size = 500; //Cada posição corresponde à uma palavra de 16bits - No total a memória terá 1KB

    public Memory (){ //Inicializa toda a memória
       for (int i = 0;i<500;i++){
           memory.add(null); //Preenche a pilha com NULL
        }

        memory.set(2,10); //Cria a base da pilha, na posição de memória 2, nele está salvo o tamanho máximo da pilha

        StackPointer SP = new StackPointer(); //Inicializa o registrador com 0
        ProgramCounter PC = new ProgramCounter();
        Accumulator ACC = new Accumulator();
        OperationMode MOP = new OperationMode();
        AddressRecorder RE = new AddressRecorder();
        InstructionRecorder RI = new InstructionRecorder();

        memory.set(13,SP); //Cria o registrador SP na posição 13 da memória
        memory.set(14,PC);
        memory.set(15,ACC);
        memory.set(16,MOP);
        memory.set(17,RI);
        memory.set(18,RE);
    }

    public void set_element (int index, int element){
        memory.set(index,element);
    }

    public Object get(int index) {
        return memory.get(index);
    }

    public void push (StackPointer SP, int element){
        if (SP.getPointer() == 0 && memory.get(3) == null){ //Pilha está vazia
            SP.setPointer(4);
            memory.set(3,element);
        }else{
            if(SP.getPointer() == 13 || SP.getPointer() == 0){
                SP.setPointer(0);
                System.out.println("Stack Overflow");
            }else{
                memory.set(SP.getPointer(),element);
                int index = SP.getPointer();
                SP.setPointer(index++);
            }
        }
    }

    public void pop (StackPointer SP){
        if (SP.getPointer() == 0 && memory.get(3) == null || SP.getPointer() == 2){
            System.out.println("Empty Stack");
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
    }
}