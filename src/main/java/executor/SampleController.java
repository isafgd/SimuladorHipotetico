package executor;

import ReadingFile.Read;
import executor.Memory;
import executor.Registradores;
import executor.recorders.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.Data;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Data
public class SampleController implements Initializable {
    int i = 0;

    int insert = 0;

    ObservableList<String> executionModeList = FXCollections.observableArrayList("Continuo", "Semi-Continuo","Depuracao");

    private String instrucoesLeitura;

    private ObservableList<Registradores> list = FXCollections.observableArrayList();

    private Memory memory = new Memory(list);

    private CPU cpu = new CPU();

    @FXML
    private TextArea instructions;

    @FXML
    private ChoiceBox<String> executionMode;

    @FXML
    private Button insertText;

    @FXML
    private TableView<Registradores> tableView;

    @FXML
    private TableColumn<Registradores, String> colunaEndereco;

    @FXML
    private TableColumn<Registradores, String> colunaValor;

    @FXML
    private Button botaoStep;

    @Override
    public void initialize (URL url, ResourceBundle rb){
        colunaEndereco.setCellValueFactory(new PropertyValueFactory<Registradores,String>("enderecos"));
        colunaValor.setCellValueFactory(new PropertyValueFactory<Registradores,String>("valor"));
        tableView.setItems(getRegistradores());
        executionMode.setItems(executionModeList);
        /*
        instrucoesLeitura = instructions.getText();*/
    }

    public void onInsertTextAction() throws IOException, InterruptedException {
        Read reader = new Read();
        instrucoesLeitura = instructions.getText().replaceAll("\n", System.getProperty("line.separator"));
        String teste = executionMode.getValue();
        Writer.writeFile(instrucoesLeitura);
        //Covnverte bonito
        //imprimir na caixa de texto
        instructions.clear();
        System.out.println(instrucoesLeitura);
        System.out.println(teste);
        cpu.initialMemory(memory,list);
        cpu.executionMode(memory, reader, i, list);
    }


    /*@FXML
    public void changeScreenButtonPushed (ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/fxml/initializer.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage) ((javafx.scene.Node)event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }*/

    @FXML
    public void onBotaoStepAciotn(){
        i++;
        System.out.println(i);
    }

    //Esse metodo tem que retornar como um ObservableList

    public ObservableList<Registradores> getRegistradores(){
        /*StackPointer SP = (StackPointer) memory.get(13);
        ProgramCounter PC = (ProgramCounter) memory.get(14);
        Accumulator ACC = (Accumulator) memory.get(15);
        OperationMode MOP = (OperationMode) memory.get(16);
        AddressRecorder RE = (AddressRecorder) memory.get(18);
        InstructionRecorder RI = (InstructionRecorder) memory.get(17);*/
/*
        list.add(new Registradores("0",0));
        list.add(new Registradores("1",0));
        list.add(new Registradores("2",10));
        list.add(new Registradores("3",0));
        list.add(new Registradores("4",0));
        list.add(new Registradores("5",0));
        list.add(new Registradores("6",0));
        list.add(new Registradores("7",0));
        list.add(new Registradores("8",0));
        list.add(new Registradores("9",0));
        list.add(new Registradores("10",0));
        list.add(new Registradores("11",0));
        list.add(new Registradores("12",0));
        list.add(new Registradores("SP", SP.getPointer()));
        list.add(new Registradores("PC", PC.getPc()));
        list.add(new Registradores("ACC",ACC.getAcc()));
        list.add(new Registradores("MOP",MOP.getMop()));
        list.add(new Registradores("RI",RI.getRi()));
        list.add(new Registradores("RE",RE.getRe()));
        list.add(new Registradores("OPD1",(Integer) memory.get(19)));*/

        return list;
    }

}