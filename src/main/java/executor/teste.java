package executor;

import binder.Binder;

import java.io.IOException;

public class teste {

    public static void main (String[] args) throws IOException {
        Binder binder = new Binder();
        binder.process();
    }
}
