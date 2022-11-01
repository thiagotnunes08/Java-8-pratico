package diamondMelhorado;

import lambdas.Usuario;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestClss {



    public static void main(String[] args) {

        Usuario usuario = new Usuario();

        /**
         * o operador diamente pe aquele que evita digitarmos desnecessariamente situações obvias:
         */
        List<Usuario> usuarios = new ArrayList<Usuario>();
        /**
         * a partir do java 7, ele sabe que o tipo do diamente é de usuario:
         */
        List<Usuario> usuarios1 = new ArrayList<>();

        /**
         * porém o recurso era muito limitado. Basicamente apenas podia ser usado junto com a declaração da variável referencia.
         * Já no java8, é possivel escrever e compilar o seguinte codigo:
         */
       usuario.adiciona(new ArrayList<>());
    }




}
