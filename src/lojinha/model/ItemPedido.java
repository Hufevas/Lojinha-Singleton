package lojinha.model;

/**
 * Representa um item dentro de um pedido, associando produto e quantidade.
 */
public class ItemPedido {

    private final Produto produto;
    private final int quantidade;
    private final double subtotal;

    public ItemPedido(Produto produto, int quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;
        this.subtotal = produto.getPreco() * quantidade;
    }

    public Produto getProduto() { return produto; }
    public int getQuantidade() { return quantidade; }
    public double getSubtotal() { return subtotal; }

    @Override
    public String toString() {
        return String.format("  -> %s x%d = R$%.2f",
                produto.getNome(), quantidade, subtotal);
    }
}