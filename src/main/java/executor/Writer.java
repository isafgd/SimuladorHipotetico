package executor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;


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
