package ReadingFile;

import config.GetProperties;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Read {
    private FileReader arq;
    private  BufferedReader lerArq;
    private GetProperties properties;
    private ArrayList<Object> teste = new ArrayList<Object>();

    public Read() throws FileNotFoundException, IOException{
        properties = new GetProperties();
        arq = new FileReader(properties.getPropValues("caminho"));
        lerArq = new BufferedReader(arq);
       // teste.set();
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
