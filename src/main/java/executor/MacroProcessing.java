package executor;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

public class MacroProcessing {

    public StringBuilder fileContent;
    public Map<String,String> macros;

    public MacroProcessing(){
        fileContent = new StringBuilder();
        macros = new HashMap<>();
    }

    public void convertToObjectFont () throws FileNotFoundException {
            Reader reader = new Reader("RawFontFile.txt");
            fileContent.append(reader.readLine() + "\n\n");
            reader.readLine();
            String[] arr;
            String line = reader.readLine();
            while(line != null){
                if(line.equals("MACRO")){
                    String key = reader.readLine();
                    arr = key.split(" ", 3);
                    StringBuilder value = new StringBuilder();
                    String aux = reader.readLine();
                    while(!aux.equals("MEND")){
                        value.append(aux + "\n");
                        aux = reader.readLine();
                    }
                    if(arr[0].charAt(0) == '&')
                        macros.put(arr[1],value.toString());
                    else
                        macros.put(arr[0],value.toString());
                }else{
                    if(line.equals("")){
                        replaceMacros(reader);
                    }
                }
                line = reader.readLine();
            }

            Writer.writeFile(fileContent.toString(),"ProcessedFontFile.txt" );

    }

    public void replaceMacros(Reader reader){
        String line = reader.readLine();
        String[] arr;
        String method;
        while(line!= null) {
            if (!line.equals("")) {
                arr = line.split(" ", 3);
                if (arr[0].charAt(0) == '&')
                    method = arr[1];
                else
                    method = arr[0];
                if (macros.containsKey(method)) {
                    fileContent.append(macros.get(line));
                } else {
                    fileContent.append(line.substring(1,line.length()) + "\n");
                }
                line = reader.readLine();
            }else {
                fileContent.append("\n");
                line = reader.readLine();
            }
        }

    }

}