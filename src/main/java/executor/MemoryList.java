package executor;


import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

public class MemoryList {

    private SimpleStringProperty enderecos;
    private SimpleStringProperty valor;

    public String getEnderecos() {
        return enderecos.get();
    }

    public void setEnderecos(SimpleStringProperty enderecos) {
        this.enderecos =  enderecos;
    }

    public String getValor() {
        return valor.get();
    }

    public void setValor(SimpleStringProperty valor) {
        this.valor = valor;
    }

    public MemoryList(String enderecos, String valor) {
        this.enderecos = new SimpleStringProperty(enderecos);
        this.valor = new SimpleStringProperty(valor);
    }
}
