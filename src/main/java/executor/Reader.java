package executor;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Reader {

    private FileReader arq;
    private  BufferedReader lerArq;
    private String caminho;

    public Reader() throws FileNotFoundException{
        caminho="src/main/resources/Example.txt";
        arq = new FileReader(caminho);
        lerArq = new BufferedReader(arq);
    }

    public String readLine(){
        String linha;
        try{
            linha = lerArq.readLine();
        }catch(IOException e){
            return null;
        }
        return linha;
    }

}

