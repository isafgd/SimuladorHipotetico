package charger;

import ReadingFile.Read;
import executor.Memory;
import executor.MemoryList;
import executor.Operations;
import javafx.collections.ObservableList;
import javafx.scene.control.TextArea;

import java.io.IOException;

public class Charger extends Operations {

    public void initialMemory(Memory memory, ObservableList<MemoryList> list, TextArea console) throws IOException {
        Read reader = new Read();
        String line = reader.readLine();
        Integer i = 19;
        Integer j = 240;
        while (line!=null && i<240 && j<500){
            memory.set_string(i, line.substring(0, 16));
            list.set(i, new MemoryList(i.toString(), line.substring(0, 16)));
            if(line.length()==32){
                memory.set_string(j, line.substring(16, 32));
                list.set(j, new MemoryList(j.toString(), line.substring(16, 32)));
                j++;
            }else{
                if(line.length()>32) {
                    Integer k = j + 1;
                    memory.set_string(j, line.substring(16, 32));
                    list.set(j, new MemoryList(j.toString(), line.substring(16, 32)));
                    memory.set_string(k, line.substring(32, 48));
                    list.set(j, new MemoryList(k.toString(), line.substring(32, 48)));
                    j = j + 2;
                }
            }
            i++;
            line = reader.readLine();
        }
        if (i == 239 || j == 499){
            console.setText("Memory Limit");
        }
    }

}