package novaApiDatas;

import pratica.Cliente;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

public class Assinatura {

    private BigDecimal mensalidade;

    private LocalDateTime comeco;

    private Optional<LocalDateTime> fim;

    private Cliente cliente;

    public Assinatura(BigDecimal mensalidade, LocalDateTime comeco, Cliente cliente) {
        this.mensalidade = mensalidade;
        this.comeco = comeco;
        this.fim = Optional.empty();
        this.cliente = cliente;
    }

    public Assinatura(BigDecimal mensalidade, LocalDateTime comeco, LocalDateTime fim, Cliente cliente) {
        this.mensalidade = mensalidade;
        this.comeco = comeco;
        this.fim = Optional.of(fim);
        this.cliente = cliente;
    }

    public BigDecimal getMensalidade() {
        return mensalidade;
    }

    public LocalDateTime getComeco() {
        return comeco;
    }

    public Optional<LocalDateTime> getFim() {
        return fim;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public BigDecimal totalGastoComAAssinatura(){

        return mensalidade.multiply(new BigDecimal(ChronoUnit.MONTHS.between(getComeco(),getFim().orElse(LocalDateTime.now()))));
    }
}
