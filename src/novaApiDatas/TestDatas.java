package novaApiDatas;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
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


        /**
         * formatando as novas datas.
         * formatar um localdatetime, por exemplo ficou mt simples. É so chamar o metodo format passando DateTimeFormatter
         * como parametro
         */

        LocalDateTime now = LocalDateTime.now();
                                                            //há vários outros formatos a serem explorados
        String resultOfFormat = now.format(DateTimeFormatter.ISO_LOCAL_DATE);

        /**
         * pode tmb criar um novo padrao, utilizando o ofPattern:
         */

        LocalDateTime agora1 = LocalDateTime.now();
        String format = agora1.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));

        //podemos ir além e transformar essa string em alguma representacao de data valida, e para isso usamos o
        // metodo "parse"


        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        String resultado = agora1.format(formatador);

        LocalDate agrEmData = LocalDate.parse(resultado,formatador);

        /**
         * obs: oberserve que fizemos o parse desse resultado em um LocalDate, e nao em um LocalDateTime, que é o
         * tipo da tada inicial pois perdemos as informacoes de tempo (horas,minutos,segundos e nano segundos)
         */

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////


        /**
         * duração e período.
         *
         * Sempre foi problematico trabalhar com diferenca de alguma medida de tempo em java. Exemplo: calcular
         * a diferenca de dias entre dois Calendars.
         */

        Calendar agr = Calendar.getInstance();

        Calendar outraData = Calendar.getInstance();
        outraData.set(1999,Calendar.APRIL,26);

        long diferenca = agr.getTimeInMillis() - outraData.getTimeInMillis();

        long milissegundoEmUmDia = 1000 * 60 * 60 * 24;

        long dia = diferenca / milissegundoEmUmDia;

        // agora podemos fazer essa mesma operacao de forma muito mais simples utilizando o enum ChronoUnit da nova api

        LocalDate agoran = LocalDate.now();

        LocalDate outraDatan = LocalDate.of(1999,Month.APRIL,26);

        long diass = ChronoUnit.DAYS.between(outraDatan,agoran);

        //podemos tmb saber a diferenca de anos e meses

        long meses = ChronoUnit.MONTHS.between(outraDatan,agoran);

        long anos = ChronoUnit.YEARS.between(outraDatan,agoran);

        System.out.println("dias:" + diass + " meses: " + meses + " anos:" + anos);
        //saida = dias:8572 meses:281 anos:23

        ////////////////////////////////////////////////////////////////////////////////

        // uma forma de conseguir o resultado que esperamos: os dias, meses e anos entre duas datas utilizando o modelo
        //"Period"

        LocalDate aagora = LocalDate.now();
        LocalDate outraDataa = LocalDate.of(1999,Month.APRIL,26);
        Period periodo = Period.between(outraDataa,aagora);

        System.out.println("dias: " + periodo.getDays() + " meses:"+ periodo.getMonths() + " anos:" + periodo.getYears());
        //SAIDA: dias: 18 meses:5 anos:23

        //há metodos auxiliarea com o isNegative para lidarmos com datas negativas(é comum gerar valores negativos dependento da ordem dos argumentos recebidos no between)
        if (periodo.isNegative()){
            //aki negamos seu valores e com isso a data fica positiva
            periodo = periodo.negated();
        }

        /////////////////////////////////////////////////////////////////////////////

        //existem diversas outras formas de se criar um period, além do metodo between uma delas é of
        Period period = Period.of(1999, 4, 26);
        //também podemos criar periodos de dias,meses ou anos utilizando o ofDays,ofMonths e ofYears

        ///////////////////////////////////////////////////////////////////////////////

        /**
         * mas como criar um periodo de horas,minutos ou segundo ?
         * com period não é posivel, pois é considerado dias,meses e anos. Neste caso utilizamos a classe Duration que
         * considera horas, minutos e segundos
         */

        LocalDateTime noww = LocalDateTime.now();
        LocalDateTime daquiUmaHora = LocalDateTime.now().plusHours(1);

        Duration duracao = Duration.between(daquiUmaHora,noww);

        System.out.println(duracao);

        if (duracao.isNegative()){
            duracao = duracao.negated();
        }

        //obs: repare que como estamos trabalhando com tempo, utilizamos o localDateTime


    }
}
