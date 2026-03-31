package lojinha.repository;

import lojinha.model.Cliente;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Repositório de clientes com dados estáticos pré-cadastrados.
 * Simula um banco de dados de clientes já existentes no sistema.
 */
public class ClienteRepository {

    // Clientes criados estaticamente conforme requisito do enunciado
    private static final Map<Integer, Cliente> clientes = new HashMap<>();

    static {
        clientes.put(1, new Cliente(1, "Alice Souza",   "alice@email.com"));
        clientes.put(2, new Cliente(2, "Bruno Lima",    "bruno@email.com"));
        clientes.put(3, new Cliente(3, "Carla Mendes",  "carla@email.com"));
    }

    public Optional<Cliente> buscarPorId(int id) {
        return Optional.ofNullable(clientes.get(id));
    }

    public List<Cliente> listarTodos() {
        return Collections.unmodifiableList(List.copyOf(clientes.values()));
    }
}