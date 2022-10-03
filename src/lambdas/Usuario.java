package lambdas;


public class Usuario {

    private String nome;
    private Integer pontos;
    private boolean moderador;

    public Usuario(String nome, Integer pontos) {
        this.nome = nome;
        this.pontos = pontos;
        this.moderador = false;
    }

    public Usuario() {

    }

    public Usuario(String s) {
        this.nome = s;
    }



    public String getNome() {
        return nome;
    }

    public Integer getPontos() {
        return pontos;
    }

    public boolean isModerador() {
        return moderador;
    }

    public void tornaModerador(){
        this.moderador = true;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "nome='" + nome + '\'' +
                ", pontos=" + pontos +
                ", moderador=" + moderador +
                '}';
    }

    public void atualiza(double value){

    }
}

