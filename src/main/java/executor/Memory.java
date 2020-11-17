package executor;

import executor.recorders.*;
import java.util.ArrayList;

public class Memory {

    ArrayList<Object> memory = new ArrayList();

    private final int max_size = 500; //Cada posição corresponde à uma palavra de 16bits - No total a memória terá 1KB

    public Memory (){ //Inicializa toda a memória
        for (int i = 3;i<=12;i++){
            memory.add(i,null); //Preenche a pilha com NULL
        }

        StackPointer SP = new StackPointer(); //Inicializa o registrador com 0
        ProgramCounter PC = new ProgramCounter();
        Accumulator ACC = new Accumulator();
        OperationMode MOP = new OperationMode();
        AddressRecorder RE = new AddressRecorder();
        InstructionRecorder RI = new InstructionRecorder();

        memory.add(13,SP); //Cria o registrador SP na posição 13 da memória
        memory.add(14,PC); //Cria o registrador SP na posição 14 da memória
        memory.add(15,ACC); //Cria o registrador SP na posição 15 da memória
        memory.add(16,MOP); //Cria o registrador SP na posição 16 da memória
        memory.add(17,RI); //Cria o registrador SP na posição 17 da memória
        memory.add(18,RE); //Cria o registrador SP na posição 18 da memória

        memory.add(2,10); //Cria a base da pilha, na posição de memória 2, nele está salvo o tamanho máximo da pilha
    }

    public int add_element (int index, int element){
        memory.add(index,element);
        return index++;
    }

    public void push (StackPointer SP, int element){
        if (SP.getPointer() == 0 && memory.get(3) == null){ //Pilha está vazia
            SP.setPointer(4);
            memory.add(3,element);
        }else{
            if(SP.getPointer() == 13 || SP.getPointer() == 0){
                SP.setPointer(0);
                System.out.println("Stack Overflow");
            }else{
                memory.add(SP.getPointer(),element);
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
                memory.add(12,null);
                SP.setPointer(11);
            }else{
                memory.add(SP.getPointer(),null);
                int index = SP.getPointer();
                SP.setPointer(index--);
            }
        }
    }
}
