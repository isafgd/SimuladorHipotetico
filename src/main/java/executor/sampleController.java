package executor;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class sampleController {
    int i = 0;

    @FXML
    private Button botaoStep;

    @FXML
    public void onBotaoStepAciotn(){
        i++;
        System.out.println(i);
    }

}