package assembler;

public class TabelaDefinicoes {
    private String simbolo,relocabilidade;
    private int endereco;

    public String getSimbolo() {
        return simbolo;
    }

    public int getEndereco() {
        return endereco;
    }

    public String getRelocabilidade() {
        return relocabilidade;
    }

    public void setSimbolo(String simbolo) {
        this.simbolo = simbolo;
    }

    public void setEndereco(int endereco) {
        this.endereco = endereco;
    }

    public void setRelocabilidade(String relocabilidade) {
        this.relocabilidade = relocabilidade;
    }


}