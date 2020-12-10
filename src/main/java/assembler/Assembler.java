package assembler;

import java.io.*;
import java.math.BigInteger;
import java.util.*;


import ReadingFile.Read;
import lombok.Data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Data
public class Assembler {
    private int locationCounter; // contador de localizacao
    private Map<String, Integer> tabSimbolo; // tabela de simbolos
    private Map<String, Integer> labels; // rotulos
    private ArrayList<TabelaDefinicoes> definicoes = new ArrayList<> ();
    private ArrayList<TabelaDeUso> uso = new ArrayList<> ();


    public Assembler() {
        locationCounter = 0;
        tabSimbolo = new HashMap<>();
        labels = new HashMap<>();
    }

    public void monta(String arq) throws FileNotFoundException, IOException {
        // Leitura do arquivo de entrada
        BufferedReader bf = new BufferedReader(new FileReader(arq + ".txt"));

        // Cria o arquivo temporario
        FileWriter arqObj = new FileWriter("objeto" + "_" + arq + ".txt");

        // Le a primeira linha
        String linha = bf.readLine();

        // Separa a linha nos tabs
        String[] textoSeparado = linha.split("\t");

        // Cria variaveis temporarias
        TabelaDefinicoes Definicoes_temp;
        TabelaDeUso Uso_temp;
        // Enquanto nao encontra o inicio das instrucoes
        while (!textoSeparado[0].equals("ORG")) {
            if(textoSeparado[0].equals("INTERN")){ // Variavel global definida internamente
                // Guarda na tabela de definicoes
                Definicoes_temp = new TabelaDefinicoes();
                Definicoes_temp.setSimbolo(textoSeparado[1]);
                Definicoes_temp.setRelocabilidade("R");
                Definicoes_temp.setEndereco(Integer.parseInt(textoSeparado[3].substring(1)));
                definicoes.add(Definicoes_temp);
                // Guarda no mapa de rotulos
                labels.put(textoSeparado[1], Integer.parseInt(textoSeparado[3].substring(1)));
            }
            else if (textoSeparado[0].equals("EXTR")){ // Variavel global definida externamente
                // Guarda na tabela de uso
                Uso_temp = new TabelaDeUso();
                Uso_temp.setSimbolo(textoSeparado[1]);
                Uso_temp.setOperacao("+");
                Uso_temp.setRelocabilidade("R");
                Uso_temp.setLocationCounter(0);
                uso.add(Uso_temp);
                // Guarda no mapa de rotulos
                labels.put(textoSeparado[1], Integer.parseInt("0"));
            }
            // Le nova linha
            linha = bf.readLine();
            // Separa a linha nos tabs
            textoSeparado = linha.split("\t");
        }
        // Atualiza locationCounter de acordo com o endereco inicial das instrucoes
        textoSeparado[1] = textoSeparado[1].substring(1);
        locationCounter = Integer.parseInt(textoSeparado[1]);

        // Le primeira instrucao
        linha = bf.readLine();

        // Separa a linha nos tabs
        textoSeparado = linha.split("\t");

        // Enquanto nao terminam as instrucoes
        while (!textoSeparado[0].equals("END")) {

            // se encontra w na instrucao, ignora-o
            if (textoSeparado[0].charAt(textoSeparado[0].length() - 1) == 'W') {
                verificaOperacao(textoSeparado[0].substring(0, textoSeparado[0].length() - 2), arqObj);
            } else {
                verificaOperacao(textoSeparado[0], arqObj);
            }
            // Atualiza endereco de ocorrencia na tabela de uso
            for(int i=0;i<uso.size();i++){
                if(uso.get(i).getSimbolo().equals(textoSeparado[1])){
                    Uso_temp=uso.get(i);
                    Uso_temp.setLocationCounter(locationCounter);
                    uso.set(i, Uso_temp);
                    break;
                }
            }
            locationCounter += 4;

            switch (textoSeparado[1].charAt(0)) {
                case '#':
                    switch (textoSeparado[1].charAt(1)) {
                        case '$':
                            // hexadecimal
                            textoSeparado[1] = textoSeparado[1].substring(2);
                            arqObj.write((new BigInteger(textoSeparado[1], 16) + "\t"));
                            break;
                        case '@':
                            // octal
                            textoSeparado[1] = textoSeparado[1].substring(2);
                            arqObj.write(new BigInteger(textoSeparado[1], 8) + "\t");
                            //break;
                        case '%':
                            // binario
                            textoSeparado[1] = textoSeparado[1].substring(2);
                            arqObj.write(textoSeparado[1] + "\t");
                            break;
                        default:
                            // decimal
                            textoSeparado[1] = textoSeparado[1].substring(1);
                            arqObj.write(Integer.parseInt(textoSeparado[1]) + "\t");
                            break;
                    }
                    break;
                case 'D':
                    arqObj.write(Integer.parseInt(textoSeparado[1].substring(1)) + "\t");
                    break;
                default:
                    arqObj.write(labels.get(textoSeparado[1]) + "\t");
                    break;
            }
            locationCounter += 4;

            if (textoSeparado.length == 3) {
                switch (textoSeparado[2].charAt(0)) {
                    case '#':
                        switch (textoSeparado[2].charAt(1)) {
                            case '$':
                                // hexadecimal
                                textoSeparado[2] = textoSeparado[2].substring(2);
                                arqObj.write(new BigInteger(textoSeparado[2], 16) + "\t");
                                break;
                            case '@':
                                // octal
                                textoSeparado[2] = textoSeparado[2].substring(2);
                                arqObj.write(new BigInteger(textoSeparado[2], 8) + "\t");
                                //break;
                            case '%':
                                // binario
                                textoSeparado[2] = textoSeparado[2].substring(2);
                                arqObj.write(textoSeparado[2] + "\t");
                                break;
                            default:
                                // decimal
                                textoSeparado[2] = textoSeparado[2].substring(1);
                                arqObj.write(Integer.parseInt(textoSeparado[2]) + "\t");
                                break;
                        }
                        break;
                    case 'D':
                        arqObj.write((Integer.parseInt(textoSeparado[2].substring(1))) + "\t");
                        break;
                    default:
                        // Atualiza endereco de ocorrencia na tabela de uso
                        for(int i=0;i<uso.size();i++){
                            if(uso.get(i).getSimbolo().equals(textoSeparado[1])){
                                Uso_temp=uso.get(i);
                                Uso_temp.setLocationCounter(locationCounter);
                                uso.set(i, Uso_temp);
                                break;
                            }
                        }
                        arqObj.write((labels.get(textoSeparado[2])) + "\t");
                        break;
                }
                locationCounter += 4;
            }

            arqObj.write("\n");

            // Le nova linha
            linha = bf.readLine();
            // Separa a linha nos tabs
            textoSeparado = linha.split("\t");

        }

        // Fecha o arquivo
        arqObj.close();
    }

    public void verificaOperacao(String palavra, FileWriter arq) throws IOException {
        switch (palavra) {
            case "ADD":
                arq.write("1101" + "\t");
                break;
            case "ADDI":
                arq.write("00000110" + "\t");
                break;
            case "AND":
                arq.write("1100" + "\t");
                break;
            case "ANDI":
                arq.write("00000010" + "\t");
                break;
            case "BRA":
                arq.write("01100000" + "\t");
                break;
            case "CMP":
                arq.write("1011" + "\t");
                break;
            case "CMPI":
                arq.write("00001100" + "\t");
                break;
            case "DIVS":
                arq.write("1000" + "\t");
                break;
            case "DIVU":
                arq.write("1000" + "\t");
                break;
            case "JMP":
                arq.write("0100111011" + "\t");
                break;
            case "LSL":
                arq.write("1110" + "\t");
                break;
            case "LSR":
                arq.write("1110" + "\t");
                break;
            case "MOVE":
                arq.write("00" + "\t");
                break;
            case "MULS":
                arq.write("1100" + "\t");
                break;
            case "MULU":
                arq.write("1100" + "\t");
                break;
            case "NOP":
                arq.write("0100111001110001");
                break;
            case "NEG":
                arq.write("01000100" + "\t");
                break;
            case "NOT":
                arq.write("01000110" + "\t");
                break;
            case "OR":
                arq.write("1000" + "\t");
                break;
            case "ORI":
                arq.write("00000000" + "\t");
                break;
            case "SUB":
                arq.write("1001" + "\t");
                break;
            case "SUBI":
                arq.write("00000100" + "\t");
                break;
            case "STOP":
                arq.write("0100111001110010" + "\t");
        }
    }

    public ArrayList<TabelaDefinicoes> GetTabelaDeDefinicoes(){
        return definicoes;
    }

    public ArrayList<TabelaDeUso> GetTabelaDeUso(){
        return uso;
    }

}
