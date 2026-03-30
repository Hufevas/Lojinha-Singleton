package lojinha.payment;

/**
 * Representa o resultado retornado pelo sistema externo de pagamento.
 */
public class ResultadoPagamento {

    private final boolean aprovado;
    private final String mensagem;
    private final String codigoTransacao;

    public ResultadoPagamento(boolean aprovado, String mensagem, String codigoTransacao) {
        this.aprovado = aprovado;
        this.mensagem = mensagem;
        this.codigoTransacao = codigoTransacao;
    }

    public boolean isAprovado() { return aprovado; }
    public String getMensagem() { return mensagem; }
    public String getCodigoTransacao() { return codigoTransacao; }

    @Override
    public String toString() {
        return String.format("ResultadoPagamento[aprovado=%b, mensagem='%s', transacao='%s']",
                aprovado, mensagem, codigoTransacao);
    }
}