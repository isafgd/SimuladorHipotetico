package ReadingFile;

import java.io.FileNotFoundException;

public class Teste {
    public static void main(String []args) throws FileNotFoundException {
        Read leitor=new Read();
        String linha="!";
        while(linha != null){
            linha=leitor.readLine();
            System.out.println(linha);
        }
    }
}
