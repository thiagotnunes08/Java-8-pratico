package ordenacao;

import lambdas.Usuario;

import java.util.*;

public class OrdenacaoTest {

    public static void main(String[] args) {

        Usuario user1 = new Usuario("THIAGO",79);
        Usuario user2 = new Usuario("ANA",20);
        Usuario user3 = new Usuario("BRUNA",49);
        Usuario user4 = new Usuario("LUCAS",10);
        Usuario user5 = new Usuario("ZICO",99);

        List<Usuario> usuarios = new ArrayList<>(List.of(user1, user2, user3, user4, user5));
        /**
         * 4 formar de ordenacao por ordem alfabética
         */

        //1
          Comparator<Usuario> comparator = new Comparator<Usuario>() {
            @Override
            public int compare(Usuario u1, Usuario u2) {
                return u1.getNome().compareTo(u2.getNome());
            }
        };
         Collections.sort(usuarios,comparator);
        /////////////////////////////////////////////////////////////////////////////////
        /**
         * a partir daki com java 8
         */
        //2
        Comparator<Usuario> comparator1 = (u1,u2) -> u1.getNome().compareTo(u2.getNome());
        Collections.sort(usuarios,comparator1);

        ///////////////////////////////////////////////////////////////////////////////////

        //3
        Collections.sort(usuarios,(u1,u2) -> u1.getNome().compareTo(u2.getNome()));

        ///////////////////////////////////////////////////////////////////////////////////

        //4
        usuarios.sort((u1,u2) -> u1.getNome().compareTo(u2.getNome()));

        /**
         * de forma mais susintas chegamso até o novo metodo statico de Comparator "comparing"
         * e ainda mais com method references = usuarios.sort(Comparator.comparing(Usuario::getNome));
         */
        //5

        usuarios.sort(Comparator.comparing(u-> u.getNome()));


        usuarios.sort(Comparator.comparingInt(u-> u.getPontos()));


       // metodos "naturalOrder" e "reverseOrder" não compila. Pesquisar o pq
      // usuarios.sort(Comparator.naturalOrder());
    }
}
