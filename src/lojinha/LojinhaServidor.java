package lojinha;

import lojinha.repository.ClienteRepository;
import lojinha.repository.ProdutoRepository;
import lojinha.service.ClienteService;
import lojinha.service.PedidoService;
import lojinha.service.ProdutoService;

/**
 * Representa o servidor monolítico da lojinha online.
 *
 * Em uma arquitetura cliente-servidor monolítica, este componente
 * centraliza todas as responsabilidades do backend:
 *   - Gerenciamento de clientes
 *   - Gerenciamento de produtos
 *   - Processamento de pedidos e pagamentos
 *
 * Expõe os serviços para que os "clientes" (simulação) possam
 * interagir com o sistema.
 */
public class LojinhaServidor {

    // Repositórios
    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;

    // Serviços
    private final ClienteService clienteService;
    private final ProdutoService produtoService;
    private final PedidoService   pedidoService;

    public LojinhaServidor() {
        // Inicialização das dependências (injeção manual - sem framework)
        this.clienteRepository = new ClienteRepository();
        this.produtoRepository  = new ProdutoRepository();

        this.clienteService = new ClienteService(clienteRepository);
        this.produtoService  = new ProdutoService(produtoRepository);
        this.pedidoService   = new PedidoService(produtoService);

        System.out.println("[SERVIDOR] Lojinha Online inicializada.");
    }

    public ClienteService getClienteService() { return clienteService; }
    public ProdutoService getProdutoService()  { return produtoService; }
    public PedidoService  getPedidoService()   { return pedidoService; }
}