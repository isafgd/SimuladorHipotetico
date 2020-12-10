package executor;

import java.io.*;

/*Escreve no arquivo InputCode*/
public class Writer {

    public static void writeFile(String instructions){
        FileWriter arq;
        File output = new File("src/main/resources/InputCode.txt");
        try {
            arq = new FileWriter(output);
            arq.write(instructions);
            arq.flush();
            arq.close();
        } catch (IOException ex) {
            System.out.println("Arquivo nao encontrado");
        }
    }
}
