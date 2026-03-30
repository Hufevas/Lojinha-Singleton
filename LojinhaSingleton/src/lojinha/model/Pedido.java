package lojinha.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Representa um pedido realizado por um cliente.
 * Agrega os itens selecionados e controla o status do pedido.
 */
public class Pedido {

    private static int contadorId = 1;

    private final int id;
    private final Cliente cliente;
    private final List<ItemPedido> itens;
    private StatusPedido status;

    public Pedido(Cliente cliente) {
        this.id = contadorId++;
        this.cliente = cliente;
        this.itens = new ArrayList<>();
        this.status = StatusPedido.AGUARDANDO_PAGAMENTO;
    }

    public void adicionarItem(ItemPedido item) {
        itens.add(item);
    }

    public double calcularTotal() {
        return itens.stream()
                .mapToDouble(ItemPedido::getSubtotal)
                .sum();
    }

    public void confirmar() {
        this.status = StatusPedido.CONFIRMADO;
    }

    public void cancelar() {
        this.status = StatusPedido.CANCELADO;
    }

    public int getId() { return id; }
    public Cliente getCliente() { return cliente; }
    public List<ItemPedido> getItens() { return Collections.unmodifiableList(itens); }
    public StatusPedido getStatus() { return status; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Pedido #%d | Cliente: %s | Status: %s%n",
                id, cliente.getNome(), status));
        itens.forEach(item -> sb.append(item).append("\n"));
        sb.append(String.format("  TOTAL: R$%.2f", calcularTotal()));
        return sb.toString();
    }
}