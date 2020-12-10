package assembler;


import ReadingFile.Read;
import lombok.Data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Data
public class Assembler {
    private LineCounter fontLineCounter_A;
    private LineCounter lineCounter_A;
    private AdressCounter adressCounter_A;
    private HashMap<String, Integer> tabelaSimbolos_A;  //Tabela de Símbolos: Label / Adress
    private HashMap<String, Integer> tabelaDefinicoes_A;  //Tabela de Definições: Label / Adress, temos que substituir elas no código binário

    private LineCounter fontLineCounter_B;
    private LineCounter lineCounter_B;
    private AdressCounter adressCounter_B;
    private HashMap<String, Integer> tabelaSimbolos_B;  //Tabela de Símbolos: Label / Adress
    private HashMap<String, Integer> tabelaDefinicoes_B;  //Tabela de Símbolos: Label / Adress


    //INICIAR TABELA A - iniciandoTabelaDefinicoes('A', nomeArquivoModuloA)
    //INICIAR TABELA B - iniciandoTabelaDefinicoes('B', nomeArquivoModuloB)
    //COMPLETAR TABELA A - completandoTabelaDefinicoes('A', nomeArquivoModuloA)
    //COMPLETAR TABELA B - completandoTabelaDefinicoes('B', nomeArquivoModuloB)


    public void completandoTabelaDefinicoes (char ab, String nomeArq) throws IOException {
        Read reader = new Read(nomeArq);
        String line = reader.readLine();

        String[] palavra = line.split(" ");

        while(isLabel(palavra[0])){                     //ENQUANTO NÃO TIVER A PRIMEIRA INSTRUÇÃO, NÃO CONTAMOS O ADRESS
            line = reader.readLine();
        }

        while (line != null) {
            switch (ab) {
                case 'A':
                    if(tabelaDefinicoes_A.containsKey(palavra[0])){
                        tabelaDefinicoes_A.put(palavra[0], adressCounter_A.get());
                        atualizarEndereco(adressCounter_A, palavra[1]);
                    } else {
                        atualizarEndereco(adressCounter_A, palavra[0]);
                    }
                case 'B':
                    if(tabelaDefinicoes_B.containsKey(palavra[0])){
                        tabelaDefinicoes_B.put(palavra[0], adressCounter_B.get());
                        atualizarEndereco(adressCounter_B, palavra[1]);
                    } else {
                        atualizarEndereco(adressCounter_B, palavra[0]);
                    }
            }
            line = reader.readLine();
        }
    }


    public void iniciandoTabelaDefinicoes (char ab, String nomeArq) throws IOException {
        Read reader = new Read(nomeArq);
        String line = reader.readLine();
        Integer tamanho = 0;

        String[] palavra = line.split(" ");
        while (line != null) {
            if (palavra[0] == "EXTDEF") {
                if (ab == 'A') {
                    tabelaDefinicoes_A.put(palavra[1], -1);
                } else {
                    tabelaDefinicoes_B.put(palavra[1], -1);
                }
            }
            line = reader.readLine();
        }
    }


    public void firstRead(String nomeArq) throws IOException {
        Read reader = new Read(nomeArq);
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





    public void atualizarEndereco (AdressCounter x, String instrucao){
        x.add(getLength(instrucao) + 1);
    }


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
                return "00010";
            case "BR":
                return "00000";
            case "BRNEG":
                return "00101";
            case "BRPOS":
                return "00001";
            case "BRZERO":
                return "00100";
            case "CALL":
                return "01111";
            case "DIVIDE":
                return "01010";
            case "LOAD":
                return "00011";
            case "MULT":
                return "01110";
            case "READ":
                return "01100";
            case "STORE":
                return "00111";
            case "SUB":
                return "00110";
            case "WRITE":
                return "01000";
            case "COPY":
                return "01101";
            case "RET":
                return "10000"; // RET PRECISA DE TAMANHO DE OPCODE 5
            case "STOP":
                return "01011";
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
