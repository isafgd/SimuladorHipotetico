package executor;

import java.io.*;

/*Escreve no arquivo InputCode*/
public class Writer {

    public static void writeFile(String instructions, String fileName){
        FileWriter arq;
        File output = new File("src/main/resources/txt/" + fileName);
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
