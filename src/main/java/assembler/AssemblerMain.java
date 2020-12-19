package assembler;

import java.io.FileNotFoundException;

public class AssemblerMain {

    public static void main(String[]args) throws FileNotFoundException {
        Assembler assembler = new Assembler();
        assembler.pseudoinstructionsListInit();
        assembler.intructionsListInit();
        assembler.preencheListaSimbolos();
    }
}
