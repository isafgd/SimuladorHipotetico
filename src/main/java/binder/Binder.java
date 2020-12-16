package binder;

import charger.Charger;
import executor.Reader;
import executor.Writer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Binder {

    private static StringBuilder instructions = new StringBuilder();

    public void process () throws IOException {

        Reader output1 = new Reader("Output1Montador.txt");
        Reader output2 = new Reader("Output2Montador.txt");
        Reader tbl_uso1 = new Reader("TabelaUso1.txt");
        Reader tbl_uso2 = new Reader("TabelaUso2.txt");
        Reader tbl_definicao1 = new Reader("TabelaDefinicao1.txt");
        Reader tbl_definicao2 = new Reader("TabelaDefinicao2.txt");

        List<Integer> address = firstRead(output1,19,240);
        Writer.writeFile(instructions.toString(), "OutputFirstStep1.txt");

        instructions.delete(0,instructions.length());
        firstRead(output2,address.get(0),address.get(1));
        Writer.writeFile(instructions.toString(), "OutputFirstStep2.txt");

        instructions.delete(0,instructions.length());
        secondRead("OutputFirstStep1.txt","OutputFirstStep2.txt",tbl_uso1,tbl_definicao2);
        secondRead("OutputFirstStep2.txt","OutputFirstStep1.txt",tbl_uso2,tbl_definicao1);

        Writer.writeFile(instructions.toString(), "OutputLigador.txt");

    }

    public List<Integer> firstRead (Reader output, Integer firstInstruction, Integer firstData){
        List<Integer> finalAddress = new ArrayList<Integer>();
        Integer increment = firstData;

        String line = output.readLine();
        while(line!=null){
            StringBuilder newLine = new StringBuilder();
            int addressMode = Integer.parseInt(line.substring(8, 11), 2);
            if(line.length()==32){
                if(addressMode==4){
                    newLine.append(firstInstruction.toString());
                    newLine.append(";");
                    newLine.append(line.substring(0,16));
                    newLine.append(";");
                    newLine.append(firstData.toString());
                    newLine.append(";");
                    newLine.append(line.substring(16,32));

                    firstData++;
                    firstInstruction++;
                }
                else{
                    int opd = Integer.parseInt(line.substring(16, 32),  2);
                    int newOpd = opd + increment;
                    String binOpd = toBinary(newOpd,16);
                    newLine.append(firstInstruction.toString());
                    newLine.append(";");
                    newLine.append(line.substring(0,16));
                    newLine.append(";");
                    newLine.append(firstData.toString());
                    newLine.append(";");
                    newLine.append(binOpd);

                    firstData++;
                    firstInstruction++;
                }
            }else if (line.length()==48){
                if (addressMode==4){
                    int opd = Integer.parseInt(line.substring(16, 32),  2);
                    int newOpd = opd + increment;
                    String binOpd = toBinary(newOpd,16);
                    newLine.append(firstInstruction.toString());
                    newLine.append(";");
                    newLine.append(line.substring(0,16));
                    newLine.append(";");
                    newLine.append(firstData.toString());
                    newLine.append(";");
                    newLine.append(binOpd);

                    firstData++;

                    newLine.append(";");
                    newLine.append(firstData.toString());
                    newLine.append(";");
                    newLine.append(line.substring(32,48));

                    firstData++;
                    firstInstruction++;
                }else {
                    int opd = Integer.parseInt(line.substring(16, 32), 2);
                    int newOpd = opd + increment;
                    String binOpd = toBinary(newOpd, 16);
                    newLine.append(firstInstruction.toString());
                    newLine.append(";");
                    newLine.append(line.substring(0, 16));
                    newLine.append(";");
                    newLine.append(firstData.toString());
                    newLine.append(";");
                    newLine.append(binOpd);

                    firstData++;

                    int opd2 = Integer.parseInt(line.substring(32, 48), 2);
                    int newOpd2 = opd2 + increment;
                    String binOpd2 = toBinary(newOpd2, 16);
                    newLine.append(";");
                    newLine.append(firstData.toString());
                    newLine.append(";");
                    newLine.append(binOpd2);

                    firstData++;
                    firstInstruction++;
                }

            }else{
                if (line.charAt(0) == '1'){
                    newLine.append(firstData.toString());
                    newLine.append(";");
                    newLine.append("0");
                    newLine.append(line.substring(1,16));

                    firstData++;
                }else{
                    newLine.append(firstInstruction.toString());
                    newLine.append(";");
                    newLine.append(line.substring(0, 16));
                    firstInstruction++;
                }
            }
            instructions.append(newLine+"\n");
            line = output.readLine();
        }

        finalAddress.add(0,firstInstruction);
        finalAddress.add(1,firstData);
        return finalAddress;
    }

    public void secondRead (String arquivo, String arquivo_aux, Reader tbl_uso, Reader tbl_definicao) throws FileNotFoundException {
        Reader output = new Reader(arquivo);
        Reader output_aux = new Reader(arquivo_aux);
        String line = output.readLine();
        String tblUsoLine = tbl_uso.readLine();
        String tblDefLine = tbl_definicao.readLine();
        String lineAux = output_aux.readLine();

        int lineCont = 0;
        while(line!=null){
            StringBuilder newLine = new StringBuilder();
            String address = null;
            String[] splitLine = line.split(";");
            if (splitLine.length>2) {
                if (splitLine[3].equals("0000000000000000")) {
                    boolean test = true;
                    while (test) {
                        String[] splitUso = tblUsoLine.split(";");
                        if (splitUso[0].equals(Integer.toString(lineCont))) {
                            String var = splitUso[1];
                            boolean testDef = true;
                            while (testDef) {
                                String[] splitDef = tblDefLine.split(";");
                                if (splitDef[1].equals(var)) {
                                    address = splitDef[0];
                                    testDef = false;
                                } else {
                                    tblDefLine = tbl_definicao.readLine();
                                }
                            }
                            test = false;
                        } else {
                            tblUsoLine = tbl_uso.readLine();
                        }
                    }

                    int contAux = 0;
                    test = true;
                    while (test) {
                        if (contAux == (Integer.parseInt(address))) {
                            String[] splitAux = lineAux.split(";");
                            newLine.append(splitLine[0]).append(";").append(splitLine[1]).append(";").append(splitLine[2]).append(";");
                            String newOpd = toBinary((Integer.parseInt(splitAux[0])), 16);
                            newLine.append(newOpd);
                            test = false;
                        } else {
                            lineAux = output_aux.readLine();
                            contAux++;
                        }
                    }
                    instructions.append(newLine).append("\n");

                }else {
                    instructions.append(line).append("\n");
                }
            }else{
                instructions.append(line).append("\n");
            }
            line = output.readLine();
            lineCont++;
        }

    }

    public String toBinary(int x, int len) {

        if (len > 0) {
            return String.format("%" + len + "s",
                    Integer.toBinaryString(x)).replaceAll(" ", "0");
        }
        return null;
    }

}
