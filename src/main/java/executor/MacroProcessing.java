package executor;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MacroProcessing {

    public StringBuilder fileContent;
    public Map<String,String> macros;

    public MacroProcessing(){
        fileContent = new StringBuilder();
        macros = new HashMap<>();
    }

    public void convertToObjectFont (String num) throws FileNotFoundException {
            Reader reader = new Reader("RawFile"+ num + ".txt");
            fileContent.append(reader.readLine() + "\n\n");
            reader.readLine();
            String[] arr;
            String line = reader.readLine();
            while(line != null){
                if(line.equals("MACRO")){
                    String key = reader.readLine();
                    arr = key.split(" ");
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


            removeSimbols();
            Writer.writeFile(fileContent.toString(),"Montador"+ num + ".txt" );

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
                        fileContent.append(line + "\n");
                }
                line = reader.readLine();
            }else {
                fileContent.append("\n");
                line = reader.readLine();
            }
        }

    }
    public void removeSimbols(){
        StringBuilder processedArgs = new StringBuilder();
        String arg = fileContent.toString();
        String[] args = arg.split("\n");
        for(int i = 0; i< args.length;i++){
            if(!"".equals(args[i])) {
                if (args[i].charAt(0) == '&') {
                    processedArgs.append(args[i].substring(1) + "\n");
                } else
                    processedArgs.append(args[i] + "\n");
            }else
                processedArgs.append(args[i] + "\n");
        }
        fileContent.delete(0,fileContent.length()) ;
        fileContent.append(processedArgs.toString());
    }

}
