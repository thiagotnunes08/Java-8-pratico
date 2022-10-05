package optional;

import lambdas.Usuario;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class OptionalTest {

    public static void main(String[] args) {
        List<Usuario> usuarios = new ArrayList<>();

        Usuario user1 = new Usuario("thiago",6);
        Usuario user2 = new Usuario("ana",7);
        Usuario user3 = new Usuario("bruna",8);
        Usuario user4 = new Usuario("jorge",4);
        Usuario user5 = new Usuario("matheus",101);
        Usuario user6 = new Usuario("lucas",250);
        Usuario user7 = new Usuario("junior",4);
        Usuario user8 = new Usuario("hayssa",111);
        Usuario user9 = new Usuario("jose",115);

        usuarios.addAll(List.of(user1,user2,user3,user4,user5,user6,user7,user8,user9));

        Double pontuacaoMedia = usuarios.stream()
                .mapToInt(Usuario::getPontos)
                .average()
                .getAsDouble();

        /**
         * utilizamos o average().getAsDouble() para receber o double da média.
         * q devolve um OptionalDouble. e pq nao um double?
         *
         * caso a lista de usuários estivesse vazia iriamos ter como resultado um positivo infinito!
         */

        double soma = 0;

        for (Usuario u : usuarios){
            soma += u.getPontos();
        }

        double pontucaoMedia;

        if (usuarios.isEmpty()){
            soma = 0;

        } else {

            pontucaoMedia = soma / usuarios.size();

        }

        /**
         * o mesmo codigo utilizando java 8 e optional(caixinha)
         */

        double media = usuarios
                .stream()
                .mapToInt(Usuario::getPontos)
                .average()
                .orElse(0.0);

        /**
         * podemos ainda jogar um Exception, além de vários outros metodos para fugir de null pointer,ex:
         */

        double media1 = usuarios
                .stream()
                .mapToInt(Usuario::getPontos)
                .average()
                .orElseThrow(IllegalStateException::new);


        /**
         * pode verificar o contrario. Se realmente existe um valor dentro do optional e no caso de existir
         * , passamos um Consumer como argumento
         */
        Usuario janela = new Usuario(); //exemplo

        usuarios.stream()
                .mapToInt(Usuario::getPontos)
                .average()
                .ifPresent(value -> janela.atualiza(value));

        /**
         * outro exemplo: queremos o usuário com maior quantidade de pontos.
         * podemos utilizar o metodo max
         */

        Optional<Usuario> max = usuarios.stream().max(Comparator.comparingInt(Usuario::getPontos));
        //se a lista for vazia, não havera usuario para ser retornado. por isso o retorno é um Optional.


        /**
         * pegando o nome no usuário com maior números de pontos
         */

        Optional<String> userMaisPontos = usuarios.stream()
                .max(Comparator.comparingInt(Usuario::getPontos))
                .map(Usuario::getNome);


    }
}
