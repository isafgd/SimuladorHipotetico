/*package binder;


import config.GetProperties;
import executor.Reader;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Binder {

    private GetProperties properties;

    public void firstPassage () throws IOException {
        Reader reader = new Reader("OutPut1Montador.txt");
        Reader reader2 = new Reader("OutPut1Montador.txt");

        FileWriter arq;
        File output = new File("src/main/resources/txt/OutPutLigador.txt");
        StringBuilder instructions = new StringBuilder();

        String line = reader.readLine();

        while (line!=null){
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

}*/
