package executor;

import connector.Connector;
import executor.recorders.OperationMode;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import loader.Loader;
import lombok.Data;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


/*Classe controladora da Interface*/
@Data
public class SampleController implements Initializable {

    ObservableList<String> executionModeList = FXCollections.observableArrayList("Continuo", "Semi-Continuo","Depuracao");

    private ObservableList<MemoryList> list = FXCollections.observableArrayList();

    private Memory memory = new Memory(list);

    private CPU cpu = new CPU();

    private Loader loader = new Loader();

    private Connector connector = new Connector();

    private MacroProcessing macro = new MacroProcessing();

    @FXML
    private TextArea console;

    @FXML
    private TextArea console1;

    @FXML
    private TextArea console2;

    @FXML
    private TextArea instructions;

    @FXML
    private TextArea instructions1;

    @FXML
    private ChoiceBox<String> executionMode;

    @FXML
    private Button insertFirstModule;

    @FXML
    private Button insertSecondModule;

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

    @FXML
    private Button botaoMacro;

    @FXML
    private Button botaoMontador;

    @FXML
    private Button botaoLigador;

    @FXML
    private Button botaoCarregador;

    @FXML
    private Button botaoOk;

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
    public void onInsertTextAction(){
        String instrucoesLeitura = instructions.getText().replaceAll("\n", System.getProperty("line.separator"));
        fileWrite("Modulo1.txt", instrucoesLeitura);
    }

    public void onInsertTextAction2(){
        String instrucoesLeitura = instructions1.getText().replaceAll("\n", System.getProperty("line.separator"));
        fileWrite("Modulo2.txt", instrucoesLeitura);
    }

    //Controla a acao que sera executado quando o botao step eh acionado
    @FXML
    public void onBotaoStepAction() throws IOException {
        OperationMode MOP = (OperationMode) memory.get(16);
        MOP.setMop(2);
        list.set(16,new MemoryList("MOP",MOP.getMop().toString()));
        cpu.debugMode(memory,list,console);
    }

    //Controla a acao que sera executado quando o botao reset eh acionado
    @FXML
    public void onBotaoMacroAction() throws IOException {
        macro.convertToObjectFont();
    }

    @FXML
    public void onBotaoMontadorAction() throws IOException {

    }

    @FXML
    public void onBotaoLigadorAction() throws IOException {
        connector.process(console1,console2);
    }

    @FXML
    public void onBotaoCarregadorAction() throws IOException {
        console1.clear();
        console2.clear();
        loader.initialMemory(memory,list,console);
    }

    @FXML
    public void onBotaoResetAction() throws IOException {
        memory = new Memory(list);
        console.clear();
        console1.clear();
        console2.clear();
    }

    @FXML
    public void onBotaoOkAction() throws IOException {
        String executionModeValue = executionMode.getValue();
        cpu.executionMode(memory, executionModeValue, list, console);
    }


    public ObservableList<MemoryList> getRegistradores(){
        return list;
    }

    //Chama a funcao que escreve no arquivo interno
    public void fileWrite (String fileName, String instrucoesLeitura){
        Writer.writeFile(instrucoesLeitura, fileName);
    }


}