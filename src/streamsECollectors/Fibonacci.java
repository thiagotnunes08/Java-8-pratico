package streamsECollectors;

import java.util.function.IntSupplier;


public class Fibonacci implements IntSupplier {

    private int anterior = 0;

    private int proximo = 1;


    public  int getAsInt(){

        proximo = proximo + anterior;
        anterior = proximo - anterior;

        return anterior;
    }
}
