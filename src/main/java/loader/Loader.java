package loader;

import executor.Reader;
import executor.Memory;
import executor.MemoryList;
import javafx.collections.ObservableList;
import javafx.scene.control.TextArea;

import java.io.IOException;

public class Loader {

    public void initialMemory(Memory memory, ObservableList<MemoryList> list, TextArea console) throws IOException {
        Reader reader = new Reader("OutputLigador.txt");
        String line = reader.readLine();
        boolean limit = false;
        while (line != null && !limit) {
            String[] spliLine = line.split(";");
            if (spliLine[1].charAt(0) == '1') {
                Integer constante = Integer.parseInt(spliLine[1].substring(1, 16), 2);
                memory.set_element(Integer.parseInt(spliLine[0]), constante);
                list.set(Integer.parseInt(spliLine[0]), new MemoryList(spliLine[0], constante.toString()));
            } else {
                memory.set_string(Integer.parseInt(spliLine[0]), spliLine[1]);
                list.set(Integer.parseInt(spliLine[0]), new MemoryList(spliLine[0], spliLine[1]));
            }
                if (spliLine.length == 4) {
                    Integer constante = Integer.parseInt(spliLine[3], 2);
                    memory.set_element(Integer.parseInt(spliLine[2]), constante);
                    list.set(Integer.parseInt(spliLine[2]), new MemoryList(spliLine[2], constante.toString()));
                    if (Integer.parseInt(spliLine[2]) == 499) {
                        limit = true;
                        console.setText("Memory Limit Data");
                    }
                } else {
                    if (spliLine.length > 4) {
                        Integer constante = Integer.parseInt(spliLine[3], 2);
                        memory.set_element(Integer.parseInt(spliLine[2]), constante);
                        list.set(Integer.parseInt(spliLine[2]), new MemoryList(spliLine[2], constante.toString()));
                        Integer constante2 = Integer.parseInt(spliLine[5], 2);
                        memory.set_element(Integer.parseInt(spliLine[4]), constante2);
                        list.set(Integer.parseInt(spliLine[4]), new MemoryList(spliLine[4], constante2.toString()));
                        if (Integer.parseInt(spliLine[4]) == 499) {
                            limit = true;
                            console.setText("Memory Limit Data");
                        }
                    }
                }
                line = reader.readLine();

                if (Integer.parseInt(spliLine[0]) == 239) {
                    limit = true;
                    console.setText("Memory Limit Instructions");
                }
            }

        }
    }