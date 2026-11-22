
public class MPayToPPayAdapter implements PPay {

    private final MPay pagamentoAntigo;

    public MPayToPPayAdapter(MPay pagamentoAntigo) {
        if (pagamentoAntigo == null) {
            throw new IllegalArgumentException(
                    "O pagamento MPay nao pode ser nulo");
        }

        this.pagamentoAntigo = pagamentoAntigo;
    }

    @Override
    public String getCustCardNo() {
        return pagamentoAntigo.getCreditCardNo();
    }

    @Override
    public String getCardOwnerName() {
        return pagamentoAntigo.getCustomerName();
    }

    @Override
    public String getCardExpMonthDate() {
        String mes = pagamentoAntigo.getCardExpMonth();
        String ano = pagamentoAntigo.getCardExpYear();

        if (mes == null || ano == null) {
            return null;
        }

        return mes + "/" + ano;
    }

    @Override
    public Integer getCVVNo() {
        Short cvv = pagamentoAntigo.getCardCVVNo();

        return cvv == null
                ? null
                : Integer.valueOf(cvv.intValue());
    }

    @Override
    public Double getTotalAmount() {
        return pagamentoAntigo.getAmount();
    }

    @Override
    public void setCustCardNo(String custCardNo) {
        pagamentoAntigo.setCreditCardNo(custCardNo);
    }

    @Override
    public void setCardOwnerName(String cardOwnerName) {
        pagamentoAntigo.setCustomerName(cardOwnerName);
    }

    @Override
    public void setCardExpMonthDate(String cardExpMonthDate) {

        if (cardExpMonthDate == null) {
            pagamentoAntigo.setCardExpMonth(null);
            pagamentoAntigo.setCardExpYear(null);
            return;
        }

        String[] partes = cardExpMonthDate.split("/", -1);

        if (partes.length != 2
                || partes[0].trim().isEmpty()
                || partes[1].trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Validade deve ser MM/AAAA");
        }

        pagamentoAntigo.setCardExpMonth(partes[0].trim());
        pagamentoAntigo.setCardExpYear(partes[1].trim());
    }

    @Override
    public void setCVVNo(Integer cVVNo) {

        if (cVVNo != null && (cVVNo < 0 || cVVNo > 9999)) {
            throw new IllegalArgumentException(
                    "CVV fora do intervalo de 0 a 9999");
        }

        pagamentoAntigo.setCardCVVNo(
                cVVNo == null
                        ? null
                        : Short.valueOf(cVVNo.shortValue()));
    }

    @Override
    public void setTotalAmount(Double totalAmount) {
        pagamentoAntigo.setAmount(totalAmount);
    }
}
