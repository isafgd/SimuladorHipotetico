package assembler;

import java.io.FileNotFoundException;
import java.io.IOException;

public class AssemblerMain {

    public static void main(String[]args) throws FileNotFoundException, IOException {
        Assembler assembler = new Assembler();
        assembler.pseudoinstructionsListInit();
        assembler.createOpcodeInstructions();
        assembler.intructionsListInit();
        assembler.primeiraLeitura();
        assembler.segundaLeitura();

    }
}
