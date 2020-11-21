package executor;


import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Registradores {

    private SimpleStringProperty enderecos;
    private SimpleIntegerProperty valor;

    public String getEnderecos() {
        return enderecos.get();
    }

    public void setEnderecos(SimpleStringProperty enderecos) {
        this.enderecos =  enderecos;
    }

    public Integer getValor() {
        return valor.get();
    }

    public void setValor(SimpleIntegerProperty valor) {
        this.valor = valor;
    }

    public Registradores(String enderecos, Integer valor) {
        this.enderecos = new SimpleStringProperty(enderecos);
        this.valor = new SimpleIntegerProperty(valor);
    }
}
