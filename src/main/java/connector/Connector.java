package connector;

import executor.Reader;
import executor.Writer;
import javafx.scene.control.TextArea;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Connector {

    private static StringBuilder instructions = new StringBuilder();

    public void process (TextArea console1, TextArea console2) throws IOException {

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

        console1.setText(instructions.toString());
        console2.setText(instructions.toString());
    }

    public List<Integer> firstRead (Reader output, Integer firstInstruction, Integer firstData){
        List<Integer> finalAddress = new ArrayList<Integer>();
        Integer increment = firstData;

        String line = output.readLine();
        while(line!=null){
            StringBuilder newLine = new StringBuilder();
            int addressMode = Integer.parseInt(line.substring(8, 11), 2);
            if(line.length()==32){
                if(addressMode==4 || line.substring(16, 32).equals("0000000000000000")){
                    newLine = rideLine(line.substring(0,16),line.substring(16,32),firstInstruction,firstData);
                    firstData++;
                    firstInstruction++;
                }
                else{
                    String binOpd = setOpd(line.substring(16, 32),increment);
                    newLine = rideLine(line.substring(0, 16),binOpd,firstInstruction,firstData);
                    firstData++;
                    firstInstruction++;
                }
            }else if (line.length()==48){
                if (addressMode==4){
                    String binOpd = setOpd(line.substring(16, 32),increment);
                    newLine = rideLine(line.substring(0,16),binOpd,firstInstruction,firstData);
                    firstData++;

                    rideLineSingle(newLine,line.substring(32,48),firstData);
                    firstData++;
                    firstInstruction++;
                }else {
                    if(!line.substring(16, 32).equals("0000000000000000")) {
                        String binOpd = setOpd(line.substring(16, 32),increment);
                        newLine = rideLine(line.substring(0, 16),binOpd,firstInstruction,firstData);
                        firstData++;
                    }else{
                        newLine = rideLine(line.substring(0,16),line.substring(16,32),firstInstruction,firstData);
                        firstData++;
                    }

                    if(!line.substring(16, 32).equals("0000000000000000")) {
                        String binOpd2 = setOpd(line.substring(32, 48),increment);
                        rideLineSingle(newLine,binOpd2,firstData);
                        firstData++;
                        firstInstruction++;
                    }else{
                        rideLineSingle(newLine,line.substring(32,48),firstData);
                        firstData++;
                        firstInstruction++;
                    }
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
            instructions.append(newLine).append("\n");
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

    public StringBuilder rideLine (String line1, String line2,Integer instructionAddress, Integer dataAddress){
        StringBuilder newLine = new StringBuilder();

        newLine.append(instructionAddress.toString());
        newLine.append(";");
        newLine.append(line1);
        newLine.append(";");
        newLine.append(dataAddress.toString());
        newLine.append(";");
        newLine.append(line2);

        return newLine;
    }

    public void rideLineSingle (StringBuilder newLine, String line, Integer dataAddress){
        newLine.append(";");
        newLine.append(dataAddress.toString());
        newLine.append(";");
        newLine.append(line);
    }

    public String setOpd (String line, Integer increment){
        int opd = Integer.parseInt(line, 2);
        int newOpd = opd + increment;
        return toBinary(newOpd, 16);
    }

}
