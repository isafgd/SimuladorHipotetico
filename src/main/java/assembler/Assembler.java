package assembler;

import ReadingFile.Read;
import executor.Memory;
import executor.MemoryList;
import javafx.collections.ObservableList;
import javafx.scene.control.palavraArea;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

public class Assembler {

    public void firstRead() {
        //Read reader = new Read();
        //String line = reader.readLine();

        String line = "FRONT LOAD OLDER";
        String[] palavra = line.split(" ");

        Instruction instrucao = new Instruction("ADD", 2, "00101");
        List<Instruction> instrucoes = new ArrayList<>();

        instrucoes.add(instrucao);
        HashMap<String, Integer> tabelaSimbolos = new HashMap<String, Integer>(); //Tabela de Símbolos: Label / Adress

        while (line!=null) {
            if (line.charAt(0) == '*') {
                //line = reader.readLine();
                System.out.println("Comentário");
            } else if {
                Integer tamanho = 0;
                switch (palavra[0]) {
                    case "ADD":
                    case "BR":
                    case "BRNEG":
                    case "BRPOS":
                    case "BRZERO":
                    case "CALL":
                    case "DIVIDE":
                    case "LOAD":
                    case "MULT":
                    case "READ":
                    case "STORE":
                    case "SUB":
                    case "WRITE":
                        tamanho = 1;
                        break;
                    case "COPY":
                        tamanho = 2;
                        break;
                    case "RET":
                    case "STOP":
                        tamanho = 0;
                        break;
                    default:
                        System.out.println("Label");
                        if (tabelaSimbolos.get(palavra[0]) == null){
                            tabelaSimbolos.put(palavra[0], -1);
                        } else {
                            tabelaSimbolos.remove(palavra[0]);
                            tabelaSimbolos.put(palavra[0], adress);
                        }
                        break;
                }
                Integer texto = 1;
                for ( ; tamanho > 0 ; tamanho--){
                    if (tabelaSimbolos.get(palavra[texto]) == null){
                        tabelaSimbolos.put(palavra[texto], -1);
                    } else {
                        tabelaSimbolos.remove(palavra[texto]);
                        tabelaSimbolos.put(palavra[texto], adress);
                    }

                }
            }

        }

    }

}
