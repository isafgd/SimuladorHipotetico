package executor.recorders;

import lombok.Data;

@Data
public class StackPointer {
    static final int MAX = 1000;
    int top;
    int a[] = new int[MAX]; // Tamanho máximo da pilha
    public StackPointer() { // construtor padrão
        this.top = -1;
    } // Construtor

    public boolean push(int x) {
        if (top >= (MAX-1)) {
            System.out.println("Estouro de Pilha!");
            return false;
        }
        else {
            a[++top] = x;
            return true;
        }
    }

    public int pop() {
        if (top < 0) {
            System.out.println("Pilha Vazia!");
            return 0;
        }
        else {
            int x = a[top--];
            return x;
        }
    }

}