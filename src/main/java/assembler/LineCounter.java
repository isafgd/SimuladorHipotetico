package assembler;

public class LineCounter {
    private Integer lineCounter;


    public LineCounter(){
        lineCounter = 0;
    }

    public void add () {
        this.lineCounter = this.lineCounter + 1;
    }

    public void set(Integer a){
        this.lineCounter = a;
    }

    public int get(){
        return this.lineCounter;
    }

}
