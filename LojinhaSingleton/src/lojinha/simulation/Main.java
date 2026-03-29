package lojinha.simulation;

import lojinha.LojinhaServidor;
import lojinha.model.Cliente;
import lojinha.model.Pedido;
import lojinha.payment.ConexaoPagamentoSingleton;

/**
 * Ponto de entrada da aplicação.
 *
 * Simula o fluxo completo descrito no Diagrama N0:
 *   1. Cliente acessa a loja (servidor inicializa)
 *   2. Sistema exibe os produtos
 *   3. Cliente seleciona produtos e adiciona ao carrinho
 *   4. Cliente finaliza a compra
 *   5. Sistema cria o pedido e processa o pagamento via gateway (Singleton)
 *   6. Sistema confirma ou cancela conforme o resultado
 */
public class Main {

    public static void main(String[] args) {

        separador("LOJINHA ONLINE - SIMULAÇÃO DO SISTEMA");

        // ─── Inicializa o servidor monolítico ───────────────────────────────
        LojinhaServidor servidor = new LojinhaServidor();

        // ─── CENÁRIO 1: Compra com múltiplos itens ──────────────────────────
        separador("CENÁRIO 1 — Compra de Alice (múltiplos itens)");

        // Passo 1: Identificação do cliente
        Cliente alice = servidor.getClienteService().identificarCliente(1);
        System.out.println("[CLIENTE] Identificado: " + alice);

        // Passo 2: Exibe catálogo (Sistema exibe os produtos)
        servidor.getProdutoService().exibirCatalogo();

        // Passo 3: Cria pedido e adiciona itens ao carrinho
        Pedido pedido1 = servidor.getPedidoService().criarPedido(alice);
        servidor.getPedidoService().adicionarItem(pedido1, 1, 1); // Teclado Mecânico
        servidor.getPedidoService().adicionarItem(pedido1, 2, 2); // 2x Mouse Gamer

        // Passo 4: Finaliza compra (pagamento + confirmação/cancelamento)
        servidor.getPedidoService().finalizarCompra(pedido1);

        // ─── CENÁRIO 2: Compra simples de Bruno ─────────────────────────────
        separador("CENÁRIO 2 — Compra de Bruno (item único)");

        Cliente bruno = servidor.getClienteService().identificarCliente(2);
        System.out.println("[CLIENTE] Identificado: " + bruno);

        servidor.getProdutoService().exibirCatalogo();

        Pedido pedido2 = servidor.getPedidoService().criarPedido(bruno);
        servidor.getPedidoService().adicionarItem(pedido2, 3, 1); // Monitor 24"

        servidor.getPedidoService().finalizarCompra(pedido2);

        // ─── CENÁRIO 3: Verifica que o Singleton é a mesma instância ────────
        separador("VERIFICAÇÃO DO SINGLETON");

        ConexaoPagamentoSingleton instancia1 = ConexaoPagamentoSingleton.getInstancia();
        ConexaoPagamentoSingleton instancia2 = ConexaoPagamentoSingleton.getInstancia();

        System.out.println("Instância 1 hash: " + System.identityHashCode(instancia1));
        System.out.println("Instância 2 hash: " + System.identityHashCode(instancia2));
        System.out.println("São a mesma instância? " + (instancia1 == instancia2));
        System.out.println("Session ID: " + instancia1.getSessionId());

        separador("FIM DA SIMULAÇÃO");
    }

    private static void separador(String titulo) {
        System.out.println("\n" + "=".repeat(55));
        System.out.println("  " + titulo);
        System.out.println("=".repeat(55));
    }
}
