# Sistema de Pagamentos — Padrão Adapter

**Lista Avaliativa II — Padrões de Projetos Orientados a Objetos**

**Questão 3 — Pagamentos**

## 1. Sobre o projeto

Este projeto foi desenvolvido em Java com o objetivo de demonstrar a aplicação do padrão de projeto **Adapter** na integração entre dois sistemas de pagamento que possuem interfaces diferentes.

O cenário da atividade envolve a substituição de um sistema de pagamento baseado na interface `MPay` por outro que utiliza a interface `PPay`.

Embora os dois sistemas trabalhem com informações semelhantes, como número do cartão, nome do cliente, validade, CVV e valor da transação, os métodos utilizados para acessar esses dados são diferentes.

Para resolver essa incompatibilidade, foi proposta uma classe adaptadora que permite utilizar um objeto `MPay` por meio da interface `PPay`, sem modificar os arquivos originais fornecidos.

## 2. Objetivo

Aplicar o padrão Adapter para permitir a comunicação entre interfaces incompatíveis, preservando o funcionamento do sistema existente.

Os principais objetivos são:

- Reutilizar os arquivos Java fornecidos na atividade.
- Implementar a interface `PPay` por meio de uma classe adaptadora.
- Converter os métodos e os tipos de dados necessários.
- Demonstrar o funcionamento do Adapter por meio de uma classe de teste.
- Evitar alterações nas interfaces e na implementação original de `MPay`.

## 3. Padrão de projeto utilizado

O **Adapter** é um padrão de projeto estrutural que permite que objetos com interfaces incompatíveis trabalhem juntos.

Neste projeto, a classe `MPayToPPayAdapter` implementa a interface `PPay` e recebe um objeto `MPay` no construtor.

Quando um método de `PPay` é chamado, o Adapter utiliza o método correspondente de `MPay` para realizar a operação.

Por exemplo:

```java
@Override
public String getCardOwnerName() {
    return pagamentoAntigo.getCustomerName();
}
```

Dessa forma, o sistema que utiliza `PPay` consegue acessar as informações do objeto original sem conhecer os métodos específicos de `MPay`.

### Participantes do padrão

| Participante | Classe ou interface | Responsabilidade |
|---|---|---|
| Target | `PPay` | Interface esperada pelo novo sistema |
| Adaptee | `MPay` | Interface original que precisa ser adaptada |
| Concrete Adaptee | `MpayImpl` | Implementação original dos dados de pagamento |
| Adapter | `MPayToPPayAdapter` | Converte as chamadas entre as interfaces |
| Cliente | `Main` | Demonstra a utilização do objeto adaptado |

## 4. Estrutura do projeto

```text
Pagamentos/
├── src/
│   ├── MPay.java
│   ├── MpayImpl.java
│   ├── PPay.java
│   ├── MPayToPPayAdapter.java
│   └── Main.java
├── docs/
│   └── ia.md
└── README.md
```

### Descrição dos arquivos

**MPay.java:** interface original do sistema de pagamento, responsável por definir os métodos de acesso aos dados do cartão e da transação.

**MpayImpl.java:** classe que implementa `MPay`, armazenando os dados do pagamento e disponibilizando seus getters e setters.

**PPay.java:** interface esperada pelo novo sistema, com métodos diferentes para representar informações semelhantes.

**MPayToPPayAdapter.java:** classe que implementa `PPay` e adapta suas operações para os métodos disponíveis em `MPay`.

**Main.java:** classe de demonstração responsável por criar o objeto original, utilizar o Adapter e verificar a compatibilidade entre as interfaces.

## 5. Diferenças entre as interfaces

As principais incompatibilidades identificadas são:

| MPay | PPay |
|---|---|
| `getCreditCardNo()` | `getCustCardNo()` |
| `getCustomerName()` | `getCardOwnerName()` |
| `getCardExpMonth()` e `getCardExpYear()` | `getCardExpMonthDate()` |
| `getCardCVVNo()` — `Short` | `getCVVNo()` — `Integer` |
| `getAmount()` | `getTotalAmount()` |

Os setters também possuem diferenças equivalentes.

### Conversão da validade

Na interface `MPay`, o mês e o ano da validade são armazenados separadamente.

Na interface `PPay`, a validade é representada por uma única `String`.

Para esta implementação, foi adotado o formato `MM/AAAA`.

Exemplo:

```text
MPay:
Mes: 12
Ano: 2030

PPay:
12/2030
```

O Adapter combina os valores na leitura e utiliza `split()` para separá-los na escrita.

### Conversão do CVV

A interface `MPay` utiliza `Short`, enquanto a interface `PPay` utiliza `Integer`.

O Adapter realiza a conversão entre esses tipos e inclui uma validação simplificada para evitar valores fora do intervalo aceito pela implementação.

## 6. Funcionamento do Adapter

O processo de adaptação ocorre da seguinte maneira:

**1. Criar o objeto original**

```java
MPay pagamentoAntigo = new MpayImpl();
```

**2. Preencher os dados**

```java
pagamentoAntigo.setCustomerName("Cliente Teste");
pagamentoAntigo.setCardExpMonth("12");
pagamentoAntigo.setCardExpYear("2030");
pagamentoAntigo.setAmount(150.0);
```

**3. Criar o Adapter**

```java
PPay pagamentoAdaptado =
        new MPayToPPayAdapter(pagamentoAntigo);
```

**4. Utilizar a interface PPay**

```java
System.out.println(pagamentoAdaptado.getCardOwnerName());
System.out.println(pagamentoAdaptado.getCardExpMonthDate());
System.out.println(pagamentoAdaptado.getTotalAmount());
```

A classe adaptadora permite que o objeto original seja utilizado por meio da interface nova.

O mesmo princípio se aplica aos setters, que encaminham as alterações ao objeto `MPay`.

## 7. Requisitos para execução

Para executar o projeto, é necessário:

- Java JDK instalado.
- Terminal ou PowerShell.
- Os arquivos `.java` organizados dentro da pasta `src`.

O projeto não necessita de bibliotecas externas.

## 8. Como compilar e executar

Abra o PowerShell na pasta principal do projeto.

### Compilar

```powershell
javac -encoding UTF-8 -d out src/*.java
```

Esse comando compila os arquivos Java da pasta `src` e armazena as classes compiladas na pasta `out`.

### Executar

Depois que a compilação terminar sem erros:

```powershell
java -cp out Main
```

A classe `Main` contém o método responsável por iniciar a demonstração.

**Observação:** o código Java deve estar salvo nos arquivos `.java`. Não é necessário colar o conteúdo das classes diretamente no PowerShell.

## 9. Demonstração do funcionamento

A classe `Main` utiliza um método que recebe um objeto do tipo `PPay`:

```java
public static void testPPay(PPay pp) {
    System.out.println(pp.getCardOwnerName());
    System.out.println(pp.getCustCardNo());
    System.out.println(pp.getCardExpMonthDate());
    System.out.println(pp.getCVVNo());
    System.out.println(pp.getTotalAmount());
}
```

O método recebe o Adapter e acessa os dados utilizando exclusivamente os métodos da interface `PPay`.

### Saída esperada

Considerando os dados fictícios utilizados na demonstração:

```text
=== Dados via PPay ===
Cliente Teste
CARTAO-TESTE-001
12/2030
123
150.0
=== Alteracoes no MPay ===
Mes: 08
Ano: 2031
Valor: 200.0
```

Esse resultado demonstra que os dados originais podem ser consultados pela interface `PPay` e que as alterações feitas pela interface adaptada são refletidas no objeto `MPay`.

A saída deverá ser confirmada por meio da execução no ambiente utilizado para a entrega.

## 10. Testes e validação

Os testes propostos para verificar o funcionamento da solução incluem:

- Leitura dos dados de `MPay` por meio da interface `PPay`.
- Atualização do nome do cliente pela interface adaptada.
- Conversão da validade entre os formatos utilizados pelas interfaces.
- Conversão do CVV entre `Integer` e `Short`.
- Atualização do valor da transação pela interface `PPay`.
- Verificação do comportamento diante de valores inválidos.

**Status dos testes:** [Preencher após executar e validar o projeto.]

## 11. Limitações

O projeto foi desenvolvido para fins acadêmicos e representa apenas a adaptação entre interfaces Java.

Não realiza transações financeiras reais, comunicação com serviços de pagamento ou processamento de cartões.

A validação dos dados é simplificada. Os dados empregados na demonstração são fictícios.

Em sistemas reais, informações sensíveis de pagamento, especialmente o CVV, exigem controles específicos de segurança e não devem ser exibidas ou armazenadas indevidamente.

## 12. Utilização de inteligência artificial

A inteligência artificial foi utilizada como ferramenta de apoio à compreensão e à implementação do padrão Adapter.

As orientações incluíram a análise dos arquivos fornecidos, a identificação de incompatibilidades, a criação da classe adaptadora, as conversões necessárias e a resolução de dificuldades durante a execução.

As interações, explicações e alterações realizadas durante o desenvolvimento estão documentadas em [docs/ia.md](docs/ia.md).

## 13. Autoria

**Integrantes:** [Inserir os nomes da dupla]

**Disciplina:** Padrões de Projetos Orientados a Objetos

**Atividade:** Lista Avaliativa II — Questão 3

**Padrão implementado:** Adapter
