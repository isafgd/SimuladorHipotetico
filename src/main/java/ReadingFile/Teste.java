package ReadingFile;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Teste {
    public static void main(String []args) throws FileNotFoundException, IOException {
        Read leitor=new Read();
        String linha="!";
        while(linha != null){
            linha=leitor.readLine();
            System.out.println(linha);
        }
    }
}
