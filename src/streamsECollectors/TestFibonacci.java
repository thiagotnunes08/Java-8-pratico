package streamsECollectors;

import java.util.stream.IntStream;

public class TestFibonacci {


    /**
     * testando operacoes de curto-circuito apresentadas no livro!
     *
     *   pode ser util para um Supplier manter estado. Nesse caso criaremos a classe, pois nao podemos
     *   declarar atributos no lambda
     */


    public static void main(String[] args) {

        //vamos gerar a sequencia infinita de numeros de fibonacci de maneira lazy e imprir os 10 primerios
        IntStream.generate(new Fibonacci())
                .limit(10)
                .forEach(System.out::println);

        //pegando o primeiro elemento maior que 100
        boolean b = IntStream.generate(new Fibonacci())
                .allMatch(f -> f % 2 == 0);

        //matcher tmb sao curto-circuito = podemos tentar descobrir se todos os leementos sao pares
        int maiorQue100 = IntStream
                .generate(new Fibonacci())
                .filter(f -> f > 100)
                .findFirst()
                .getAsInt();

        /**
         * obs: trabalhar com streams infinitos pode ser perigoso, ambas ultimas 2 abordagens podem rodar indefinidamente.
         */

        //opcão para quando for necessário manter o estado de apenas uma variavel, podemos usar o iterate em vez do
        //generate :

        IntStream.iterate(0,x -> x + 1)
                .limit(10)
                .forEach(System.out::println);




    }
}
