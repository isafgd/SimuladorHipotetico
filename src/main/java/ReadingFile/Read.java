package ReadingFile;

import config.GetProperties;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/*Le o arquivo que foi escrito de acordo com a entreda na interface, le linha por linha*/
public class Read {

    private FileReader arq;
    private  BufferedReader lerArq;
    private GetProperties properties;

    public Read() throws FileNotFoundException, IOException{
        properties = new GetProperties();
        arq = new FileReader(properties.getPropValues("caminho"));
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
