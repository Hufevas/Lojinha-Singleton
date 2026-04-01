package lojinha.service;

import lojinha.model.ItemPedido;
import lojinha.model.Pedido;
import lojinha.model.Cliente;
import lojinha.model.Produto;
import lojinha.payment.ConexaoPagamentoSingleton;
import lojinha.payment.ResultadoPagamento;

/**
 * Serviço responsável pelo ciclo de vida de um pedido:
 *   1. Criação do pedido vinculado ao cliente
 *   2. Adição de itens com validação de estoque
 *   3. Finalização: pagamento via gateway Singleton + confirmação ou cancelamento
 */
public class PedidoService {

    private final ProdutoService produtoService;

    public PedidoService(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    /**
     * Cria um novo pedido para o cliente informado.
     * O pedido começa com status AGUARDANDO_PAGAMENTO e sem itens.
     */
    public Pedido criarPedido(Cliente cliente) {
        Pedido pedido = new Pedido(cliente);
        System.out.printf("%n[PEDIDO] Pedido #%d criado para %s | Status: %s%n",
                pedido.getId(), cliente.getNome(), pedido.getStatus());
        return pedido;
    }

    /**
     * Busca o produto pelo ID, valida estoque e adiciona o item ao pedido.
     *
     * @throws IllegalArgumentException se produto não encontrado
     * @throws IllegalStateException    se estoque insuficiente
     */
    public void adicionarItem(Pedido pedido, int produtoId, int quantidade) {
        Produto produto = produtoService.buscarPorId(produtoId);

        if (produto.getEstoque() < quantidade) {
            throw new IllegalStateException(
                    String.format("Estoque insuficiente para '%s'. Disponível: %d | Solicitado: %d",
                            produto.getNome(), produto.getEstoque(), quantidade));
        }

        ItemPedido item = new ItemPedido(produto, quantidade);
        pedido.adicionarItem(item);

        System.out.printf("[CARRINHO] Adicionado: %s x%d (subtotal: R$%.2f)%n",
                produto.getNome(), quantidade, item.getSubtotal());
    }

    /**
     * Finaliza a compra: envia ao gateway de pagamento e atualiza o status do pedido.
     * Se aprovado, reduz o estoque dos produtos. Se recusado, cancela o pedido.
     */
    public void finalizarCompra(Pedido pedido) {
        System.out.println("\n[PEDIDO] Resumo antes do pagamento:");
        System.out.println(pedido);

        // Acessa o gateway via Singleton
        ConexaoPagamentoSingleton gateway = ConexaoPagamentoSingleton.getInstancia();
        ResultadoPagamento resultado = gateway.processarPagamento(pedido.getId(), pedido.calcularTotal());

        System.out.println("[PAGAMENTO] " + resultado.getMensagem());

        if (resultado.isAprovado()) {
            // Reduz estoque de cada produto
            pedido.getItens().forEach(item ->
                    item.getProduto().reduzirEstoque(item.getQuantidade()));

            pedido.confirmar();
            System.out.printf("[PEDIDO] ✅ Pedido #%d CONFIRMADO | Transação: %s%n",
                    pedido.getId(), resultado.getCodigoTransacao());
        } else {
            pedido.cancelar();
            System.out.printf("[PEDIDO] ❌ Pedido #%d CANCELADO. Nenhuma cobrança realizada.%n",
                    pedido.getId());
        }
    }
}
