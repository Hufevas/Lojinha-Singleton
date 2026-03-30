package lojinha.payment;

import java.util.Random;
import java.util.UUID;

/**
 * ============================================================
 *  PADRÃO DE PROJETO: SINGLETON
 * ============================================================
 *
 * POR QUÊ SINGLETON AQUI?
 *
 * A comunicação com o sistema externo de pagamento é um recurso
 * compartilhado e potencialmente caro de inicializar. Em um
 * cenário real, essa "conexão" envolveria:
 *   - Autenticação com credenciais (API key, OAuth token);
 *   - Estabelecimento de canal seguro (TLS/HTTPS);
 *   - Controle de rate-limit imposto pela gateway de pagamento.
 *
 * Permitir múltiplas instâncias dessa classe causaria:
 *   1. Desperdício de recursos (múltiplas conexões abertas);
 *   2. Inconsistência no controle de sessão/token;
 *   3. Risco de exceder limites da API de pagamento.
 *
 * O Singleton garante que exista UMA ÚNICA instância dessa
 * conexão durante todo o ciclo de vida da aplicação, centrali-
 * zando e controlando o acesso ao serviço externo.
 *
 * IMPLEMENTAÇÃO: Thread-safe com inicialização lazy usando
 * double-checked locking.
 * ============================================================
 */
public class ConexaoPagamentoSingleton {

    // volatile garante visibilidade entre threads (Java Memory Model)
    private static volatile ConexaoPagamentoSingleton instancia;

    private final String endpointUrl;
    private final String sessionId;
    private final Random random;

    // Construtor privado: impede instanciação externa
    private ConexaoPagamentoSingleton() {
        this.endpointUrl = "https://pagamento-externo.simulado.com/api/v1";
        this.sessionId   = UUID.randomUUID().toString();
        this.random      = new Random();

        System.out.println("[PAGAMENTO] Conexão estabelecida com gateway externo.");
        System.out.println("[PAGAMENTO] Session ID: " + sessionId);
        System.out.println("[PAGAMENTO] Endpoint  : " + endpointUrl);
    }

    /**
     * Retorna a única instância da conexão com o sistema de pagamento.
     * Double-checked locking garante thread-safety sem custo de
     * sincronização em cada chamada após a primeira.
     */
    public static ConexaoPagamentoSingleton getInstancia() {
        if (instancia == null) {
            synchronized (ConexaoPagamentoSingleton.class) {
                if (instancia == null) {
                    instancia = new ConexaoPagamentoSingleton();
                }
            }
        }
        return instancia;
    }

    /**
     * Simula o envio dos dados de pagamento ao sistema externo.
     * Em produção, faria uma requisição HTTP POST para a gateway.
     *
     * @param pedidoId  ID do pedido a ser pago
     * @param valor     Valor total da transação
     * @return ResultadoPagamento com status aprovado/reprovado
     */
    public ResultadoPagamento processarPagamento(int pedidoId, double valor) {
        System.out.printf("%n[PAGAMENTO] Enviando dados ao gateway externo...%n");
        System.out.printf("[PAGAMENTO] Pedido #%d | Valor: R$%.2f%n", pedidoId, valor);

        // Simula latência de rede (gateway externo)
        simularLatencia();

        // 80% de chance de aprovação (simulação)
        boolean aprovado = random.nextInt(10) < 8;
        String codigo    = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        String mensagem  = aprovado
                ? "Pagamento autorizado pela operadora."
                : "Pagamento recusado: saldo insuficiente.";

        System.out.printf("[PAGAMENTO] Status recebido: %s%n", aprovado ? "APROVADO" : "REPROVADO");

        return new ResultadoPagamento(aprovado, mensagem, aprovado ? codigo : "N/A");
    }

    public String getSessionId() { return sessionId; }

    private void simularLatencia() {
        try {
            Thread.sleep(500); // simula 500ms de resposta da gateway
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}