package assembler;

import java.io.*;
import java.math.BigInteger;
import java.util.*;


import lombok.Data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

@Data
public class Assembler {
    private int locationCounter; // contador de localizacao
    private int lineCounter; // contador de linhas
    private Map<String, Integer> labels; // rotulos
    //private ArrayList<TabelaDefinicoes> definicoes = new ArrayList<> ();
    //private ArrayList<TabelaDeUso> uso = new ArrayList<> ();
    private HashMap <String, Integer> tabelaUso;
    private HashMap <String, Integer> tabelaDefinicoes;
    private HashMap<String, Integer> tabelaDeSimbolos;

    public Assembler() {
        locationCounter = 0;
        lineCounter = 0;
        tabelaUso = new HashMap<>();
        tabelaDeSimbolos = new HashMap<>();
        tabelaDeSimbolos = new HashMap<>();
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
        while (!textoSeparado[0].equals("START")) {
            if (textoSeparado[0].equals("EXTDEF")){ // Variavel global definida externamente
                // Guarda na tabela de uso
                tabelaDefinicoes.put(textoSeparado[1], locationCounter);
                // Guarda no mapa de rotulos
                tabelaDeSimbolos.put(textoSeparado[1], locationCounter);
            }
            // Le nova linha
            linha = bf.readLine();
            // Separa a linha nos tabs
            textoSeparado = linha.split(" "); // mudar pra /t se der erro
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
            locationCounter += 1;

            switch (textoSeparado[1].charAt(0)) {
                case '#': // endereçamento imediato?
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
            locationCounter += 1;

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
                locationCounter += 1;
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
//*******************************************************************************************************************************************************************************************************************************************************
//*******************************************************************************************************************************************************************************************************************************************************
    //TEMOS QUE VERIFICAR PSEUDOINSTRUÇÕES
    //CONST, END*, EXTDEF, EXTR, SPACE, STACK, START*
    //END e START marcam início e fim do módulo, a gente só deve ver em casos específicos



    //Proposta dessa função:
    //Recebe a linha inteira do comando e verifica inicialmente verifica o primeiro componente:
    //Se for uma expressão de operação, vai verificar se os operandos são labels. Se forem, é verificada sua existência na tabela de símbolos para inserção
    //Se for diretamente um label, verifica a existência dele na tabela, se não existir, insere com o endereço correspondente. Se existe, insere o endereço (ainda não feito)
    //talvez seja melhor quebrar isso em mais funçções?
    public void verifyLabels(String[] instrucao, int instructionCounter) throws IOException {
        switch (instrucao[0]) {
            case "ADD":
            case "BR":
            case "BRNEG":
            case "BRPOS":
            case "BRZERO":
            case "DIVIDE":
            case "LOAD":
            case "MULT":
            case "READ":
            case "STORE":
            case "SUB":
            case "WRITE":
            case "CALL":
                    //lê o operando que vem depois
                    //verifica rapidamente o tipo de operação para colocar a parte certa do label dentro da tabela de Símbolos, se já não estiver lá
                    if('A' < instrucao[1].charAt(0)  || instrucao[1].charAt(0) > 'z') { //verifica se é um label
                        if (!tabelaDeSimbolos.containsKey(instrucao[1])) { //verifica se está na tabela de simbolos
                            if (instrucao[1].substring((instrucao[1].length() - 2), instrucao[1].length()) != ",I")
                                tabelaDeSimbolos.put(instrucao[1], null); //verifica se o endereçamento indireto existe, se não existe, coloca o label inteiro na tabela de simbolos
                            else
                                tabelaDeSimbolos.put(instrucao[1].substring(0, instrucao[1].length() - 1), null); //se o enderçamento indireto existe, adiciona o label na tabela sem o final ,I
                        }
                    }
                //atualizamos o location counter?
                //locationCounter += 2
                break;
            case "COPY":
                //lê o operando que vem depois
                //verifica rapidamente o tipo de operação para colocar a parte certa do label dentro da tabela de Símbolos, se já não estiver lá
                if('A' < instrucao[1].charAt(0)  || instrucao[1].charAt(0) > 'z') { //verifica se é um label
                    if (!tabelaDeSimbolos.containsKey(instrucao[1])) { //verifica se está na tabela de simbolos
                        if (instrucao[1].substring((instrucao[1].length() - 2), instrucao[1].length()) != ",I")
                            tabelaDeSimbolos.put(instrucao[1], null); //verifica se o endereçamento indireto existe, se não existe, coloca o label inteiro na tabela de simbolos
                        else
                            tabelaDeSimbolos.put(instrucao[1].substring(0, instrucao[1].length() - 1), null); //se o enderçamento indireto existe, adiciona o label na tabela sem o final ,I
                    }
                }
                //atualizamos o location counter após escrever no binário?
                //locationCounter += 3
                //lê o operando que vem depois 2
                //verifica rapidamente o tipo de operação para colocar a parte certa do label dentro da tabela de Símbolos, se já não estiver lá 2
                if('A' < instrucao[2].charAt(0)  || instrucao[2].charAt(0) > 'z') { //verifica se é um label
                    if (!tabelaDeSimbolos.containsKey(instrucao[2])) { //verifica se está na tabela de simbolos
                        if (instrucao[1].substring((instrucao[2].length() - 2), instrucao[1].length()) != ",I")
                            tabelaDeSimbolos.put(instrucao[2], null); //verifica se o endereçamento indireto existe, se não existe, coloca o label inteiro na tabela de simbolos
                        else
                            tabelaDeSimbolos.put(instrucao[2].substring(0, instrucao[2].length() - 1), null); //se o enderçamento indireto existe, adiciona o label na tabela sem o final ,I
                    }
                }
                //atualizamos o location counter?
                //locationCounter += 3
                break;
            case "RET":
                break;
            case "STOP":
                //começa a guardar os valores dos labels
                //pula pra próxima linha direto
                //atualizamos o location counter?
                //locationCounter += 2
            //pseudointruções
            case "EXTDEF":
                //se acha esse comando, quer dizer que esse símbolo declarado aqui é usado no outro módulo
                //colocar na tabela de definições
                tabelaDefinicoes.put(instrucao[0], lineCounter);
            case "EXTR":
                //se achar esse comando, quer dizer que esse símbolo foi definido no outro módulo
                //deve ser colocado na tabela de uso
                tabelaUso.put(instrucao[0], lineCounter);
            case "STACK":
                //??????????????
                break;
            default:
                //verifica se tem formato de um label (se é uma string começada por um caractere alfabético)
                //se for, coloca ele na table de símbolos junto com o valor do adress counter
                if('A' < instrucao[0].charAt(0)  || instrucao[0].charAt(0) > 'z' && !tabelaDeSimbolos.containsKey(instrucao[1])) {tabelaDeSimbolos.put(instrucao[0], instructionCounter);}

                for(int i = 0; i < (instrucao.length - 1); i++) { //como tem um label na posição 0 da instrução, ele move a string pra que o label, que já tá pronto, suma e faz de novo a a função
                    instrucao[i] = instrucao[i+1];
                }
                verifyLabels(instrucao, locationCounter);
                break;
        }
    }

    public int getAmountOperands(String instrucao) throws IOException { // Pegar quantidade de operandos
        int amountOperands = -1; // contador de operandos, SE FOR -1 PODE SER LABEL VERIFICAR
        switch (instrucao) {
            case "ADD":
            case "BR":
            case "BRNEG":
            case "BRPOS":
            case "BRZERO":
            case "DIVIDE":
            case "LOAD":
            case "MULT":
            case "READ":
            case "STORE":
            case "SUB":
            case "WRITE":
            case "CALL":
                amountOperands = 1;
                break;
            case "COPY":
                amountOperands = 2;
                break;
            case "RET":
            case "STOP":
                amountOperands = 0;
                break;
        }
        return amountOperands; // insere a quantidade de operandos desse return em um array
    }

    // FAZER MAP COM AS INSTRUÇÕES E BINÁRIO DE CADA UMA -> opcodeInstrucao <-
    public void escreveBinarioTxt(String instrucao, FileWriter arq, ArrayList <Integer> operandos, Map <String, String> opcodeInstrucao) throws IOException {
        // já envia os 32 bits pro txt
        switch (instrucao) {
            case "ADD":
            case "BR":
            case "BRNEG":
            case "BRPOS":
            case "BRZERO":
            case "DIVIDE":
            case "LOAD":
            case "MULT":
            case "READ":
            case "STORE":
            case "SUB":
            case "WRITE":
            case "CALL":
                List <String> atributos = getEnderecamento(operandos.get(0));
                arq.write("00000000"+atributos.get(0)+opcodeInstrucao.get(instrucao)+ atributos.get(1));
                //atualizamos o location counter após escrever no binário?
                //locationCounter += 2
                break;
            case "COPY":
                List <String> atributos2 = getEnderecamento(operandos.get(0), operandos.get(1));
                arq.write("00000000"+atributos2.get(0) + opcodeInstrucao.get(instrucao) + atributos2.get(1) + atributos2.get(2));  // BITS -SIGNIFICATIVOS, ENDEREÇAMENTO, OPCODE, OPD1 E OPD2
                //atualizamos o location counter após escrever no binário?
                //locationCounter += 3
                break;
            case "RET":
            case "STOP":
                arq.write("00000000"+"000"+opcodeInstrucao.get(instrucao));
                //atualizamos o location counter após escrever no binário?
                //locationCounter += 1
                break;
            default:
                arq.write("Instrução inválida.");
        }
        //como contador de linhas sempre vai ser 1 a mais que o anterior
        ////lineCounter += 1
    }

    public List <String> getEnderecamento(Integer operando1) {
        String resultado = "000";
        String operando1String = operando1.toString();
        char firstOp1 = operando1String.charAt(0);
        char lastOp1 = operando1String.charAt(operando1String.length());

        if(firstOp1 == '#')
            resultado = "100";

        if(lastOp1 == 'I')
            resultado = "001";

        List <String> resultList = removeSimbol(operando1.toString(), "0");
        resultList.add(0, resultado); // primeiro item endereçamento, 2 opd1 e 3 opd2
        return resultList;
    }
    public List <String> getEnderecamento(Integer operando1, Integer operando2) {
        String resultado = "000";                                            // direto por default

        String operando1String = operando1.toString();
        char firstOp1 = operando1String.charAt(0);                           // Testa primeiro caractere
        char lastOp1 = operando1String.charAt(operando1String.length());     // Testa ultimo operando

        String operando2String = operando2.toString();
        char firstOp2 = operando2String.charAt(0);
        char lastOp2 = operando2String.charAt(operando2String.length());

        if(firstOp2 == '#')                                                  // Verificações de endereçamento de operandos
            if(firstOp1 != '#' && lastOp1 != 'I' )
                resultado = "100"; // imediato
            else resultado = "101";

        if(lastOp2 == 'I')
            if(firstOp1 != '#' && lastOp1 != 'I' )
                resultado = "010";
            else resultado = "011";

        if(lastOp1 == 'I')
                resultado = "001";

        List <String> resultList = removeSimbol(operando1.toString(), operando2.toString());
        resultList.add(0, resultado); // primeiro item endereçamento, 2 opd1 e 3 opd2
        return resultList;

    }

    public List <String> removeSimbol (String operando1, String operando2) {
        String newOpd1 = operando1;
        String newOpd2 = operando2;

        String operando1String = operando1.toString();
        char firstOp1 = operando1String.charAt(0);                           // Testa primeiro caractere
        char lastOp1 = operando1String.charAt(operando1String.length());     // Testa ultimo operando

        String operando2String = operando2.toString();
        char firstOp2 = operando2String.charAt(0);
        char lastOp2 = operando2String.charAt(operando2String.length());

        if(firstOp1 == '#')
            newOpd1 = operando1.substring(1,operando1.length());

        if(lastOp1 == 'I')
            newOpd1 = operando1.substring(0, operando1.length() - 1);

        if(firstOp2 == '#')
            newOpd2 = operando1.substring(1,operando2.length());

        if(lastOp2 == 'I')
            newOpd2 = operando1.substring(0, operando2.length() - 1);

        newOpd1 = Integer.toBinaryString(Integer.parseInt(newOpd1));
        newOpd2 = Integer.toBinaryString(Integer.parseInt(newOpd2));

        List <String> operandos = new ArrayList<>();
        operandos.add(1, newOpd1);
        operandos.add(2, newOpd2);

        return operandos;
    }

    public ArrayList<TabelaDefinicoes> GetTabelaDeDefinicoes(){
        return definicoes;
    }

    public ArrayList<TabelaDeUso> GetTabelaDeUso(){
        return uso;
    }

    public void escreveTabelaDefinicoes(){
        GetTabelaDeDefinicoes();
        // escrever em um arquivo txt para enviar pra Isa e pro Jonnhy, primeira coluna endereço e segunda de símbolo
    }

    public void escreveTabelaDeUso(){

    }

}