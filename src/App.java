import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;

public class App {
    public static void main(String[] args) throws Exception {

        // fazer uma conexão HTTP e buscar os top 250 filmes
        /*
         * A API do IMDB, usada durante esta aula, saiu do ar hoje. A comunidade criou
         * endereços alternativos que você pode usar no lugar da URL, são eles:
         * 
         * (https://mocki.io/v1/9a7c1ca9-29b4-4eb3-8306-1adb9d159060) criada
         * pelo @rezendecas
         * (https://alura-imdb-api.herokuapp.com/movies) criada pelo Jhon Santana
         * (https://api.mocki.io/v2/549a5d8b) criada pelo instrutor Alexandre Aquiles
         * (https://alura-filmes.herokuapp.com/conteudos) criada pela instrutora
         * Jacqueline Oliveira
         * (https://raw.githubusercontent.com/alexfelipe/imersao-java/json/top250.json)
         * criada pelo instrutor Alex Felipe
         */
        String url = "https://api.mocki.io/v2/549a5d8b";
        URI endereco = URI.create(url);
        var client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(endereco).GET().build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        String body = response.body();

        // extrair só os dados que interessam (titulo, poster, classificação)
        var parser = new JsonParser();
        List<Map<String, String>> listaDeFIlmes = parser.parse(body);

        /*
         * System.out.println(listaDeFIlmes.size());
         * System.out.println(listaDeFIlmes.get(0));
         */

        // exibir e manipular os dados
        for (Map<String, String> filme : listaDeFIlmes) {

            String URLiMAGEM = filme.get("image");
            String titulo = filme.get("title");

            InputStream inputStream = new URL(URLiMAGEM).openStream();
            String nomeArquivo = titulo + ".png";

            var geradora = new GeradoraDeFigurinhas();
            geradora.cria(inputStream, nomeArquivo);

            System.out.println(titulo);
            System.out.println();
        
        }

    }
}
