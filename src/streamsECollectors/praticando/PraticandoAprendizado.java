package streamsECollectors.praticando;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.stream.IntStream;

import static java.nio.file.Files.*;
import static java.nio.file.Files.lines;

/**
 * praticando o que aprendemos utilziando a classe java.nio.files.Files(manipulacao de arquivos e diretorios)
 * é umas das classes que agora possuem metodos para trabalhar com stream.
 */

public class PraticandoAprendizado {

    public static void main(String[] args) throws IOException {

        /**
         * e se quisermos listar todos os arquvivos de um diretorio ?
         */

        list(Paths.get("C:\\Users\\thiago\\Downloads"))
                .forEach(System.out::println);

        /**
         * fazendo um filtro:
         */

        list(Paths.get("C:\\Users\\thiago\\Downloads"))
                .filter(path -> path.toString().endsWith(".pdf"))
                .forEach(System.out::println);


        /**
         * para tentar pegar o conteúdo do arquivo:
         * obs: o codigo compila apenas com try/catch pois o map lança IO exception
         */

        list(Paths.get("C:\\Users\\thiago\\Downloads"))
                .filter(path -> path.toString().endsWith(".pdf"))
                .map(p -> {
                    try {
                        return lines(p);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .forEach(System.out::println);
        /**
         * a saida:
         * java.util.stream.ReferencePipeline$Head@76fb509a
         * java.util.stream.ReferencePipeline$Head@300ffa5d
         * java.util.stream.ReferencePipeline$Head@1f17ae12
         *
         * o problema é: com esse "map" teremos um "Stream<Stream<String>>",pois a invocacao de "lines(p)" devolve
         * um "Stream<String>" para cada Patch  do nosso stream de path.
         */

        ///////////////////////////////////////////////////////////////////////////////////

        /**
         * flatMap: podemos "achatar" um stream de streams com o flatMap.
         *
         */

        IntStream chars =
                list(Paths.get("C:\\Users\\thiago\\Downloads"))
                        .filter(path -> path.toString().endsWith(".txt"))
                        .flatMap(path1 -> {
                            try {
                                return lines(path1);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        })
                        .flatMapToInt(String::chars);

    }
}
