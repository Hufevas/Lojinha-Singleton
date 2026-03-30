package lojinha.service;

import lojinha.exception.ClienteNaoEncontradoException;
import lojinha.model.Cliente;
import lojinha.repository.ClienteRepository;

public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    /**
     * Identifica o cliente pelo ID. Falha se o cadastro não existir.
     */
    public Cliente identificarCliente(int id) {
        return clienteRepository.buscarPorId(id)
                .orElseThrow(() -> new ClienteNaoEncontradoException(id));
    }

    /**
     * Autentica o cliente: confere se o ID existe e se o e-mail informado
     * corresponde ao cadastro.
     */
    public Cliente autenticarCliente(int id, String email) {
        Cliente cliente = identificarCliente(id);
        if (email == null || !cliente.getEmail().equalsIgnoreCase(email.trim())) {
            throw new IllegalArgumentException("E-mail não confere com o cadastro do cliente.");
        }
        return cliente;
    }
}
