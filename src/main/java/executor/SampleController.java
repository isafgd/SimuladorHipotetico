package executor;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class SampleController implements Initializable {
    int i = 0;

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
    }

    @FXML
    public void onBotaoStepAciotn(){
        i++;
        System.out.println(i);
    }

    public int getI (){
        return i;
    }

    //Esse metodo tem que retornar como um ObservableList

    public ObservableList<Registradores> getRegistradores(){
        ObservableList<Registradores> lista = FXCollections.observableArrayList();
        lista.add(new Registradores("1",123));
        lista.add(new Registradores("2",321));
        lista.add(new Registradores("3",456));
        lista.add(new Registradores("4",654));
        lista.add(new Registradores("5",789));
        lista.add(new Registradores("6",987));
        lista.add(new Registradores("7",890));
        lista.add(new Registradores("8",980));
        lista.add(new Registradores("9",122));

        return lista;
    }

}