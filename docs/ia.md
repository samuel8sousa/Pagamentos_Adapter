# Registro de Utilização de Inteligência Artificial — Questão 3

**Disciplina:** Padrões de Projetos Orientados a Objetos  
**Atividade:** Lista Avaliativa II — Questão 3  
**Tema:** Pagamentos — Padrão Adapter  
**Linguagem:** Java  
**Ferramenta de IA:** ChatGPT (OpenAI)  
**Integrantes:** [Inserir nomes da dupla]

---

## 1. Objetivo da utilização da IA

A inteligência artificial foi utilizada como ferramenta de apoio para compreender e implementar o padrão de projeto Adapter em Java.

O objetivo foi desenvolver uma solução que permitisse utilizar os dados de pagamento de um sistema baseado na interface `MPay` por meio da interface `PPay`, sem modificar as interfaces originais fornecidas na atividade.

Durante o desenvolvimento, a IA auxiliou na interpretação dos arquivos, na explicação do padrão, na construção da classe adaptadora, na elaboração dos testes e na identificação de problemas durante a execução.

As sugestões recebidas foram apresentadas com explicações sobre o funcionamento de cada método, permitindo compreender a implementação e avaliar as alterações necessárias.

---

## 2. Primeira interação — Análise dos arquivos fornecidos

**Prompt enviado:**

> esses são os arquivos da terceira questão para utilizar

**Arquivos fornecidos:**

- `MPay.java`
- `MpayImpl.java`
- `PPay.java`

**Orientação recebida:**

A IA analisou as interfaces e a classe de implementação fornecidas.

Foi identificado que `MPay` e `PPay` representam informações semelhantes de pagamento, mas apresentam diferenças nos nomes dos métodos e nos tipos utilizados.

Entre as incompatibilidades identificadas estavam:

| Informação | Interface MPay | Interface PPay |
|---|---|---|
| Número do cartão | `getCreditCardNo()` | `getCustCardNo()` |
| Nome do cliente | `getCustomerName()` | `getCardOwnerName()` |
| Validade | Mês e ano separados | Data em uma única String |
| CVV | `Short` | `Integer` |
| Valor | `getAmount()` | `getTotalAmount()` |

**Decisão sugerida:**

Criar uma classe adaptadora que implementasse `PPay` e recebesse um objeto `MPay` por meio do construtor.

Essa abordagem permitiria reaproveitar os arquivos originais sem alterar seus contratos.

---

## 3. Segunda interação — Implementação passo a passo

**Prompt enviado:**

> mostre o passo a passo

**Orientação recebida:**

A IA apresentou uma sequência de implementação, começando pela organização do projeto e pela análise das interfaces.

Foi sugerida a criação da classe `MPayToPPayAdapter`, responsável por implementar todos os métodos da interface `PPay` e encaminhar as operações aos métodos correspondentes da interface `MPay`.

A estrutura principal apresentada foi:

```java
public class MPayToPPayAdapter implements PPay {

    private final MPay pagamentoAntigo;

    public MPayToPPayAdapter(MPay pagamentoAntigo) {
        this.pagamentoAntigo = pagamentoAntigo;
    }
}
```

**Explicação recebida:**

A IA explicou que `implements PPay` permite que a classe adaptadora seja utilizada em qualquer método que receba um objeto `PPay`.

Já o atributo `pagamentoAntigo` armazena a referência ao objeto original, responsável por manter os dados do pagamento.

Essa solução utiliza composição para adaptar uma interface à outra.

### 3.1. Adaptação dos métodos

Foi explicado que vários métodos precisam apenas encaminhar as chamadas.

Por exemplo:

```java
@Override
public String getCardOwnerName() {
    return pagamentoAntigo.getCustomerName();
}
```

Nesse caso, a interface nova solicita o nome do proprietário do cartão, enquanto o objeto original fornece a informação por meio de `getCustomerName()`.

Também foram explicadas as adaptações dos métodos responsáveis por alterar os dados.

### 3.2. Conversão da validade

Uma das principais dificuldades identificadas foi a diferença na representação da validade do cartão.

Na interface `MPay`, o mês e o ano são armazenados separadamente. Na interface `PPay`, a validade é representada por uma única String.

A IA sugeriu utilizar o formato `MM/AAAA`, combinando os valores na leitura e separando-os na escrita.

Exemplo:

```text
MPay:
Mes = 12
Ano = 2030

PPay:
12/2030
```

Para separar os valores, foi apresentado o método `split()`.

**Justificativa:** a conversão permite preservar as informações de validade mesmo com estruturas diferentes entre os dois sistemas.

### 3.3. Conversão do CVV

Outra incompatibilidade identificada foi a utilização de `Short` em `MPay` e `Integer` em `PPay`.

A IA explicou a necessidade de converter os valores numéricos e verificar seus limites antes da conversão de `Integer` para `Short`.

Foi sugerida uma validação simplificada para impedir valores negativos ou superiores a 9999.

**Justificativa:** evitar conversões numéricas que possam alterar o valor original.

Os dados utilizados na demonstração são fictícios. O projeto não realiza transações financeiras reais.

---

## 4. Terceira interação — Criação do teste da classe Adapter

Durante a explicação passo a passo, a IA também orientou a criação de `Main.java`.

O objetivo desse arquivo é demonstrar que um objeto originalmente criado como `MPay` pode ser utilizado por meio da interface `PPay`.

Foi apresentado o método:

```java
public static void testPPay(PPay pp) {
    System.out.println(pp.getCardOwnerName());
    System.out.println(pp.getCustCardNo());
    System.out.println(pp.getCardExpMonthDate());
    System.out.println(pp.getCVVNo());
    System.out.println(pp.getTotalAmount());
}
```

**Orientação recebida:**

Primeiro, criar um objeto utilizando a implementação original:

```java
MPay pagamentoAntigo = new MpayImpl();
```

Depois, criar o Adapter:

```java
PPay pagamentoAdaptado =
        new MPayToPPayAdapter(pagamentoAntigo);
```

Por fim, utilizar:

```java
testPPay(pagamentoAdaptado);
```

**Justificativa:**

Essa demonstração permite verificar que o Adapter apresenta os métodos esperados pela interface `PPay`, mesmo utilizando internamente um objeto `MPay`.

Também foi sugerido testar os setters da interface nova e verificar se os valores foram atualizados no objeto original.

---

## 5. Quarta interação — Problema durante a execução

**Situação apresentada à IA:**

Foi compartilhada a saída do PowerShell contendo diversos erros após a tentativa de executar o código da classe `Main`.

Entre as mensagens apresentadas estavam:

```text
System.out.println : O termo 'System.out.println'
não é reconhecido como nome de cmdlet
```

E:

```text
Uma expressão era esperada após '('.
```

Também ocorreram erros envolvendo `PPay`, `pagamentoAntigo`, comentários iniciados por `//` e a conversão `(short) 123`.

**Diagnóstico apresentado pela IA:**

A IA identificou que o código Java havia sido inserido diretamente no PowerShell.

O problema ocorreu porque o PowerShell interpreta comandos de terminal, e não definições de classes Java.

**Correção orientada:**

Criar o arquivo `Main.java` dentro da pasta `src`, inserir o código nesse arquivo e salvá-lo.

Depois, utilizar os comandos:

```powershell
javac -encoding UTF-8 -d out src/*.java
java -cp out Main
```

**Explicação recebida:**

O comando `javac` compila os arquivos `.java`, enquanto `java` executa a classe compilada.

A IA também esclareceu que a instrução:

```java
pagamentoAntigo.setCardCVVNo((short) 123);
```

é válida em Java, pois realiza uma conversão explícita para o tipo `short`.

O erro apresentado anteriormente não comprovava um defeito nessa instrução, mas uma tentativa de interpretá-la utilizando o ambiente incorreto.

**Resultado:** foi identificada a causa dos erros apresentados. A compilação e a execução corretas no computador ainda precisam ser confirmadas.

---

## 6. Decisões técnicas e justificativas

| Decisão | Justificativa |
|---|---|
| Utilizar o padrão Adapter | Permitir compatibilidade entre interfaces diferentes |
| Implementar `PPay` na classe adaptadora | Atender ao contrato esperado pelo novo sistema |
| Receber `MPay` no construtor | Reaproveitar qualquer objeto compatível com a interface original |
| Utilizar composição | Adaptar o comportamento sem alterar a implementação existente |
| Preservar os arquivos originais | Evitar modificações desnecessárias nas classes fornecidas |
| Combinar mês e ano | Adaptar a representação da validade |
| Converter `Short` e `Integer` | Compatibilizar os tipos numéricos do CVV |
| Criar `Main.java` | Demonstrar o funcionamento do Adapter |
| Utilizar dados fictícios | Evitar o uso de informações reais de pagamento |

---

## 7. Testes e validação

A IA orientou a realização dos seguintes testes:

**Teste 1 — Leitura pela interface PPay**

Criar um objeto `MpayImpl`, preencher seus atributos, adaptá-lo e utilizar o método `testPPay()`.

Saída esperada para os dados fictícios apresentados:

```text
=== Dados via PPay ===
Cliente Teste
CARTAO-TESTE-001
12/2030
123
150.0
```

**Teste 2 — Alteração pela interface PPay**

Alterar a validade para `08/2031` e o valor para `200.0`, utilizando o objeto adaptado.

Depois, consultar os atributos do objeto `MPay` original.

Resultado esperado:

```text
=== Alteracoes no MPay ===
Mes: 08
Ano: 2031
Valor: 200.0
```

**Teste 3 — Conversão do CVV**

Verificar se um valor definido pela interface `PPay` é convertido corretamente e armazenado pela implementação de `MPay`.

**Teste 4 — Validade inválida**

Verificar o comportamento do Adapter quando a validade não apresenta o formato esperado.

**Resultados efetivamente obtidos:**

[Preencher após executar o programa e verificar a saída do terminal.]

**Erros encontrados durante os testes:**

Foi identificada uma tentativa de executar o código Java diretamente no PowerShell. A IA explicou a diferença entre escrever código Java em arquivos `.java` e executar comandos de compilação no terminal.

[Acrescentar outros erros somente se ocorrerem.]

---

## 8. Modificações realizadas pela dupla

Esta seção deve registrar as alterações próprias realizadas sobre as sugestões fornecidas pela IA.

| Arquivo | Alteração realizada | Justificativa |
|---|---|---|
| `MPayToPPayAdapter.java` | [Preencher] | [Preencher] |
| `Main.java` | [Preencher] | [Preencher] |
| `README.md` | [Preencher] | [Preencher] |

Caso algum código tenha sido utilizado sem alterações, isso deverá ser informado, sem atribuir à dupla modificações que não foram realizadas.

---

## 9. Histórico de commits

Os commits devem representar as etapas reais de desenvolvimento.

| Etapa | Mensagem sugerida | Hash real |
|---|---|---|
| Arquivos originais | `chore: adicionar interfaces e implementacao originais de pagamento` | [Preencher] |
| Implementação do Adapter | `feat: implementar adapter entre MPay e PPay` | [Preencher] |
| Classe de demonstração | `test: demonstrar compatibilidade entre MPay e PPay` | [Preencher] |
| Testes adicionais, se realizados | `test: validar conversoes de validade e CVV` | [Preencher] |
| Documentação | `docs: documentar implementacao e uso da IA` | [Preencher] |

As mensagens acima são sugestões. A tabela deverá ser ajustada para refletir os commits efetivamente realizados no repositório.

---

## 10. Considerações finais

A utilização da inteligência artificial auxiliou na compreensão do padrão Adapter e das diferenças entre as interfaces `MPay` e `PPay`.

As orientações permitiram estudar como uma classe adaptadora pode encaminhar métodos entre interfaces incompatíveis, transformar dados e preservar a implementação original.

A IA também contribuiu para identificar o uso incorreto do PowerShell durante a tentativa de execução, esclarecendo o processo de compilação e execução de programas Java.

O desenvolvimento da atividade possibilita exercitar conceitos de interfaces, encapsulamento, composição, conversão de tipos e reutilização de código.

A revisão da implementação, a execução dos testes e a responsabilidade pelo código entregue permanecem com a dupla.

---

**Ferramenta utilizada:** ChatGPT — OpenAI  
**Atividade:** Questão 3 — Pagamentos  
**Padrão de projeto:** Adapter
