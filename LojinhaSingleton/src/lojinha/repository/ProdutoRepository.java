package lojinha.repository;

import lojinha.model.Produto;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Repositório de produtos com catálogo estático.
 * Simula um banco de dados de produtos disponíveis na loja.
 */
public class ProdutoRepository {

    private static final Map<Integer, Produto> produtos = new HashMap<>();

    static {
        produtos.put(1, new Produto(1, "Teclado Mecânico",   299.90, 10));
        produtos.put(2, new Produto(2, "Mouse Gamer",         149.90, 15));
        produtos.put(3, new Produto(3, "Monitor 24\"",        899.00,  5));
        produtos.put(4, new Produto(4, "Headset USB",         199.90,  8));
        produtos.put(5, new Produto(5, "Webcam Full HD",      249.90, 12));
    }

    public Optional<Produto> buscarPorId(int id) {
        return Optional.ofNullable(produtos.get(id));
    }

    public List<Produto> listarTodos() {
        return Collections.unmodifiableList(List.copyOf(produtos.values()));
    }
}
