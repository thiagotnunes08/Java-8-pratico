package novaApiDatas;

import pratica.Cliente;
import pratica.Pagamento;
import pratica.Produto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

public class RelatoriosComDatas {

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
         * Relatórios com datas!
         * é muito simples separamos os pagamentos por data. usando um groupingBy(Pagamento::getDate). há um perigo:
         * o LocalDateTime vai agrupar os pagamentos ate pelos milissegundos. não é oq queremos. Com isso podemos
         * agrupar por LocalDate, usando um groupingBy(p -> p.getDate().toLocalDate, ou em um intervalo ainda
         * maior, como por ano e mes. para isso usamos o YearMonth:
         */

        Map<YearMonth, List<Pagamento>> pagamentosPorMes =
                pagamentos.stream()
                        .collect(Collectors.groupingBy(pagamento -> YearMonth.from(pagamento.getData())));
          //  pagamentosPorMes.entrySet().forEach(System.out::println);

        /**
         * e se quisermos saber, também por mes, quanto foi faturado na loja ?
         * basta agrupar com o mesmo criterio e usar a reducao que conhecemos: somando todos os precos de todos os
         * produtos de todos pagamentos
         */

        Map<YearMonth, BigDecimal> paymentsValuePerMonth = pagamentos.stream()
                .collect(Collectors.groupingBy(p -> YearMonth.from(p.getData()),
                        Collectors.reducing(BigDecimal.ZERO,
                                p -> p.getProdutos().stream()
                                        .map(Produto::getPrice)
                                        .reduce(BigDecimal.ZERO,
                                                BigDecimal::add),
                                BigDecimal::add)));


        paymentsValuePerMonth.entrySet().stream()
                .forEach(System.out::println);
        //saida: 2022-09=3100
        //       2022-10=20500

        /**
         * imagine tmb que oferecemos um sistema de assinatura. Pagando um valor mnensal, o cliente tem acesso
         * a todoo conteudo de nosso e-comerce. além do valor e do cliente, uma assinatura precisa ter a data de inicio
         * e talvez uma data de termina(Optional)
         */

        BigDecimal mensalidade = new BigDecimal("99.90");

        Assinatura assinatura1 = new Assinatura(mensalidade,ontem.minusMonths(5),cliente1);

        Assinatura assinatura2 = new Assinatura(mensalidade,ontem.minusMonths(8),hj.minusMonths(1),cliente2);

        Assinatura assinatura3 = new Assinatura(mensalidade,ontem.minusMonths(5),hj.minusMonths(2),cliente3);

        List<Assinatura> assinaturas = List.of(assinatura1,assinatura2,assinatura3);

        /**
         * como calcular quantos meses foram pagos atraves daquela assinatura ? se a assinatura ainda estiver ativa,
         * calculamos o intervalo de tempo entre o comeco e a data de hoje:
         */

        long meses = ChronoUnit.MONTHS
                .between(assinatura1.getComeco(),LocalDateTime.now());

        /**
         * e se a assinatura terminou ? em vez de enchermos nosso codigo com ifs, tiramos proveito do Optional:
         */

        long meses1 = ChronoUnit.MONTHS
                .between(assinatura2.getComeco(),assinatura2.getFim().orElse(LocalDateTime.now()));


        /**
         * para calcular o valor gerado por aquela assinatura, basta multiplicar esse numero de meses pelo custoi mensal:
         */

        BigDecimal total = assinatura1.getMensalidade()
                .multiply(new BigDecimal(ChronoUnit.MONTHS.between(assinatura2.getComeco(),assinatura2.getFim().orElse(LocalDateTime.now()))));

      //ou podemos facilitar o trabalho e adicionar um novo metodo na propria classe: dada uma lista de assinaturas
        // fica facil somar quanto a empresa lucrou com todos os assinantes.

        BigDecimal totalPago = assinaturas.stream()
                .map(Assinatura::totalGastoComAAssinatura)
                .reduce(BigDecimal.ZERO,BigDecimal::add);

        System.out.println(totalPago);




    }
}
