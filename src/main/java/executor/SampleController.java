/*
package executor;

import executor.recorders.OperationMode;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.Data;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


*/
/*Classe controladora da Interface*//*

@Data
public class SampleController implements Initializable {

    ObservableList<String> executionModeList = FXCollections.observableArrayList("Continuo", "Semi-Continuo","Depuracao");

    private String instrucoesLeitura;

    private ObservableList<MemoryList> list = FXCollections.observableArrayList();

    private Memory memory = new Memory(list);

    private CPU cpu = new CPU();

    private Read reader = new Read();

    @FXML
    private TextArea console;

    @FXML
    private TextArea instructions;

    @FXML
    private ChoiceBox<String> executionMode;

    @FXML
    private Button insertText;

    @FXML
    private TableView<MemoryList> tableView;

    @FXML
    private TableColumn<MemoryList, String> colunaEndereco;

    @FXML
    private TableColumn<MemoryList, String> colunaValor;

    @FXML
    private Button botaoStep;

    @FXML
    private Button botaoReset;

    public SampleController () throws IOException {}

    //Inicializa os campos da interface
    @Override
    public void initialize (URL url, ResourceBundle rb){
        colunaEndereco.setCellValueFactory(new PropertyValueFactory<MemoryList,String>("enderecos"));
        colunaValor.setCellValueFactory(new PropertyValueFactory<MemoryList,String>("valor"));
        tableView.setItems(getRegistradores());
        executionMode.setItems(executionModeList);
    }

    //Controla a acao que sera executado quando o botao insert eh acionado
    public void onInsertTextAction() throws IOException, InterruptedException {
        fileWrite();
        String executionModeValue = executionMode.getValue();
        System.out.println(instrucoesLeitura);
        cpu.initialMemory(memory,list, console);
        cpu.executionMode(memory, reader, executionModeValue, list, console);
    }

    //Controla a acao que sera executado quando o botao step eh acionado
    @FXML
    public void onBotaoStepAciotn() throws IOException {
        fileWrite();
        cpu.initialMemory(memory,list, console);
        OperationMode MOP = (OperationMode) memory.get(16);
        MOP.setMop(2);
        list.set(16,new MemoryList("MOP",MOP.getMop().toString()));
        cpu.debugMode(memory,reader,list,console);
    }

    //Controla a acao que sera executado quando o botao reset eh acionado
    @FXML
    public void onBotaoResetAciotn() throws IOException {
        memory = new Memory(list);
        console.clear();
    }


    public ObservableList<MemoryList> getRegistradores(){
        return list;
    }

    //Chama a funcao que escreve no arquivo interno
    public void fileWrite (){
        instrucoesLeitura = instructions.getText().replaceAll("\n", System.getProperty("line.separator"));
        Writer.writeFile(instrucoesLeitura);
    }


}*/
