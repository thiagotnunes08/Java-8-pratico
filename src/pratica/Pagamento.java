package pratica;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class Pagamento {

    private List<Produto> produtos;
    private LocalDateTime data;
    private Cliente cliente;

    public Pagamento(List<Produto> produtos, LocalDateTime data, Cliente cliente) {
        this.produtos = produtos;
        this.data = data;
        this.cliente = cliente;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public LocalDateTime getData() {
        return data;
    }

    public Cliente getCliente() {
        return cliente;
    }

    @Override
    public String toString() {
        return "Pagamento{" +
                "produtos=" + produtos +
                ", data=" + data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) +
                ", cliente=" + cliente +
                '}';
    }

    public BigDecimal getValorTotal(){
        return produtos.stream()
                .map(Produto::getPrice)
                .reduce(BigDecimal.ZERO,BigDecimal::add);

    }
}
