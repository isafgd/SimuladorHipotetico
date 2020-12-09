package assembler;


import ReadingFile.Read;
import lombok.Data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Data
public class Assembler {
    private LineCounter fontLineCounter;
    private LineCounter lineCounter;
    private AdressCounter adressCounter;
    private HashMap<String, Integer> tabelaSimbolos;  //Tabela de Símbolos: Label / Adress

    public void firstRead() throws IOException {
        Read reader = new Read();
        String line = reader.readLine();
        Integer tamanho = 0;

        String[] palavra = line.split(" ");

        while (line!=null) {
            if (line.charAt(0) != '*') {
                switch (getLength(palavra[0])) {
                    case 1:                             //OPCODE QUE PRECISA DE UM OPERANDO
                        readOperando(palavra[1]);
                        adressCounter.add(2);
                        break;
                    case 2:                            //OPCODE QUE PRECISA DE DOIS OPERANDOS
                        readOperando(palavra[1]);
                        readOperando(palavra[2]);
                        adressCounter.add(3);
                        break;
                    case -1:                           //LABEL?
                        if (isReallyLabel(palavra[0])){
                            if (isCompleteInTable(palavra[0])){
                                //ERRO - LABEL COM DOIS PONTEIROS

                            } else {
                                tabelaSimbolos.put(palavra[0], adressCounter.get());    //ADICIONA O LABEL E SEU ENDEREÇO NA TABELA
                                                                                         //JÁ QUE ELE É O ENDEREÇO JÁ
                                switch (getLength(palavra[1])) {                        // VERIFICA QUANTOS OPERANDOS TEM NO OPCODE
                                    case 1:
                                        readOperando(palavra[2]);
                                        adressCounter.add(2);
                                        break;
                                    case 2:
                                        readOperando(palavra[2]);
                                        readOperando(palavra[3]);
                                        adressCounter.add(3);
                                        break;
                                }
                            }
                        } else {
                            //ERRO DE LABEL - MAIS DE 8 CARACT OU NÃO INICIA COM LETRA
                        }
                        break;
                }


                System.out.println("Label");
                if (tabelaSimbolos.get(palavra[0]) == null){    //LABEL AINDA NÃO ESTÁ NA TABELA
                    tabelaSimbolos.put(palavra[0], -1);
                } else {                                        //LABEL JÁ ESTÁ NA TABELA, COLOCAR O ADRESS
                    //tabelaSimbolos.remove(palavra[0]);
                    tabelaSimbolos.put(palavra[0], adressCounter.get());
                }
                break;
            }                           //termina aqui o if do '*'

            line = reader.readLine();   //lerá a proxima linha

        }                               //fim do while

    }                                   //fim da primeira leitura

    public int getLength (String string) {
        switch (string) {
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
                return 1;
            break;
            case "COPY":
                return 2;
            break;
            case "RET":
            case "STOP":
                return 0;
            break;
            default:
                return -1;
            break;
        }
    }

    public String getBinary (String string) {
        switch (string) {
            case "ADD":
                return "0010";
            case "BR":
                return "0000";
            case "BRNEG":
                return "0101";
            case "BRPOS":
                return "0001";
            case "BRZERO":
                return "0100";
            case "CALL":
                return "1111";
            case "DIVIDE":
                return "1010";
            case "LOAD":
                return "0011";
            case "MULT":
                return "1110";
            case "READ":
                return "1100";
            case "STORE":
                return "0111";
            case "SUB":
                return "0110";
            case "WRITE":
                return "1000";
            case "COPY":
                return "1101";
            case "RET":
                return "10000"; // RET PRECISA DE TAMANHO DE OPCODE 5
            case "STOP":
                return "1011";
            default:
                return "";
        }
    }

    public boolean isLabel (String string){
        if (string.charAt(0) == '@' || string.charAt(0) == '&'){
            return false;
        } else if (string.charAt(0) > 48 && string.charAt(0) < 57) {
            return false;
        }
        return true;
    }

    public boolean isReallyLabel (String string){   //pra ser REALMENTE um label, tem que ter no máx 8 caract. e começar por letra
        if (string.length() <= 8){
            if ((string.charAt(0) >= 65 && string.charAt(0) <= 90) && (string.charAt(0) >= 97 && string.charAt(0) <= 122)){
                return true;
            }
        }
        return false;
    }

    public void readOperando (String string){               //LÊ O OPERANDO
        if (isLabel(string) && isReallyLabel(string)) {
            if (!isInTable(string)) {                       //SE JÁ TIVER NA TABELA TUDO BEM, NÃO É ERRO POIS ESTÁ NO OPERANDO
                tabelaSimbolos.put(string, -1);
            }
        }
    }

    public String adressMode (String string){
        if (string.charAt(0) == '@'){
            return "imediato";
        } else if (string.charAt(0) == '&') {
            return "indireto";
        }
        return "direto";
    }

    public boolean isInTable (String string){
        if (tabelaSimbolos.get(string) == null){
            return false;
        } else {
            return true;
        }
    }

    public boolean isCompleteInTable (String string){   //Se já estiver completo na tabela e o vermos de novo, é um erro.
        if (tabelaSimbolos.get(string) == -1){
            return false;
        } else {
            return true;
        }
    }

    public boolean isUp80Char (String string){
        if (string.length() > 80) {
            return true;
        } else {
            return false;
        }
    }



}
