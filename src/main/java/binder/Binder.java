package binder;

import executor.Reader;
import executor.Writer;

import java.io.IOException;

public class Binder {

    public void firstPassage () throws IOException {
        Reader reader = new Reader("OutPut1Montador.txt");
        Reader reader2 = new Reader("OutPut1Montador.txt");

        StringBuilder instructions = new StringBuilder();
        String line = reader.readLine();

        while (line!=null){
            String newLine = null;
            //newLine = line.substring(0,16);
            int addressMode = Integer.parseInt(line.substring(8, 11), 2);
            if(line.length()==32){
                if(addressMode==4){
                    newLine = line;
                }
                else{
                    int opd = Integer.parseInt(line.substring(16, 32),  2);
                    int newOpd = opd + 240;
                    String binOpd = toBinary(newOpd,16);
                    newLine = line.substring(0,16) + binOpd;
                }
            }else if (line.length()==48){
                
            }
            instructions.append(newLine+"\n");
            line = reader.readLine();
        }

        line = reader2.readLine();

        while (line!=null){
            instructions.append(line+"\n");
            line = reader.readLine();
        }

        Writer.writeFile(instructions.toString(), "OutPutLigador.txt");
    }

    public String toBinary(int x, int len) {

        if (len > 0) {
            return String.format("%" + len + "s",
                    Integer.toBinaryString(x)).replaceAll(" ", "0");
        }
        return null;
    }

}
