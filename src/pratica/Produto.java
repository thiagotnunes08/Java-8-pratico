package pratica;

import java.math.BigDecimal;

public class Produto {

    private String nome;
    private BigDecimal price;

    public Produto(String nome, BigDecimal price) {
        this.nome = nome;
        this.price = price;
    }

    public String getNome() {
        return nome;
    }

    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Produto{" +
                "nome='" + nome + '\'' +
                ", price=" + price +
                '}';
    }
}
