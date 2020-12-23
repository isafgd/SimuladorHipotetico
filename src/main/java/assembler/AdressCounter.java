package assembler;

public class AdressCounter {
    private Integer adressCounter;


    public AdressCounter(){
        adressCounter = 0;
    }

    public void add (Integer a) {
        this.adressCounter = this.adressCounter + a;
    }

    public void set(Integer a){
        this.adressCounter = a;
    }

    public int get(){
        return this.adressCounter;
    }

}
