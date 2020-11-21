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


import java.io.IOException;
import java.io.PrintStream;
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
        String executionModeValue = executionMode.getValue();
        Writer.writeFile(instrucoesLeitura);
        //Covnverte bonito
        //imprimir na caixa de texto
        instructions.clear();
        System.out.println(instrucoesLeitura);
        cpu.initialMemory(memory,list);
        cpu.executionMode(memory, reader, executionModeValue, list);
    }

    @FXML
    public void onBotaoStepAciotn(){
        i++;
        System.out.println(i);
        console.setText("QlqrCoisa");
    }

    //Esse metodo tem que retornar como um ObservableList

    public ObservableList<Registradores> getRegistradores(){
        return list;
    }


}