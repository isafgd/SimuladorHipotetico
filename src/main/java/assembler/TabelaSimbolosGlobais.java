package assembler;


public class TabelaSimbolosGlobais {
    private String Simbolo, relocabilidade;
    private int endereco;
    public String getSimbolo() {
        return Simbolo;
    }

    public void setSimbolo(String Simbolo) {
        this.Simbolo = Simbolo;
    }

    public int getEndereco() {
        return endereco;
    }

    public void setEndereco(int endereco) {
        this.endereco = endereco;
    }

    public String getRelocabilidade() {
        return relocabilidade;
    }

    public void setRelocabilidade(String relocabilidade) {
        this.relocabilidade = relocabilidade;
    }


}