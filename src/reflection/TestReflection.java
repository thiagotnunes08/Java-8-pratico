package reflection;

import lambdas.Usuario;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.Arrays;

public class TestReflection {

    public static void main(String[] args) throws NoSuchMethodException {

        /**
         * melhorias em reflections: É possivel recuperar o nome dos parametros dos metodos e construtores.
         * basta invocar o getParameters?
         */

        Constructor<Usuario> constructor = Usuario.class.getConstructor(String.class,Integer.class);

        Parameter[] parameters = constructor.getParameters();

//        Arrays.asList(parameters)
//                .forEach(parame -> System.out.println(parame.getName()));
        //saida:
            //arg0
             //arg1

        /**
         * a informacao dos nomes nao esta presente no construtor. Para confirmar isso podemos imprimir tmb o resultado
         * do metodo isNamePresent em cada parametro:
         */

        Arrays.asList(parameters)
                .forEach(parame -> System.out.println(parame.isNamePresent() + ":" + parame.getName()));

        //saida: false:arg0
        //          false:arg1

        /**
         * repare que saiu false. Isso aconteceu pois precisamos passar o argumento parameters para o compilador!
         * apenas dessa forma ele vai manter os nomes dos parametros no byte code para recuperarmos via reflection
         */


    }
}
