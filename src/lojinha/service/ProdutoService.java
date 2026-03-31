package lojinha.service;

import lojinha.model.Produto;
import lojinha.repository.ProdutoRepository;

import java.util.List;
import java.util.Optional;

/**
 * Serviço responsável pelas operações relacionadas a produtos.
 * Camada de negócio entre o repositório e o restante do sistema.
 */
public class ProdutoService {

    private final ProdutoRepository repository;

    public ProdutoService(ProdutoRepository repository) {
        this.repository = repository;
    }

    /**
     * Retorna o catálogo completo de produtos disponíveis.
     */
    public List<Produto> listarProdutos() {
        return repository.listarTodos();
    }

    /**
     * Busca um produto pelo ID.
     * @throws IllegalArgumentException se o produto não for encontrado
     */
    public Produto buscarPorId(int id) {
        return repository.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Produto não encontrado: id=" + id));
    }

    /**
     * Exibe o catálogo formatado no console (simula "Exibe os produtos" do diagrama).
     */
    public void exibirCatalogo() {
        System.out.println("\n===== CATÁLOGO DE PRODUTOS =====");
        List<Produto> produtos = listarProdutos();
        produtos.forEach(p -> System.out.printf(
                "  [%d] %-20s R$%7.2f  (estoque: %d)%n",
                p.getId(), p.getNome(), p.getPreco(), p.getEstoque()));
        System.out.println("================================");
    }
}