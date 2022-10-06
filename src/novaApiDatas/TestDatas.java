package novaApiDatas;

import java.time.*;
import java.time.format.TextStyle;
import java.util.Calendar;
import java.util.Locale;

public class TestDatas {


    public static void main(String[] args) {
        /**
         * trabalhando com datas de forma fluente!
         *
         * aplicar uma transformacao em um "Calendar" é um processo muito verboso, como por exemplo, para criar
         * uma data com um mês a partir da data atual:
         */

        //legado
        Calendar mesQVem = Calendar.getInstance();
        mesQVem.add(Calendar.MONTH,1);

        /**
         * com a nova api podemos fazer a mesma coisa de forma mais fluente!
         */
        LocalDate mesQueVem = LocalDate.now().plusMonths(1);
        //podemos utilizar plus ou minus qualquer coisa conforme necessidade.

        //subitraindo um ano
        LocalDate anoPassado = LocalDate.now().minusYears(1);
        /**
         * obs: a classe "LocalDate" representa uma data sem horario nem timezone.Se as informacoes do horario forem
         * importantes, usamos "LocalDateTime", ex:
         */
        LocalDateTime agoraa = LocalDateTime.now();


        //há ainda o LocalTime para representar apenas as horas
        LocalTime agora = LocalTime.now();

        //é possivel também criar um localdatetime com horario especifico com o "atTime"
        LocalDateTime hojeAoMeioDia = LocalDate.now().atTime(12,0);

        /**
         * obs: assim como fizemos com atTime, sempre podemos utlizar o metodo "at" para combinar os diferentes modelos.
         * Observe como fica simples unir uma classe LocalDate com um LocalTime
         */

        LocalTime hrAgora = LocalTime.now();

        LocalDate hoje = LocalDate.now();

        LocalDateTime dataEHora = hoje.atTime(hrAgora);

       //também podemos a partir do localDateTime, chamar o metodo atZone para construir um ZonedDateTime, que é o
        //modelo usado para representar uma data com hora e timezone(fuso horario)

        ZonedDateTime dataComHoraETimeZone = dataEHora.atZone(ZoneId.of("America/Sao_Paulo"));

        /**
         * obs:em alguns momentos é importante trabalhar com timeZone, mas o ideal é utlizar quando necessário.
         * A propria documentacao pede cuidado com o uso dessa informacao, pois muitas vezes nao será necessário, e
         * usá-la pode causar problemas, como para guardar o aniversário de um usuario.
         *
         * Para converter de ZonedDateTime para LocalDateTime fazemos assim:
         */
        LocalDateTime semTimeZone = dataComHoraETimeZone.toLocalDateTime();
        //da mesma forma pode ser feito toLocalDate entre outros metodos de conversao

        LocalDate semHoras = semTimeZone.toLocalDate();

        /**
         * Além disso, as classes dessa nova API contam com o método estático of, que é um factory method para construcao
         * de suas novas instacias:
         */

        LocalDate dataDoMeuNiver = LocalDate.of(1999,4,26);

        LocalDateTime umaData = LocalDateTime.of(1999,4,26,9,30);
        //há uma sobrecarga de metodo, é possivel colocar tmb segundos e nano segundos!


        /**
         * de forma silumar a um metódo "setter" os novos modelos imutáveis possuem os métodos "whit" para facilitar
         * a insercao de suas informacoes.Para modificar o ano de um LocalDate, por exemplo, poderiamos usar o metodo
         * "whithYear" exemplo:
         */
        LocalDate anoNiver = LocalDate.now().withYear(1999);

        /**
         * Existem também outros comportamentos essencias, como ssaber se alguma medida de tempo acontece antes,depois ou
         * ao mesmo tempo que outra Para esses casos, utilizamos os metodos "is" :
         */

        LocalDate hj = LocalDate.now();

        LocalDate amanha = LocalDate.now().plusDays(1);

        System.out.println(hoje.isBefore(amanha)); //true
        System.out.println(hoje.isAfter(amanha));  //false
        System.out.println(hoje.isEqual(amanha));  //false

        //há casos que queremos comparar datas iguais, porém em timeZones diferentes

        ZonedDateTime tokyo =  ZonedDateTime.of(2022,10,6,17,19,0,0,ZoneId.of("Asia/Tokyo"));

        ZonedDateTime sp =  ZonedDateTime.of(2022,10,6,17,19,0,0,ZoneId.of("America/Sao_Paulo"));

        //o resultado ainda é false. Precisamos acertar o timeZone:

        tokyo.plusHours(12);


        System.out.println(tokyo.isEqual(sp));

        /**
         * a nova api possui diversos outros modelos que facilitam bastante nosso trabalho com data e tempo, como as
         * classes MonthDay, Year e YearMonth.Para obter o dia do mes atual, por exemplo poderiamos utilziar o metodo
         * getDayOfMonth de umas instacia da classe MonthDay :
         */
        //pega o dia exemplo: dia 6
        System.out.println("hoje eh dia:" + MonthDay.now().getDayOfMonth());

        //pega o dia da semana,exemplo: quinta-feira
        System.out.println(LocalDate.now().getDayOfWeek().getDisplayName(TextStyle.FULL_STANDALONE, Locale.forLanguageTag("pt-br")));

        //pega quantos dias se passaram do ano atual!
        System.out.println(LocalDate.now().getDayOfYear());


        //vc tmb pode pegar o YearMonth de uma determinada data
        //ym = 1999-04
        YearMonth ym = YearMonth.from(dataDoMeuNiver);

        //aki imprime APRIL 1999
        System.out.println(ym.getMonth()+ " " + ym.getYear());


        /**
         * ENUMS no lugar de constantes.
         * essa nova API de datas favorece o uso de enums no lugar das famosas contantes do calendar.
         * para representar um mes utilizamos Month.DECEMBER.(cada valor do enum representa um inteiro, exemplo: janeiro
         * 1, dezembor 12.
         */

        System.out.println(LocalDate.of(2002,4,10));
        //AS saidas sao as mesmas, porém o cod fica mais legivel assim v
        System.out.println(LocalDate.of(2002,Month.APRIL,10));

        //outra vantagem: note como é facil consultar o primeiro dia do trimestre de determidado mes, ou entao
      //  incrementar/decrementar meses

        System.out.println(Month.MAY.firstMonthOfQuarter().firstMonthOfQuarter());

        System.out.println(Month.APRIL.plus(2));

        System.out.println(Month.APRIL.minus(2));

        //OBS: outro enum foi o DayOfWeek

        Locale brasa = new Locale("pt-br");

        System.out.println(DayOfWeek.FRIDAY.getDisplayName(TextStyle.SHORT_STANDALONE,Locale.forLanguageTag("pt-br")));




    }
}
