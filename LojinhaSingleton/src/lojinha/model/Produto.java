package lojinha.model;

/**
 * Representa um produto disponível na loja online.
 */
public class Produto {

    private final int id;
    private final String nome;
    private final double preco;
    private int estoque;

    public Produto(int id, String nome, double preco, int estoque) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.estoque = estoque;
    }

    public int getId() { return id; }
    public String getNome() { return nome; }
    public double getPreco() { return preco; }
    public int getEstoque() { return estoque; }

    public boolean temEstoque() {
        return estoque > 0;
    }

    public void reduzirEstoque(int quantidade) {
        if (quantidade > estoque) {
            throw new IllegalStateException("Estoque insuficiente para o produto: " + nome);
        }
        this.estoque -= quantidade;
    }

    @Override
    public String toString() {
        return String.format("Produto[id=%d, nome='%s', preco=R$%.2f, estoque=%d]",
                id, nome, preco, estoque);
    }
}
