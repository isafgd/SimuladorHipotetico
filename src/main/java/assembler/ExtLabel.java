package assembler;

import java.util.ArrayList;
import java.util.List;

public class ExtLabel {

    public String nome;
    public List<Integer> posicoes;

    public ExtLabel(String nome){
        this.nome = nome;
        posicoes = new ArrayList<>();
    }
}
