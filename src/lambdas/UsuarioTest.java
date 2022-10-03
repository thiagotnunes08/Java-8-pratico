package lambdas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class UsuarioTest {

    public static void main(String[] args) {
        Usuario user1 = new Usuario("thiago", 10);
        Usuario user2 = new Usuario("maria", 5);
        Usuario user3 = new Usuario("lucas", 30);
        Usuario user4 = new Usuario("julius", 30);

        List<Usuario> usuarios = new ArrayList<>();

        usuarios.addAll(List.of(user1,user2,user3,user4));



        /**
         * como era utilzizado o loop para iterar uma lista, ou qualquer objeto q implemente Iterable.
         * A partir do java 8
         * é utilizado o forEach "para cada usuário oq ele deve fazer ?"
         *
         */

        for (Usuario u : usuarios) {
            System.out.println(u.getNome());

        }

        /**
         * Lambda= maneira mais simples de implementar uma interface que so tem um unico metodo
         * no caso a Consumer é uma boa candidata
         *
         * metodo forEach em java 8:
         */

        usuarios.forEach(usuario -> System.out.println(usuario.getPontos()));
        /**
         *  // para cada usuário nesse array com usuários, vou pegar cada um e imprir seus devidos pontos!!
         */

        ////////////////////////////////////////////////////////////////////////////////////

        usuarios.forEach(usuario -> System.out.println(usuario.isModerador() + " antes"));

        usuarios.forEach(usuario -> usuario.tornaModerador());
        /**
         *  //para cada usuario. torne-os moderador!
         */

        usuarios.forEach(usuario -> System.out.println(usuario.isModerador() + " dps"));

        ////////////////////////////////////////////////////////////////////////////////////


       Consumer<Usuario> mostraMsg = u -> System.out.println("antes de imprir os nomes");
       Consumer<Usuario> imprimeNome = u -> System.out.println(u.getNome());
        /**
         * // uma iterface pode sim ter mais metodos desde que sejam defaults.
         * O metodo andThen pode ser usado para compor instancias da interface Consumer para que possam ser
         * executadas sequencialmente, exemplo:
         */
        usuarios.forEach(mostraMsg.andThen(imprimeNome));

        ////////////////////////////////////////////////////////////////////////////////////

       /**
        * além do forEach chegou o "removeIf" que recebe um predicate, uma interface funcional
        *  que permite testar objetos de um determinado tipo. Dado um Predicate o removeIf vai remover
        *  todos os elementos que devolverem true para esse predicate
        */
        usuarios.removeIf(usuario -> usuario.getPontos() > 50);

        usuarios.forEach(usuario -> System.out.println(usuario.getNome() + " aki"));



    }

}