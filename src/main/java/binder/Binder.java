package binder;


import config.GetProperties;
import executor.Reader;

import java.io.IOException;

public class Binder {

    private GetProperties properties;

    public void firstPassage () throws IOException {
        Reader reader = new Reader("OutPut1Montador.txt");
        Reader reader2 = new Reader("OutPut1Montador.txt");

        String line = reader.readLine();
    }

}
