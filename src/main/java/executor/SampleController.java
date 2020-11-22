package executor;

import ReadingFile.Read;
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


@Data
public class SampleController implements Initializable {
    int i = 0;

    int insert = 0;

    ObservableList<String> executionModeList = FXCollections.observableArrayList("Continuo", "Semi-Continuo","Depuracao");

    private String instrucoesLeitura;

    private ObservableList<Registradores> list = FXCollections.observableArrayList();

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
    private TableView<Registradores> tableView;

    @FXML
    private TableColumn<Registradores, String> colunaEndereco;

    @FXML
    private TableColumn<Registradores, String> colunaValor;

    @FXML
    private Button botaoStep;

    public SampleController () throws IOException {}

    @Override
    public void initialize (URL url, ResourceBundle rb){
        colunaEndereco.setCellValueFactory(new PropertyValueFactory<Registradores,String>("enderecos"));
        colunaValor.setCellValueFactory(new PropertyValueFactory<Registradores,String>("valor"));
        tableView.setItems(getRegistradores());
        executionMode.setItems(executionModeList);
    }

    public void onInsertTextAction() throws IOException, InterruptedException {
        fileWrite();
        String executionModeValue = executionMode.getValue();
        System.out.println(instrucoesLeitura);
        cpu.initialMemory(memory,list);
        cpu.executionMode(memory, reader, executionModeValue, list, console);
    }

    @FXML
    public void onBotaoStepAciotn() throws IOException {
        fileWrite();
        cpu.initialMemory(memory,list);
        OperationMode MOP = (OperationMode) memory.get(16);
        MOP.setMop(2);
        list.set(16,new Registradores("MOP",MOP.getMop().toString()));
        cpu.debugMode(memory,reader,list,console);
    }

    public ObservableList<Registradores> getRegistradores(){
        return list;
    }

    public void fileWrite (){
        instrucoesLeitura = instructions.getText().replaceAll("\n", System.getProperty("line.separator"));
        Writer.writeFile(instrucoesLeitura);
    }


}