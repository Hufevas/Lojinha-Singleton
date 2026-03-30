package lojinha.exception;

/**
 * Lançada quando um cliente é referenciado por ID inexistente no sistema.
 */
public class ClienteNaoEncontradoException extends RuntimeException {

    public ClienteNaoEncontradoException(int id) {
        super("Cliente com ID " + id + " não encontrado.");
    }
}
