
public class Main {

    public static void testPPay(PPay pp) {
        System.out.println(pp.getCardOwnerName());
        System.out.println(pp.getCustCardNo());
        System.out.println(pp.getCardExpMonthDate());
        System.out.println(pp.getCVVNo());
        System.out.println(pp.getTotalAmount());
    }

    public static void main(String[] args) {

        // Criar objeto do sistema antigo
        MPay pagamentoAntigo = new MpayImpl();

        // Preencher com dados ficticios
        pagamentoAntigo.setCreditCardNo("CARTAO-TESTE-001");
        pagamentoAntigo.setCustomerName("Cliente Teste");
        pagamentoAntigo.setCardExpMonth("12");
        pagamentoAntigo.setCardExpYear("2030");
        pagamentoAntigo.setCardCVVNo((short) 123);
        pagamentoAntigo.setAmount(150.0);

        // Criar o Adapter
        PPay pagamentoAdaptado =
                new MPayToPPayAdapter(pagamentoAntigo);

        // Testar a interface PPay
        System.out.println("=== Dados via PPay ===");
        testPPay(pagamentoAdaptado);

        // Testar alteracoes pela interface nova
        pagamentoAdaptado.setCardExpMonthDate("08/2031");
        pagamentoAdaptado.setTotalAmount(200.0);

        // Conferir se o objeto antigo foi atualizado
        System.out.println(
                "=== Confirmando alteracoes no MPay ===");

        System.out.println("Mes: "
                + pagamentoAntigo.getCardExpMonth());

        System.out.println("Ano: "
                + pagamentoAntigo.getCardExpYear());

        System.out.println("Valor: "
                + pagamentoAntigo.getAmount());
    }
}
