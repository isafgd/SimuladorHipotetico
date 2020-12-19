package assembler;

import java.io.*;
import java.math.BigInteger;
import java.util.*;


import executor.Reader;
import lombok.Data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

@Data
public class Assembler {
    private int adressCounter; // contador de localizacao
    private int lineCounter; // contador de linhas
    private ArrayList<String> tabelaUso;
    private Map<String, Integer> tabelaUsoAux;
    private Map<String, Integer> tabelaDefinicoes;
    private Map<String, Integer> tabelaDeSimbolos;
    private Map<String, Integer> instrucoes;
    private Map<String, Integer> pseudoinstrucoes;

    public Assembler() {
        adressCounter = 0;
        lineCounter = 0;
        tabelaUsoAux = new HashMap<>();
        tabelaDefinicoes = new HashMap<>();
        tabelaDeSimbolos = new HashMap<>();
        instrucoes = new HashMap<>();
        pseudoinstrucoes = new HashMap<>();
        tabelaUso = new ArrayList<String>();
    }

    /*public void monta(String arq) throws FileNotFoundException, IOException {
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
            if (textoSeparado[0].equals("EXTDEF")) { // Variavel global definida externamente
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
            for (int i = 0; i < uso.size(); i++) {
                if (uso.get(i).getSimbolo().equals(textoSeparado[1])) {
                    Uso_temp = uso.get(i);
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
                        for (int i = 0; i < uso.size(); i++) {
                            if (uso.get(i).getSimbolo().equals(textoSeparado[1])) {
                                Uso_temp = uso.get(i);
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
*/
    //*******************************************************************************************************************************************************************************************************************************************************
//*******************************************************************************************************************************************************************************************************************************************************
    public void assemblerMain() {
        //primeira leitura do arquivo
        try {
            preencheListaSimbolos();
        } catch (FileNotFoundException e) {
            System.out.print("Nao foi possivel ler os labels, por causa: " + e.getMessage());
        }
        //

    }

/*    public void preencheListaSimbolos () throws FileNotFoundException{
        Reader reader = new Reader("EntradaTeste.txt");
        Integer cont = 0;
        String linha = reader.readLine();
        while(linha!=null) {
            String[] args = linha.split(" ");
            if(args.length==2){
                if(!instrucoes.containsKey(args[0])){
                    if(!tabelaDeSimbolos.containsKey(args[0]))
                        tabelaDeSimbolos.put(args[0],cont);
                }else
                    if (args[0].equals("EXTDEF")){
                        tabelaDefinicoes.put(args[1],-1);

                    }
            }

            linha = reader.readLine();
        }
    }*/

    //RAQUEL MEXEU AQUI
    public void preencheListaSimbolos() throws FileNotFoundException {
        Reader reader = new Reader("EntradaTeste.txt");
        String linha = reader.readLine();
        boolean flag = false;
        String ext = "";
        List<ExtLabel> extLabels = new ArrayList<>();
        lineCounter = 0;
        while (linha != null) {
            String[] args = linha.split(" ");
            if(args.length >=2) {
                args = removeAdressType(args);  //tira todos as formas de endereçamento presentes
                while (pseudoinstrucoes.containsKey(args[0]) || pseudoinstrucoes.containsKey(args[1])) {
                    if (args[1].equals("EXTR")) { //como o EXTR é só "label extr" sempre, aqui ele identifica o extr, coloca o label dele numa tabela auxiliar
                        ExtLabel teste = new ExtLabel(args[0]);
                        extLabels.add(teste);
                        //tabelaUsoAux.put(args[0], -1); //coloca na tabela auxiliar
                    }
                    if (args[0].equals("EXTDEF")) {
                        if (args[1].equals(ext)) {
                            tabelaDeSimbolos.remove(ext);
                            tabelaDefinicoes.put(ext, 0);
                        } else
                            tabelaDefinicoes.put(args[1], -1);
                    }
                    if (args.length == 2) {
                        if (args[1].equals("START")) {
                            tabelaDeSimbolos.put(args[0], 0);
                            ext = args[0];
                        }
                    }
                    linha = reader.readLine(); // lê a próxima linha
                    args = removeAdressType(linha.split(" "));  //formata; depois disso tudo só segue como se nada tivesse acontecido
                }
            }

            insereLabels(args, lineCounter);
            if(args.length > 1) { //verifica se a linha tem mais de uma string (ex.: se não é só um STOP) pra saber se tem operandos nela
                insereTabelaUso(args, extLabels); //função responsável por inserir as coisas na tabela de uso
            }
            lineCounter += 1;
            linha = reader.readLine();
        }
        Set<String> set = tabelaDefinicoes.keySet();
        for (String key : set) {
            tabelaDefinicoes.replace(key, tabelaDeSimbolos.get(key));
        }

    }

    public void insereTabelaUso(String[] args, List<ExtLabel> extLabels) { //função responsável por inserir as coisas na tabela de uso
        for(int m = 0; m < args.length; m++) { //itera sobre a linha para verificar cada uma das partes
            for(int n = 0; n < extLabels.size(); n++) { //itera sobre a lista de itens do tipo ExtLabel
                if(extLabels.get(n).nome.equals(args[m])) //verifica a igualdade entre o nome do Extlabel e o args nas posições correspondentes
                    extLabels.get(n).posicoes.add(lineCounter); //se forem iguais, quer dizer que aquele label é externo e sua posição deve ser adicionada na tabela como um uso do símbolo
            }
        }
    }
//public void preencheListaSimbolos () throws FileNotFoundException {
//    Reader reader = new Reader("EntradaTeste.txt");
//    Integer cont = 0;
//    String linha = reader.readLine();
//    while (linha != null) {
//        String[] args = linha.split(" ");
//        args = removeAdressType(args);  //tira todos as formas de endereçamento presentes
//        //alguém pelo amor me ajuda a avaliar essa merda aqui
//        if(args[1].equals("EXTR")) { //como o EXTR é só "label extr" sempre, aqui ele identifica o extr, coloca o label dele numa tabela auxiliar e passa direto pra próxima linha
//            tabelaUsoAux.put(args[0], -1); //coloca na tabela auxiliar
//            linha = reader.readLine(); // lê a próxima linha
//            args = removeAdressType(linha.split(" "));  //formata; depois disso tudo só segue como se nada tivesse acontecido
//        }
//        //**
//        if (args[0].equals("EXTDEF")) {
//            tabelaDefinicoes.put(args[1], -1);
//        }
//        for (String arg : args)
//            existe(arg, cont);
//
//        linha = reader.readLine();
//    }
//    Set<String> set = tabelaDefinicoes.keySet();
//    for (String key: set) {
//        tabelaDefinicoes.replace(key, tabelaDeSimbolos.get(key));
//    }
//}


//    public void preencheListaSimbolos () throws FileNotFoundException {
//        Reader reader = new Reader("EntradaTeste.txt");
//        Integer cont = 0;
//        String linha = reader.readLine();
//        while (linha != null) {
//            String[] args = linha.split(" ");
//            args = removeAdressType(args);  //tira todos as formas de endereçamento presentes
//            if (args[0].equals("EXTDEF")) {
//                tabelaDefinicoes.put(args[1], -1);
//            }
//            for (String arg : args)
//                existe(arg, cont);
//
//            linha = reader.readLine();
//        }
//        Set<String> set = tabelaDefinicoes.keySet();
//        for (String key: set) {
//            tabelaDefinicoes.replace(key, tabelaDeSimbolos.get(key));
//        }
//    }

    //considerando que label só pode ter endereçamente indireto, ele verifica se tem , e I no fim de cada uma das strings do array
    public String[] removeAdressType(String[] argumento) {
        for (int i = 0; i < argumento.length; i++) { //percorre as strings que estão no array
            if (!instrucoes.containsKey(argumento[0])) { //verifica se é uma instrução
                if (argumento[i].charAt(argumento[i].length() - 1) == 'I' && argumento[i].charAt(argumento[i].length() - 2) == ',') { //se não é instrução, verifica se tem endereçamento indireto
                    argumento[i] = argumento[i].substring(0, argumento[i].length() - 2); //es tiver endereçamento indireto, coloca uma nova string igual a primeira, mas sem ",I" no fim no array
                }
            }
        }
        return argumento; //retorna a nova string, semelhante a original, porém agora os operandos estão sem a marcasção do tipo de endereçamento
    }

    public void insereLabels(String[] argumento,Integer cont) {
        if (!instrucoes.containsKey(argumento[0])) {
            tabelaDeSimbolos.put(argumento[0], cont);
        }
    }

    public void pseudoinstructionsListInit() {
        pseudoinstrucoes.put("EXTDEF", 1);
        pseudoinstrucoes.put("EXTR", 0);
        pseudoinstrucoes.put("STACK", 1);
        pseudoinstrucoes.put("START", 1);
    }

    //todas as palavras que não forem uma dessas são labels
    public void intructionsListInit() {
        instrucoes.put("ADD", 2);
        instrucoes.put("BR", 2);
        instrucoes.put("BRNEG", 2);
        instrucoes.put("BRPOS", 2);
        instrucoes.put("BRZERO", 2);
        instrucoes.put("DIVIDE", 2);
        instrucoes.put("LOAD", 2);
        instrucoes.put("MULT", 2);
        instrucoes.put("READ", 2);
        instrucoes.put("STORE", 2);
        instrucoes.put("SUB", 2);
        instrucoes.put("WRITE", 2);
        instrucoes.put("CALL", 2);
        instrucoes.put("STOP", 1);
        instrucoes.put("COPY", 3);
        instrucoes.put("RET", 1);
        instrucoes.put("SPACE", 0);
        instrucoes.put("CONST", 1);
        instrucoes.put("END", 1);
    }
//RAQUEL TERMINOU DE MEXER

//ORIGINAL
//    public void preencheListaSimbolos () throws FileNotFoundException{
//        Reader reader = new Reader("EntradaTeste.txt");
//        Integer cont = 0;
//        String linha = reader.readLine();
//        while(linha!=null) {
//            String[] args = linha.split(" ");
//            if(args[0].equals("EXTDEF")){
//                tabelaDefinicoes.put(args[1],-1);
//            }
//            for (String arg : args)
//                existe(arg, cont);
//
//            linha = reader.readLine();
//        }
//        Set<String> set = tabelaDefinicoes.keySet();
//        for(String key: set){
//            tabelaDefinicoes.replace(key,tabelaDeSimbolos.get(key));
//        }
//
//    }

//    public void existe(String argumento,Integer cont){
//        if(!instrucoes.containsKey(argumento)){
//            if(!tabelaDeSimbolos.containsKey(argumento))
//                tabelaDeSimbolos.put(argumento,cont);
//            else
//                if(tabelaDeSimbolos.get(argumento) == -1)
//                    tabelaDeSimbolos.replace(argumento,cont);
//        }
//    }
// ORIGINAL


        //todas as palavras que não forem uma dessas são labels
       /* public void intructionsListInit () {
            instrucoes.put("ADD", 2);
            instrucoes.put("BR", 2);
            instrucoes.put("BRNEG", 2);
            instrucoes.put("BRPOS", 2);
            instrucoes.put("BRZERO", 2);
            instrucoes.put("DIVIDE", 2);
            instrucoes.put("LOAD", 2);
            instrucoes.put("MULT", 2);
            instrucoes.put("READ", 2);
            instrucoes.put("STORE", 2);
            instrucoes.put("SUB", 2);
            instrucoes.put("WRITE", 2);
            instrucoes.put("CALL", 2);
            instrucoes.put("STOP", 1);
            instrucoes.put("COPY", 3);
            instrucoes.put("RET", 1);
            instrucoes.put("EXTDEF", 1);
            instrucoes.put("EXTR", 0);
            instrucoes.put("START", 1);
            instrucoes.put("END", 1);
            instrucoes.put("STACK", 1);
            instrucoes.put("SPACE", 0);
            instrucoes.put("CONST", 1);
        }*/

        //Proposta dessa função:
        //Recebe a linha inteira do comando e verifica inicialmente verifica o primeiro componente:
        //Se for uma expressão de operação, vai verificar se os operandos são labels. Se forem, é verificada sua existência na tabela de símbolos para inserção
        //Se for diretamente um label, verifica a existência dele na tabela, se não existir, insere com o endereço correspondente. Se existe, insere o endereço (ainda não feito)
        //talvez seja melhor quebrar isso em mais funçções?

        public int getAmountOperands (String instrucao) throws IOException { // Pegar quantidade de operandos
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
        public void escreveBinarioTxt (String instrucao, FileWriter
        arq, ArrayList < Integer > operandos, Map < String, String > opcodeInstrucao) throws IOException {
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
                    List<String> atributos = getEnderecamento(operandos.get(0));
                    arq.write("00000000" + atributos.get(0) + opcodeInstrucao.get(instrucao) + atributos.get(1));
                    //atualizamos o location counter após escrever no binário?
                    //locationCounter += 2
                    break;
                case "COPY":
                    List<String> atributos2 = getEnderecamento(operandos.get(0), operandos.get(1));
                    arq.write("00000000" + atributos2.get(0) + opcodeInstrucao.get(instrucao) + atributos2.get(1) + atributos2.get(2));  // BITS -SIGNIFICATIVOS, ENDEREÇAMENTO, OPCODE, OPD1 E OPD2
                    //atualizamos o location counter após escrever no binário?
                    //locationCounter += 3
                    break;
                case "RET":
                case "STOP":
                    arq.write("00000000" + "000" + opcodeInstrucao.get(instrucao));
                    //atualizamos o location counter após escrever no binário?
                    //locationCounter += 1
                    break;
                default:
                    arq.write("Instrução inválida.");
            }
            //como contador de linhas sempre vai ser 1 a mais que o anterior
            ////lineCounter += 1
        }

        public List<String> getEnderecamento (Integer operando1){
            String resultado = "000";
            String operando1String = operando1.toString();
            char firstOp1 = operando1String.charAt(0);
            char lastOp1 = operando1String.charAt(operando1String.length());

            if (firstOp1 == '#')
                resultado = "100";

            if (lastOp1 == 'I')
                resultado = "001";

            List<String> resultList = removeSimbol(operando1.toString(), "0");
            resultList.add(0, resultado); // primeiro item endereçamento, 2 opd1 e 3 opd2
            return resultList;
        }
        public List<String> getEnderecamento (Integer operando1, Integer operando2){
            String resultado = "000";                                            // direto por default

            String operando1String = operando1.toString();
            char firstOp1 = operando1String.charAt(0);                           // Testa primeiro caractere
            char lastOp1 = operando1String.charAt(operando1String.length());     // Testa ultimo operando

            String operando2String = operando2.toString();
            char firstOp2 = operando2String.charAt(0);
            char lastOp2 = operando2String.charAt(operando2String.length());

            if (firstOp2 == '#')                                                  // Verificações de endereçamento de operandos
                if (firstOp1 != '#' && lastOp1 != 'I')
                    resultado = "100"; // imediato
                else resultado = "101";

            if (lastOp2 == 'I')
                if (firstOp1 != '#' && lastOp1 != 'I')
                    resultado = "010";
                else resultado = "011";

            if (lastOp1 == 'I')
                resultado = "001";

            List<String> resultList = removeSimbol(operando1.toString(), operando2.toString());
            resultList.add(0, resultado); // primeiro item endereçamento, 2 opd1 e 3 opd2
            return resultList;

        }

        public List<String> removeSimbol (String operando1, String operando2){
            String newOpd1 = operando1;
            String newOpd2 = operando2;

            String operando1String = operando1.toString();
            char firstOp1 = operando1String.charAt(0);                           // Testa primeiro caractere
            char lastOp1 = operando1String.charAt(operando1String.length());     // Testa ultimo operando

            String operando2String = operando2.toString();
            char firstOp2 = operando2String.charAt(0);
            char lastOp2 = operando2String.charAt(operando2String.length());

            if (firstOp1 == '#')
                newOpd1 = operando1.substring(1, operando1.length());

            if (lastOp1 == 'I')
                newOpd1 = operando1.substring(0, operando1.length() - 1);

            if (firstOp2 == '#')
                newOpd2 = operando1.substring(1, operando2.length());

            if (lastOp2 == 'I')
                newOpd2 = operando1.substring(0, operando2.length() - 1);

            newOpd1 = Integer.toBinaryString(Integer.parseInt(newOpd1));
            newOpd2 = Integer.toBinaryString(Integer.parseInt(newOpd2));

            List<String> operandos = new ArrayList<>();
            operandos.add(1, newOpd1);
            operandos.add(2, newOpd2);

            return operandos;
        }

}