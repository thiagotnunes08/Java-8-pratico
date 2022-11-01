package methodreference;

import lambdas.Usuario;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class TestMethod {

    public static void main(String[] args) {

        /**
         * como faríamos para tornar moderadoes todos os elementos de uma lista de usuário ?
         *
         * " usuarios.forEach(u -> u.tornaModerador());
         *
         *   Com method Reference é mt mais simples:'
         *
         *    listUsuarios.forEach(Usuario::tornaModerador);
         */

        List<Usuario> listUsuarios = new ArrayList<>();

        Usuario user1 = new Usuario("thiago",1);
        Usuario user2 = new Usuario("ana",2);
        Usuario user3 = new Usuario("bruna",3);
        Usuario user4 = new Usuario("jorge",4);
        Usuario user5 = new Usuario("matheus",5);
        Usuario user6 = new Usuario("lucas",6);
        Usuario user7 = new Usuario("junior",1);
        Usuario user8 = new Usuario("hayssa",3);

        listUsuarios.addAll(List.of(user1,user2,user3,user4,user5,user6,user7,user8));

      //  listUsuarios.forEach(usuario -> System.out.println(usuario.getNome()+ " eh moderador ? = "+usuario.isModerador()));


        listUsuarios.forEach(Usuario::tornaModerador);

    //    listUsuarios.forEach(usuario -> System.out.println(usuario.getNome()+ " eh moderador ? = "+usuario.isModerador()));


        /**
         * e se quisermos um critério de comparacao mais elaborado ?
         * exemplo: ordenar pelos pontos e no caso de empate ordenar pelo nome
         */

        Comparator<Usuario> usuarioComparator =
                Comparator.comparingInt(Usuario::getPontos)
                        .thenComparing(Usuario::getNome);

        listUsuarios.sort(usuarioComparator);
        ///////////////////////////////////////////////////////////////////////

        /**
         * para comparar na ordem de pontos decrescente utilizamos reversed();
         *
         */

        listUsuarios.sort(Comparator.comparingInt(Usuario::getPontos).reversed());




        ///////////////////////////////////////////////////////////////////////

        /**
         * há outros 2 metódos que vc pode aproveitar para compor comparaçoes
         *
         * "nullLast" e "nullFirst"
         *
         * Com isso, todos os usuários nulos da nossa lista estarão posicionados no fim(ou inicio) e o restante
         * ordenado pelo nome!
         */

        Comparator<Usuario> comparing = Comparator.comparing(Usuario::getNome);
        listUsuarios.sort(Comparator.nullsLast(comparing));

        listUsuarios.sort(comparing);


    }

    /**
     * referenciando construtores:
     *
     * "Usuario thiago :: new;" <- este codigo não compila, pois devemos guardar o resultado dessa referencia
     * em uma interface funcional.
     *
     */
     Supplier<Usuario> criandoUser = Usuario::new;
     Usuario novo = criandoUser.get(); //usamos um supplier sempre para criar um novo objeto a partir de um contrutor default(vazio)

    /**
     * quando há argumentos da classe no construtor, que receba o que será criado (Usuario)
     * como qual argumento vai ser passado para o contrutor (String) = utilizamos Function!!
     */

    Function<String,Usuario> criandoUser1 = Usuario::new;

    //criamos esse usuário utilizando o metodo abstrado aplly

    Usuario thiago = criandoUser1.apply("thiago");

    //quando há mais de argumento no construtor utilizamos a BiFunction

    BiFunction<String,Integer,Usuario> criandoUser2 = Usuario::new;

    Usuario thiago2 = criandoUser2.apply("thiago",3);

    /**
     * é possivel referenciar o construtor de um Array
     * exemplo: int[]::new
     */

}
