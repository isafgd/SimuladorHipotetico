package executor.recorders;

import lombok.Data;

@Data
public class StackPointer {
    private final int size = 16;
    int stack;
    public StackPointer() { // construtor padrão
        this.stack = 0;
    }

    public int ge() {
        return this.stack;
    }

    public void set(int newStack) {
        this.stack = newStack;
    }
}
