package pratica;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Arrays.*;

/**
 * classe criada para colocar em prática conceitos aprendidos no livro.
 */
public class Tests {

    public static void main(String[] args) {

        Cliente cliente1 = new Cliente("thiago");
        Cliente cliente2 = new Cliente("lucas");
        Cliente cliente3 = new Cliente("andressa");
        Cliente cliente4 = new Cliente("hayssa");

        Produto produto = new Produto("TV",new BigDecimal(1000));
        Produto produto1 = new Produto("Geladeira",new BigDecimal(1500));
        Produto produto2 = new Produto("Video Game",new BigDecimal(4000));
        Produto produto3 = new Produto("Notebook",new BigDecimal(3000));
        Produto produto4 = new Produto("Celular",new BigDecimal(2000));
        Produto produto5 = new Produto("Ventilador",new BigDecimal(100));

        LocalDateTime hj = LocalDateTime.now();
        LocalDateTime ontem = LocalDateTime.now().minusDays(1);
        LocalDateTime mesPassado = LocalDateTime.now().minusMonths(1);

        Pagamento pagamento1 = new Pagamento(asList(produto,produto1),hj,cliente1);
        Pagamento pagamento2 = new Pagamento(asList(produto2,produto3),ontem,cliente1);
        Pagamento pagamento3 = new Pagamento(asList(produto4,produto5,produto),mesPassado,cliente2);
        Pagamento pagamento4 = new Pagamento(asList(produto4,produto1,produto),ontem,cliente3);
        Pagamento pagamento5 = new Pagamento(asList(produto,produto1,produto2),hj,cliente4);

        List<Pagamento> pagamentos = asList(pagamento1,pagamento2,pagamento3,pagamento4,pagamento5);

        /**
         * ordenar os pagamentos por data e imprimi-los
         */

        pagamentos.stream()
                .sorted(Comparator.comparing(Pagamento::getData))
                .forEach(System.out::println);

        /**
         * reduzindo bigDecimal em soma
         * obs: iremos calcular o valor total do pagamento1 usando a api de stream e lambdas. Há um probelma, se preco
         * fosse um int, poderiamos usar o mapToDouble e invocar o sum do DoubleStream resultante. Nao eh o caso
         * teremos um Stream<BigDecimal> e ele nao possui um sum
         */

        pagamento1.getProdutos().stream()
                .map(Produto::getPrice)
                .reduce(BigDecimal::add)
                .ifPresent(System.out::println);

        //outras abordagem:
        BigDecimal total = pagamento1.getProdutos().stream()
                .map(Produto::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        //abstrai a logica dentro da classe
       pagamento5.getValorTotal();

        /**
         * somando todos os valores da lista de pagamentos
         */

        BigDecimal totalFlat =
                pagamentos.stream()
                        .flatMap(p-> p.getProdutos().stream()
                                .map(Produto::getPrice))
                        .reduce(BigDecimal.ZERO,BigDecimal::add);

        /**
         * produtos mais vendidos
         */


        Map<Produto, Long> topProducts = pagamentos.stream()
                .flatMap(p -> p.getProdutos().stream())
                .collect(Collectors.groupingBy(Function.identity(),
                        Collectors.counting()));

            //System.out.println(topProducts);
            //esse sysout acima pode ser ruim para ler o resultado. Podemos pegar o entrySet desse mapa e imprimir
            // linha a linha

        topProducts.entrySet().forEach(System.out::println);

        //obs: certamente poderiamos encadear o entrySet logo apos nosso collect, porém perderiamos acesso ao map.
        //há tb uma versao do forEach que recebe uma BiConsumer<K,V> possibilitando o seguinte exemplo:
        topProducts.forEach((k,v)-> System.out.println("key: "+ k + " valor:" + v));

        // a saída:
//        key: Produto{nome='Ventilador', price=100} valor:1
//        key: Produto{nome='Notebook', price=3000} valor:1
//        key: Produto{nome='Geladeira', price=1500} valor:3
//        key: Produto{nome='Video Game', price=4000} valor:2
//        key: Produto{nome='Celular', price=2000} valor:2
//        key: Produto{nome='TV', price=1000} valor:4

        //TV é o mais comprado dos produtos. Mas como pegar apenas essa entrada do mapa ?
        topProducts.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .ifPresent(System.out::println);
        //o max devolve um optional, por isso o ifPresent

        /**
         * valores gerados por produtos
         */

        Map<Produto,BigDecimal> valorTotalPorProduto = pagamentos.stream()
                .flatMap(p -> p.getProdutos().stream())
                .collect(Collectors.groupingBy(Function.identity(),
                        Collectors.reducing(BigDecimal.ZERO,Produto::getPrice,BigDecimal::add)));

        valorTotalPorProduto.entrySet().stream()
                .sorted(Map.Entry.comparingByValue()).forEach(System.out::println);

        //podemos fazer de outra forma esse agrupamento:

        Map<Produto,BigDecimal> totalValorPorProduto2 = pagamentos.stream()
                .flatMap(p-> p.getProdutos().stream())
                .collect(Collectors.toMap(Function.identity(),
                        Produto::getPrice,
                        BigDecimal::add));

        //nesse caso, estamos dizendo ao coletor para invocar o metodo add usando o valor antigo como referencia
        //e passando o valor novo como argumento, armazenando seu retorno.

        /**
         * quais sao os produtos de cada cliente?
         * em um primeiro momento, podemos ter, para cada Cliente, sua Lista de pagamentos,bastando agrupar
         * os pagamnetos com groupingBy(Pagamentos::getClientes):
         */

        Map<Cliente,List<Pagamento>> clientesParaPagamentos =
                pagamentos.stream()
                        .collect(Collectors.groupingBy(Pagamento::getCliente));
        //mas nao estamos interessados nos pagamentos de um cliente. E sim nas listas de produtos!
        //fazemos:

        Map<Cliente,List<List<Produto>>> clientesParaListProdutos =
                pagamentos.stream().collect(Collectors.groupingBy(Pagamento::getCliente,Collectors.mapping(Pagamento::getProdutos,Collectors.toList())));

        clientesParaListProdutos.entrySet().stream()
                .sorted(Comparator.comparing(e-> e.getKey().getNome()))
                .forEach(System.out::println);

        //a saída : Cliente{nome='andressa'}=[[Produto{nome='Celular', price=2000}, Produto{nome='Geladeira', price=1500}, Produto{nome='TV', price=1000}]] (lista aninhadas)

        //queremos esse mesmo resultado mas com as listas achatadas em uma só.

        Map<Cliente,List<Produto>> clientesParaProdutosPasso2 =
                clientesParaListProdutos.entrySet().stream()
                        .collect(Collectors
                                .toMap(Map.Entry::getKey,e-> e.getValue()
                                .stream().flatMap(List::stream)
                                .collect(Collectors.toList())));

        clientesParaProdutosPasso2.entrySet()
                .stream()
                .sorted(Comparator.comparing(e-> e.getKey().getNome())).forEach(System.out::println);

        //é possivel fazer tudo em uma unica chamada, porem estouraria as linhas de legibiliade!

        Map<Cliente,List<Produto>> clientesParaProdutos1Passo =
                pagamentos.stream()
                        .collect(Collectors.groupingBy(Pagamento::getCliente,
                                Collectors.mapping(
                                        Pagamento::getProdutos,Collectors.toList()
                                ))).entrySet()
                        .stream()
                        .collect(Collectors.toMap(Map.Entry::getKey,
                                e-> e.getValue().stream()
                                        .flatMap(List::stream)
                                        .collect(Collectors.toList())));
        //dificil seguir esta sequencia. é mais facil quebrar em varios passos.


        //outra solucao:

        Map<Cliente,List<Produto>> clientesToProdutos =
                pagamentos.stream()
                        .collect(Collectors.groupingBy(Pagamento::getCliente,
                                Collectors.reducing(Collections.emptyList(),
                                        Pagamento::getProdutos,
                                        (l1,l2)-> {List<Produto> list =
                                new ArrayList<>();
                                list.addAll(l1);
                                list.addAll(l2);
                                return list;})));

        //tivemos que escrever algo muito parecido com o toList.Criamos um coletor que pega todos os produtos do pagamento
        //e vai acumulando o resultado em uma nova Array list. o resultado é exatamente o mesmo que o flatMap

        /**
         * e qual é o nosso cliente mais especial ?
         */

        //começaremos com bigDecimal.ZERO e, para cada pagamento, faremos Bigdecima:add da soma dos precos de seus
        //produtos. por esse motivo, uma reducao ainda aparece dentro do reducing


        Map<Cliente, BigDecimal> totalValuePerCustomer = pagamentos.stream()
                .collect(Collectors.groupingBy(Pagamento::getCliente,
                        Collectors.reducing(BigDecimal.ZERO,
                                p -> p.getProdutos().stream().map(Produto::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add),
                                BigDecimal::add)));

        //o codigo está no minimo muito acumulado. Cremos ja termos passado do limite da legibilidade, Vamos quebrar
        //essa reducao criando uma varival temporaria resposanvel por mapear um pagamento para a soma de todos
        //os precos de seus produtos

        Function<Pagamento,BigDecimal> pagamentoTotal =
                pagamento -> pagamento.getProdutos()
                        .stream()
                        .map(Produto::getPrice)
                        .reduce(BigDecimal.ZERO,BigDecimal::add);

        //com isso podemos utilizar essa function no reducing:

        Map<Cliente,BigDecimal> totalPorClient =
                pagamentos.stream()
                        .collect(Collectors.groupingBy(Pagamento::getCliente,
                                Collectors.reducing(BigDecimal.ZERO,pagamentoTotal,BigDecimal::add)));

        //conteudo do mapa:
        //.sorted(Comparator.comparing(Map.Entry::getValue)) - forma antiga
        totalPorClient.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .forEach(System.out::println);












    }
}
