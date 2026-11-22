
public class Main {

    public static void testPPay(PPay pp) {
        System.out.println(pp.getCardOwnerName());
        System.out.println(pp.getCustCardNo());
        System.out.println(pp.getCardExpMonthDate());
        System.out.println(pp.getCVVNo());
        System.out.println(pp.getTotalAmount());
    }

    public static void main(String[] args) {

        // Criar o pagamento do sistema antigo
        MPay pagamentoAntigo = new MpayImpl();

        // Configurar dados ficticios para o teste
        pagamentoAntigo.setCreditCardNo("CARTAO-TESTE-001");
        pagamentoAntigo.setCustomerName("Cliente Teste");
        pagamentoAntigo.setCardExpMonth("12");
        pagamentoAntigo.setCardExpYear("2030");
        pagamentoAntigo.setCardCVVNo((short) 123);
        pagamentoAntigo.setAmount(150.0);

        // Adaptar MPay para PPay
        PPay pagamentoAdaptado =
                new MPayToPPayAdapter(pagamentoAntigo);

        // Testar os dados pela interface PPay
        System.out.println("=== Dados via PPay ===");
        testPPay(pagamentoAdaptado);

        // Alterar os valores utilizando PPay
        pagamentoAdaptado.setCardExpMonthDate("08/2031");
        pagamentoAdaptado.setTotalAmount(200.0);

        // Conferir as alteracoes no objeto MPay original
        System.out.println("=== Alteracoes no MPay ===");

        System.out.println("Mes: "
                + pagamentoAntigo.getCardExpMonth());

        System.out.println("Ano: "
                + pagamentoAntigo.getCardExpYear());

        System.out.println("Valor: "
                + pagamentoAntigo.getAmount());
    }
}
