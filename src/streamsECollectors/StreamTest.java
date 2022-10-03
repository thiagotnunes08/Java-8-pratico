package streamsECollectors;

import lambdas.Usuario;

import java.util.*;
import java.util.function.IntBinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamTest {

    public static void main(String[] args) {

        List<Usuario> usuarios = new ArrayList<>();

        Usuario user1 = new Usuario("thiago", 6);
        Usuario user2 = new Usuario("ana", 7);
        Usuario user3 = new Usuario("bruna", 8);
        Usuario user4 = new Usuario("jorge", 4);
        Usuario user5 = new Usuario("matheus", 101);
        Usuario user6 = new Usuario("lucas", 250);
        Usuario user7 = new Usuario("junior", 4);
        Usuario user8 = new Usuario("hayssa", 111);
        Usuario user9 = new Usuario("jose", 115);

        usuarios.addAll(List.of(user1, user2, user3, user4, user5, user6, user7, user8, user9));

        /**
         * Tornando moderador os 3 usuários com mais pontos! = lucas,hayssa e jose
         */


        usuarios.sort(Comparator.comparing(Usuario::getPontos).reversed());

        usuarios.subList(0, 3)
                .forEach(Usuario::tornaModerador);

        //  usuarios.forEach(System.out::println);


        /**
         *  tornando moderadores os usuários com mais de 100 pontos!
         *
         *  até o java7 faziamos dessa forma: for(Usuario u : listDeUsuarios){
         *                                     if(u.getPontos() > 100){
         *                                     usuario.tornaModerador();
         *
         *  o codigo acima está grande e imperativo. A qualquer mudança na filtragem ou na acao a ser executada
         *  teremos de encadear mais blocos de codigo.
         *
         *
         *  Outro ponto é que não há um metodo de filtro em nenhuma collection do java.
         * Contudo, tiveram a moticação de criacao do Stream!
         *
         */

        Stream<Usuario> streamUsuario = usuarios.stream();

        /**
         * a partir desse stream de usuário conseguimos utilizar o metodo "filter". Ele recebe um
         * parametro do tipo "Predicate"
         */

        Stream<Usuario> stream = usuarios.stream().filter(usuario -> usuario.getPontos() > 100);

        // stream.forEach(System.out::println);

        /**
         * É importante ressaltar que o stream evita efeitos colaterais na coleção que estamos trabalhando.
         *
         * exemplo:
         */

        usuarios.stream()
                .filter(usuario -> usuario.getPontos() > 100);

        // usuarios.forEach(System.out::println); //a saída imprime todos usuários. Logo, o filtro nao foi aplicado

        /**
         * LOGO: O STREAM NAO TEM EDEUTI COLATERAL SOBRE A COLECAO Q O ORIGINOU
         * por isso sempre que aplicamos uma transformacao em um Stream, como fizemos com o filter
         * ele nos retorna um novo Stream como resultado
         * exemplo:
         */

        Stream<Usuario> stream2 = usuarios.stream()
                .filter(usuario -> usuario.getPontos() > 100);

        //  stream2.forEach(System.out::println); //agora assim a saida é filtrada apenas por quem tem mais de 100 pontos

        /**
         * complementando: Stream então é uma coleção ? certamente não!
         * a mais clara diferenca é que um stream nunca guarda dados.
         * Ele não tem uma estrutura de dados interna parar armazenar cada um dos elementos: ele na verdade usa uma
         * colecao ou algum outro tipo de fonte para trabalhar com os objetos e executar uma série de operacoes
         * (um pipeline de operacoes) . Ele está mais proximo de um iterator. o Stream é uma sequencia de elementos
         * que pode ser trabalhada de diversas formas.
         *
         * Podemos encadear as invocacoes ao stream de maneira fluente. Em vez de fazer:
         */

        Stream<Usuario> stream1 = usuarios
                .stream()
                .filter(usuario -> usuario.getPontos() > 100);

//        stream1.forEach(System.out::println);

        /**
         * vamos utilizar o retorno do filter para encaixar diretamente no forEach
         */

//        usuarios.stream()
//                .filter(u->u.getPontos() > 100)
//                .forEach(System.out::println);

        /**
         * O stream é desenhado para que vc utilize-o apenas uma vez. Caso queira realizar novas operacoes
         * voce deve invocar stream() na colecao mais um vez obtendo um novo!
         */

        ///////////////////////////////////////////////////////////////////

        /**
         * voltando na situação original que era torna-los moderadores:
         */

        usuarios.stream()
                .filter(usuario -> usuario.getPontos() > 100)
                .forEach(Usuario::tornaModerador);

        /**
         * outro exemplo: filtrar os usuários que sao moderadores
         */

        usuarios.stream()
                .filter(Usuario::isModerador);

        ///////////////////////////////////////////////////////////////

        /**
         * e como coletamos/obtemos as informações que criamos ou filtramos: Collectors.toList!!
         */

        usuarios.stream().filter(usuario -> usuario.getPontos() > 100).toList();

        /**
         * há ainda o método toCollection que permite que vc escolha a implementação
         */

        Set<Usuario> maisque100 = usuarios.stream().collect(Collectors.toCollection(HashSet::new));

        /////////////////////////////////////////////////////////////////////

        /**
         * Liste apena sos pontos de todos os usuários com o map
         */

        List<Integer> pontos = new ArrayList<>();
        usuarios.forEach(u -> pontos.add(u.getPontos()));

        /**
         * repare que é preciso criar uma variável intermediária com a lista, e adicionar os pontos manualmente.
         * Ainda nao estamos tirando proveito da api do java 8.
         *
         * Utilizando o .map() ficaria assim:
         */

        List<Integer> pontos1 = usuarios.stream().map(Usuario::getPontos).toList();

        /**
         * intStream, para evitar autoboxing
         *
         * obetendo a média de pontos dos usuários!
         *
         */

        Double pontuacaoMedia = usuarios.stream()
                .mapToInt(Usuario::getPontos)
                .average()
                .getAsDouble();


        //////////////////////////////////////////////////////////////////////////////////////

        /**
         * Ordenando um stream!
         *
         * filtrando os usuários com mais de 100 pontos e ordena-los por ordem alfabetica
         *
         */

        Stream<Usuario> sorted = usuarios.stream()
                .filter(usuario -> usuario.getPontos() > 100)
                .sorted(Comparator.comparing(Usuario::getNome));

//       sorted.forEach(System.out::println);

        /**
         * obs: no stream o sortd nao altera a lista original. Para tal é necessário colocar para uma NOVA lista
         * utilizando o collect Collector.toList ou so o toList
         */

        /////////////////////////////////////////////////////////////////////////////////////////////

        /**
         * muitas operações no stream são lazy! (executam quando necessário)
         *
         * quando manipulamos um stream, normalmente encadeamos diversas operacoes computacionais. Esse conjuto de
         * operacoes realiazdo é chamado de pipeline. e como otimizalas ? evitando utilizar ao maximo as operacoes
         * somente quando há necessidade.
         *
         * um exemplo:
         */

        usuarios.stream().filter(usuario -> usuario.getPontos() > 10)
                .sorted(Comparator.comparing(Usuario::getNome));

        /**
         * os metodos filter e sorted devolvem um stream. No momento dessas invocações, esses metodos nem filtram,
         * nem ordenam, eles apenas devolvem novos streams em que essa informação é marcada. (metodos intermediarios)
         * os novos streams retornados sabem q devem ser filtrados e ordenados no momento em que uma operacao terminal
         * for invocada.
         * exemplo: collect (so aki o stream ira executar o pipeline de operacoes pedido.
         *
         */
        // má pratica pois o stream foi totalmente filtrado percorrendo todos usuarios da lista
        Usuario usuario1 = usuarios.stream().filter(usuario -> usuario.getPontos() > 10).toList().get(0);

        //boa pratica, pois nao foi completamente filtrado, ao encontrar um user com +10 pontos
        //forcou a execucao do pipeline pois o findAny é uma operacao terminal.
        //ou findFirst = acha o primeiro na ordem percorrida do stream
        Optional<Usuario> any = usuarios.stream().filter(usuario -> usuario.getPontos() > 200).findAny();


        /**
         * encergando a execucao do pipeline com .peek()
         * podemos pedir para o stream execute uma tarefa toda vez que processar um elemento.
         */

        usuarios.stream().sorted(Comparator.comparing(Usuario::getNome))
                .peek(System.out::println)
                .findFirst();
        ///////////////////////////////////////////////////////////////////////////////////////////////

        /**
         * operacoes de redução!!
         *Operacoes que utilizam os elementos da stream para retornar um valor final sao frequentemente chamadas
         * de operacos de reducao. um exemplo é o average
         */
        double pontuacaoMedia1 = usuarios.stream().mapToInt(Usuario::getPontos)
                .average()
                .getAsDouble();
        //há outros utéis como o average= min,count,max e sum por exemplo.
        //obs: min e max pedem um Comparator como argumento. sum e count trabalham com o optional.

        Optional<Usuario> max = usuarios.stream().max(Comparator.comparing(Usuario::getPontos));

        Usuario maxPontuacao = max.get();

        //para somar todos os pontos dos usuários:

        int total = usuarios.stream()
                .mapToInt(Usuario::getPontos)
                .peek(System.out::println)
                .sum();
        //essa soma é executada através de uma operacao de reducao bem explicita,
        //mas como ela funciona ?
        //vamos quebrar essa operação de soma para enxergar melhor

        int valorInicial = 0;

        IntBinaryOperator operator = (a, b) -> a + b;
        //essa é uma interface funcional que define o metodo applyAsInt, que recebe dois inteiros e devolve um inteiro.

        //com essas definicoes, podemos pedir para que o stream processe a reducao, passo a passo:

        int total1 = usuarios.stream()
                .mapToInt(Usuario::getPontos)
                .reduce(valorInicial,operator);

        //pronto! temos um codigo equivalente ao metodo sum()




    }

}
