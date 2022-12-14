package mapeandoParticionandoAgrupando;


import lambdas.Usuario;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class ClassTest {

    public static void main(String[] args) {

        /**
         * coletores utilizando mapas:
         * mapeando todos os usuários utilizando seu nome como chave fica facil:
         */


        Usuario user1 = new Usuario("thiago", 6, true);
        Usuario user2 = new Usuario("ana", 8, false);
        Usuario user3 = new Usuario("bruna", 8, true);
        Usuario user7 = new Usuario("junior", 4, false);
        Usuario user8 = new Usuario("hayssa", 111, true);
        Usuario user9 = new Usuario("jose", 115, false);

        List<Usuario> usuarios = Arrays.asList(user1, user2, user3, user7, user8, user9);


        Map<String, Usuario> name =
                usuarios.stream().collect(Collectors.toMap(Usuario::getNome, Function.identity()));
        //identity é o mesmo que f -> f
        //para que o lambda retorne o proprio argumento

        /**
         * obs: se o usuário fosse entidade da JPA, poderiamos usar toMap(Usuario::getId(),Function.identity)
         * gerando um Map<Long,Usuario> na qual a chave é o id da entidade
         */

        ////////////////////////////////////////////////////////////////////////////////////////////////


        /**
         * queremos um mapa em que a chave seja a pontuação do usuário e o valor seja uma lista de usuários.
         * vamos fazer da maneira tradicional e dps com java 8
         */

        //tradicional/legado
        Map<Integer, List<Usuario>> pontuacao = new HashMap<>();

        for (Usuario u : usuarios) {

            if (!pontuacao.containsKey(u.getPontos())) {
                pontuacao.put(u.getPontos(), new ArrayList<>());
            }
            pontuacao.get(u.getPontos()).add(u);
        }

         //System.out.println(pontuacao);

        //com java 8
        Map<Integer,List<Usuario>> pontuacao1 = new HashMap<>();

        for (Usuario u : usuarios){
            pontuacao1.computeIfAbsent(
                    u.getPontos(),user -> new ArrayList<>())
                    .add(u);
        }
        /**
         * obs: o metodo "computeIfAbsent" chama a funcao do lambda, caso n encontre um valor para chave "u.getPontos"
         * ele irá assosiar o resultado(a nova arryaList) a essa mesma chave. fazendo o mesmo papel do if anterior
         */

        /**
         * porém há um coletor do stream que faz exetamente esse trabalho!
         * GROUPINGBY !!
         */

        Map<Integer,List<Usuario>> pontuacao2 =
                usuarios.stream()
                        .collect(Collectors.groupingBy(Usuario::getPontos));

        //pontuacao2.entrySet().forEach(System.out::println);

     //   System.out.println(pontuacao2);

        /**
         * podemos ir além e particionar os usuários moderadoes e nao moderadores!
         * utilizando PARTITIONBY!
         */


        Map<Boolean,List<Usuario>> moderadores = usuarios
                .stream()
                .collect(Collectors.partitioningBy(Usuario::isModerador));

     //   moderadores.entrySet().forEach(System.out::println);


        /**
         * ao ives de guardar o objeto usuario e todas suas informacoes, podemos pegar apenas o nome!
         * utilizando MAPPING!
         */

        Map<Boolean,List<String>> nomeModeradores = usuarios
                .stream()
                .collect(Collectors.partitioningBy(
                        Usuario::isModerador,Collectors.mapping(Usuario::getNome,Collectors.toList()
                        )));

        //nomeModeradores.entrySet().forEach(System.out::println);

        /**
         * vamo a mais um desafio: particionar por quem é moderador,mas ter como valor nao os usuários, mas sim a soma
         * de seus pontos.
         */

        Map<Boolean,Integer> pontuacaoPorModeradores = usuarios
                .stream()
                .collect(Collectors
                        .partitioningBy(Usuario::isModerador,
                                Collectors
                                        .summingInt(Usuario::getPontos)));

     // pontuacaoPorModeradores.entrySet().forEach(System.out::println);

        /**
         * é possivel também concatenar todos os nomes dos usuários com um coletor!
         */

        String nomes = usuarios
                .stream()
                .map(Usuario::getNome)
                .collect(Collectors.joining(","));

        System.out.println(nomes);

        /**
         * obs: com streams e coletores, conseguimos os mesmos resultados de antigamente, porém em um estilo funcional e,
         * consequentemente, mais enxuto e expressivo. Além disso, mesmo que vc não tenha percebido, acabamos trabalhando
         * com MENOS EFEITOS COLATERAIS e FAVORECEMOS a IMUTABILIDADE das coleções originais.
         * E qual é a vantagem de evitar efeitos colaterais e mutabilidade ?
         * facilitar a paralelizacao
         */

        /////////////////////////////////////////////////////////////////////////////////////////////////////////

        /**
         * Executando o pipeline em paralelo:
         *
         * Normalmente tudo acontece na mesma thread, se tivermos uma lista com milhoes de usuários, o processo
         * poderá levar alguns segundos. Mas e se precisarmos paralelizar esse processo ?
         * uma opcao seria tirar proveito da API de Fork/Join.
         * As Collections oferecem uma implementacao de Stream diferente, o strem em paralelo( .parallelStream() )
         * ele decide quantas thread deve utilizar, como deve quebrar o processamento dos dados e qual será a forma de
         * unir o resultado final em um só.
         */

        List<Usuario> filtradosEOrdenados = usuarios
                .parallelStream()
                .filter(user -> user.getPontos() > 100)
                .sorted(Comparator.comparing(Usuario::getNome)).toList();

        /**
         * este filtro acima é um ambiente controlado.
         * vamos gerar um numero bem maior para operar sobre ele
         */

        long coemco = System.currentTimeMillis();

        //no cenário COM o PARALLEL é executado em 700 MS
        //no cenário SEM o PARALLEL é executado em 3130 MS
        long sum1 =
                LongStream.range(0,2_129_999_999)
                        //.parallel()
                        .filter(n-> n % 2 == 0)
                        .sum();

        long fim = System.currentTimeMillis();


       // System.out.println(fim - coemco);

        ///////////////////////////////////////////////////////////////////////////////////////////////////////

        /**
         * operacoes nao deterministicas e ordered streams.
         *
         * operacoes nao deterministicas podem devolver diferentes resultados quando utilizadas em streams paralelos.
         * Os principais exemplos sao o forEach e o findAny. Ao invoca-los vc nao tem garantia da ordem de execucao,
         * isso melhora a performace em paralelo. (caso necessite garantir a ordem utilize forEachOrdered e o findFirst.
         */


        /**
         * para saber mais: a base do stream paralelo é o Spliterator. ele é como um iterator, so que muitas vezes pode
         * ser facilmente quebrado em spliterator menores, para que cada thread disponivel consuma um pedaco do
         * seu stream. A interface iterable agoraa também define um metodo default spliterator(). Tudo que vimos
         * de paralelizacao sao abstracoes que utilizam Slpiterators por debaixo dos panos, junto com a API de Fork/Join.
         */



    }
}
