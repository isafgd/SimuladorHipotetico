package executor;

import executor.recorders.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.util.ArrayList;


public class CPU extends Application {

    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Simulador");
        primaryStage.setScene(new Scene(root, 1000, 900));
        primaryStage.show();
    }

    public static void main(String[] args) throws FileNotFoundException {
        Memory memory = new Memory();
        Reader reader = new Reader();
        executionMode(memory, reader);
    }

    public static void executionMode(Memory memory, Reader reader){
        SampleController sampleController = new SampleController();
        int choice = sampleController.getI();
        OperationMode MOP = (OperationMode) memory.get(16);
        MOP.setMop(choice);

        if (choice==0){
            continuousMode(memory, reader);
        }else{
            if(choice==1){
                semiContinuousMode(memory, reader);
            }else{
                debugMode(memory, reader);
            }
        }
    }

    public static void continuousMode(Memory memory, Reader reader){
        String line = reader.readLine();
        while (line!=null){
            ArrayList<Integer> attributes = convert(memory, line);
            execute(memory, attributes);
            line = reader.readLine();
        }
    }

    public static void semiContinuousMode(Memory memory, Reader reader){}

    public static void debugMode(Memory memory, Reader reader){}

    public static ArrayList<Integer> convert (Memory memory, String line){
        ArrayList<Integer> attributes = new ArrayList<Integer>();
        attributes.add(getAddressMode(line));
        attributes.add(getFirstOP(line));
        attributes.add(getSecondOP(line));

        InstructionRecorder RI = (InstructionRecorder) memory.get(17);
        RI.setRi(getOpcode(line));
//        AddressRecorder RE = (AddressRecorder) memory.get(18);
//        memory.set_element(RE.getRe(),getFirstOP(line));
//        RE.increment();

        return attributes;
    }

    public static void execute (Memory memory, ArrayList<Integer> attributes){
        InstructionRecorder RI = (InstructionRecorder) memory.get(17);
        Operations operation = new Operations();
        switch (RI.getRi()) {
            case 0:
                operation.br((ProgramCounter) memory.get(14), attributes.get(1));
                break;
            case 1:
                operation.brPos((ProgramCounter) memory.get(14), (Accumulator) memory.get(15), attributes.get(1));
                break;
            case 2:
                operation.add((Accumulator) memory.get(15), attributes.get(1));                 // Raquel, basicamente é chamar a instrução aqui e printar o que ela deu set/get
                break;
            case 3:
                operation.load((Accumulator) memory.get(15), attributes.get(1));
                break;
            case 4:
                operation.brZero((ProgramCounter) memory.get(14), (Accumulator) memory.get(15), attributes.get(1));
                break;
            case 5:
                operation.brNeg((ProgramCounter) memory.get(14), (Accumulator) memory.get(15), attributes.get(1));
                break;
            case 6:
                operation.sub((Accumulator) memory.get(15), attributes.get(1));
                break;
            case 7:
                operation.store((Accumulator) memory.get(15), attributes.get(1), memory);
                break;
            case 8:
                operation.write(attributes.get(1));
                break;
            case 9:
                operation.ret((ProgramCounter) memory.get(14), (StackPointer) memory.get(13), memory);
                break;
            case 10:
                operation.divide((Accumulator) memory.get(15), attributes.get(1));
                break;
            case 11:
                operation.stop((Accumulator) memory.get(15));
                break;
            case 12:
                operation.read(attributes.get(1));
                break;
            case 13:
                attributes.set(1,operation.copy(attributes.get(1), attributes.get(2)));
                break;
            case 14:
                operation.mult((Accumulator) memory.get(15), attributes.get(1));
                break;
            case 15:
                operation.call((StackPointer) memory.get(13), (ProgramCounter) memory.get(14), attributes.get(1), memory);
                break;
        }
    }

    public static Integer getOpcode(String instruction) {
        return Integer.parseInt(instruction.substring(0, 4), 2);     //Retorna um inteiro da substring do espaço 0 até 3
    }

    public static Integer getAddressMode(String instruction) {
        return Integer.parseInt(instruction.substring(4, 7), 2);     //Retorna um inteiro da substring do espaço 0 até 3
    }

    public static Integer getFirstOP(String instruction) {
        return Integer.parseInt(instruction.substring(7, 11), 2);     //Retorna um inteiro da substring do espaço 0 até 3
    }

    public static Integer getSecondOP(String instruction) {
        return Integer.parseInt(instruction.substring(12, 16), 2);     //Retorna um inteiro da substring do espaço 0 até 3
    }

}
