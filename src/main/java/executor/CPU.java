package executor;


import executor.recorders.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import lombok.Data;

import java.io.IOException;
import java.util.ArrayList;

@Data
public class CPU extends Application {

    @FXML
    private Button initialButton;

    @FXML
    private TextArea textArea;

    /*Criacao da interface*/
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/sample.fxml"));
        primaryStage.setTitle("Simulador");
        Scene scene = new Scene(root, 1000, 1450);
        primaryStage.setScene(scene);
        scene.getStylesheets().add(getClass().getResource("/css/Style.css").toExternalForm());
        primaryStage.initStyle(StageStyle.UTILITY);
        primaryStage.setMinHeight(1000);
        primaryStage.setMaxHeight(1000);
        primaryStage.setMaxWidth(1450);
        primaryStage.setMinWidth(1450);
        primaryStage.show();
    }

    public static void main(String[] args) throws IOException {
        launch(args);
    }

    /*Adiciona as intrucoes e os operandos na memoria*/
/*    public void initialMemory(Memory memory, ObservableList<MemoryList> list, TextArea console) throws IOException {
        Reader reader = new Reader();
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
    }*/

    /*Redireciona para o modo de execucao*/
    public static void executionMode(Memory memory, String choiceString, ObservableList<MemoryList> list, TextArea console){
        int choice = 0;
        switch (choiceString){
            case "Continuo":
                choice = 0;
                break;
            case "Semi-Continuo":
                choice = 1;
                break;
            case "Depuracao":
                choice = 2;
                break;
        }
        OperationMode MOP = (OperationMode) memory.get(16);
        MOP.setMop(choice);
        list.set(16,new MemoryList("MOP",MOP.getMop().toString()));
        if (choice==0){
            continuousMode(memory,list, console);
        }else{
            if(choice==1) {
                semiContinuousMode(memory,list, console);
            }
        }
    }

    public static void continuousMode(Memory memory, ObservableList<MemoryList> list, TextArea console){
        ProgramCounter PC = (ProgramCounter) memory.get(14);
        AddressRecorder RE = (AddressRecorder) memory.get(18);
        while((String) memory.get(PC.getPc()) != null) {
            ArrayList<Integer> attributes = convert(memory, (String) memory.get(PC.getPc()), (String) memory.get(RE.getRe()),list);
            execute(memory, attributes, list, console);
        }
    }

    /*Execucao agendada a cada 5seg */
    public static void semiContinuousMode(Memory memory,ObservableList<MemoryList> list, TextArea console){
        ScheduledService scheduledService = new ScheduledService() {
            @Override
            protected Task createTask() {
                return new Task() {
                    @Override
                    protected String call() throws Exception {
                        ProgramCounter PC = (ProgramCounter) memory.get(14);
                        if((String) memory.get(PC.getPc()) != null) {
                            Platform.runLater(() -> executeSemiContinuos(memory,list,console));
                        }
                        return null;
                    }
                };
            }
        };
        scheduledService.setPeriod(Duration.seconds(5));
        scheduledService.start();
    }

    public static void executeSemiContinuos(Memory memory, ObservableList<MemoryList> list, TextArea console){
        ProgramCounter PC = (ProgramCounter) memory.get(14);
        AddressRecorder RE = (AddressRecorder) memory.get(18);
        ArrayList<Integer> attributes = convert(memory, (String) memory.get(PC.getPc()), (String) memory.get(RE.getRe()),list);
        execute(memory, attributes, list, console);
    }

    public static void debugMode(Memory memory, ObservableList<MemoryList> list, TextArea console){
        ProgramCounter PC = (ProgramCounter) memory.get(14);
        AddressRecorder RE = (AddressRecorder) memory.get(18);
        ArrayList<Integer> attributes = convert(memory, (String) memory.get(PC.getPc()), (String) memory.get(RE.getRe()),list);
        execute(memory, attributes, list, console);
    }

    /*Converte a instrucao para decimal*/
    public static ArrayList<Integer> convert (Memory memory, String line, String data,ObservableList<MemoryList> list){
        InstructionRecorder RI = (InstructionRecorder) memory.get(17);
        RI.setRi(getOpcode(line));
        list.set(17,new MemoryList("RI", RI.getRi().toString()));
        ArrayList<Integer> attributes = new ArrayList<Integer>();
        if (RI.getRi()!=9 && RI.getRi()!=13 && RI.getRi()!=11) {
            attributes.add(getAddressMode(line));
                attributes.add(getFirstOP(data));
        }else{
            if (RI.getRi() == 13){
                attributes.add(getAddressMode(line));
                attributes.add(getFirstOP(data));
                AddressRecorder RE = (AddressRecorder) memory.get(18);
                RE.setRe(RE.getRe() + 1);
                attributes.add(getSecondOP((String) memory.get(RE.getRe())));
            }
        }

        return attributes;
    }

    /*Chama a instrucao correspondente*/
    public static void execute (Memory memory, ArrayList<Integer> attributes, ObservableList<MemoryList> list, TextArea console){
        InstructionRecorder RI = (InstructionRecorder) memory.get(17);
        Operations operation = new Operations();
        switch (RI.getRi()) {
            case 0:
                operation.br((ProgramCounter) memory.get(14), attributes.get(1), attributes.get(0), memory, list);
                break;
            case 1:
                operation.brPos((ProgramCounter) memory.get(14), (Accumulator) memory.get(15), attributes.get(1), attributes.get(0), memory, list);
                break;
            case 2:
                operation.add((Accumulator) memory.get(15), attributes.get(1), attributes.get(0), memory, list);
                break;
            case 3:
                operation.load((Accumulator) memory.get(15), attributes.get(1), attributes.get(0), memory, list);
                break;
            case 4:
                operation.brZero((ProgramCounter) memory.get(14), (Accumulator) memory.get(15), attributes.get(1), attributes.get(0), memory, list);
                break;
            case 5:
                operation.brNeg((ProgramCounter) memory.get(14), (Accumulator) memory.get(15), attributes.get(1), attributes.get(0), memory, list);
                break;
            case 6:
                operation.sub((Accumulator) memory.get(15), attributes.get(1), attributes.get(0), memory, list);
                break;
            case 7:
                operation.store((Accumulator) memory.get(15), attributes.get(1), memory, attributes.get(0), list);
                break;
            case 8:
                operation.write(attributes.get(1), attributes.get(0), memory, list, console);
                break;
            case 9:
                operation.ret((ProgramCounter) memory.get(14), (StackPointer) memory.get(13), memory, list, console);
                break;
            case 10:
                operation.divide((Accumulator) memory.get(15), attributes.get(1), attributes.get(0), memory, list);
                break;
            case 11:
                operation.stop();
                break;
            case 12:
                operation.read(attributes.get(0), attributes.get(1), memory, list, "");
                break;
            case 13:
                operation.copy(attributes.get(1), attributes.get(2), attributes.get(0), memory, list);
                break;
            case 14:
                operation.multi((Accumulator) memory.get(15), attributes.get(1), attributes.get(0), memory, list);
                break;
            case 15:
                operation.call((StackPointer) memory.get(13), (ProgramCounter) memory.get(14), attributes.get(1), memory, attributes.get(0), list, console);
                break;
        }
    }

    public static Integer getOpcode(String instruction) {
        return Integer.parseInt(instruction.substring(11, 16), 2);
    }

    public static Integer getAddressMode(String instruction) {
        return Integer.parseInt(instruction.substring(8, 11), 2);
    }

    public static Integer getFirstOP(String instruction) {
        if (isNegative(instruction,0)){
            return (Integer.parseInt(instruction.substring(1, 16),  2) * -1);
        }else{
            return Integer.parseInt(instruction.substring(1, 16),  2);
        }
    }

    public static Integer getSecondOP(String instruction) {
        if (isNegative(instruction,0)){
            return (Integer.parseInt(instruction.substring(1, 16), 2) * -1);
        }else{
            return Integer.parseInt(instruction.substring(1, 16), 2);
        }
    }

    public static Boolean isNegative(String instruction, int bit){
        if (instruction.charAt(bit) == '1'){
            return true;
        }else{
            return false;
        }
    }

}
