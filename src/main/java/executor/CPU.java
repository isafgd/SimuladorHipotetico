package executor;

import ReadingFile.Read;
import executor.recorders.*;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.Data;

import java.io.IOException;
import java.util.ArrayList;

@Data
public class CPU extends Application {

    @FXML
    private Button initialButton;

    @FXML
    private TextArea textArea;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/sample.fxml"));
        primaryStage.setTitle("Simulador");
        Scene scene = new Scene(root, 1000, 900);
        primaryStage.setScene(scene);
        scene.getStylesheets().add(getClass().getResource("/css/Style.css").toExternalForm());
        primaryStage.initStyle(StageStyle.UTILITY);
        primaryStage.setMinHeight(950);
        primaryStage.setMaxHeight(950);
        primaryStage.setMaxWidth(1050);
        primaryStage.setMinWidth(1050);
        primaryStage.show();
    }

    public static void main(String[] args) throws IOException {
        launch(args);
    }

    public void initialMemory(Memory memory, ObservableList<Registradores> list) throws IOException {
        Read reader = new Read();
        String line = reader.readLine();
        Integer i = 19;
        while (line!=null){
            memory.set_element(i,getInstruction(line));
            list.set(i,new Registradores(i.toString(),getInstruction(line)));
            i++;
            line = reader.readLine();
        }

    }

    public static void executionMode(Memory memory, Read reader, String choiceString, ObservableList<Registradores> list){
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
        list.set(16,new Registradores("MOP",MOP.getMop()));
        if (choice==0){
            continuousMode(memory, reader, list);
        }else{
            if(choice==1){
                semiContinuousMode(memory, reader, list);
            }else{
                debugMode(memory, reader, list);
            }
        }
    }

    public static void continuousMode(Memory memory, Read reader, ObservableList<Registradores> list){
        String line = reader.readLine();
        while (line!=null){
            ArrayList<Integer> attributes = convert(memory, line, list);
            execute(memory, attributes, list);
            line = reader.readLine();
        }
    }

    public static void semiContinuousMode(Memory memory, Read reader, ObservableList<Registradores> list){
        String line = reader.readLine();
        while (line!=null){
            ArrayList<Integer> attributes = convert(memory, line, list);
            execute(memory, attributes, list);
            line = reader.readLine();

        }
    }

    public static void debugMode(Memory memory, Read reader, ObservableList<Registradores> list){}

    public static ArrayList<Integer> convert (Memory memory, String line, ObservableList<Registradores> list){
        InstructionRecorder RI = (InstructionRecorder) memory.get(17);
        RI.setRi(getOpcode(line));
        list.set(17,new Registradores("RI", RI.getRi()));
        ArrayList<Integer> attributes = new ArrayList<Integer>();
        if (RI.getRi()!=9 && RI.getRi()!=13 && RI.getRi()!=11) {
            attributes.add(getAddressMode(line));
            attributes.add(getFirstOP(line));
        }else{
            if (RI.getRi() == 13){
                attributes.add(getAddressMode(line));
                attributes.add(getFirstOP(line));
                attributes.add(getSecondOP(line));
            }
        }

        return attributes;
    }

    public static void execute (Memory memory, ArrayList<Integer> attributes, ObservableList<Registradores> list){
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
                operation.write(attributes.get(1), attributes.get(0), memory, list);
                break;
            case 9:
                operation.ret((ProgramCounter) memory.get(14), (StackPointer) memory.get(13), memory, list);
                break;
            case 10:
                operation.divide((Accumulator) memory.get(15), attributes.get(1), attributes.get(0), memory, list);
                break;
            case 11:
                operation.stop();
                break;
            case 12:
                operation.read(attributes.get(0), attributes.get(1), memory, list);
                break;
            case 13:
                operation.copy(attributes.get(1), attributes.get(2), attributes.get(0), memory, list);
                break;
            case 14:
                operation.multi((Accumulator) memory.get(15), attributes.get(1), attributes.get(0), memory, list);
                break;
            case 15:
                operation.call((StackPointer) memory.get(13), (ProgramCounter) memory.get(14), attributes.get(1), memory, attributes.get(0), list);
                break;
        }
    }

    public static Integer getOpcode(String instruction) {
        return Integer.parseInt(instruction.substring(12, 16), 2);
    }

    public static Integer getAddressMode(String instruction) {
        return Integer.parseInt(instruction.substring(9, 12), 2);
    }

    public static Integer getFirstOP(String instruction) {
        return Integer.parseInt(instruction.substring(16, 32),  2);
    }

    public static Integer getSecondOP(String instruction) {
        return Integer.parseInt(instruction.substring(32, 48), 2);
    }

    public static Integer getInstruction(String instruction) {
        return Integer.parseInt(instruction.substring(0, 16));
    }

}
