<div align="center">

# ☕ Byte & Brew

### Sistema de Vendas e Fidelidade para uma Cafeteria Geek

![Java](https://img.shields.io/badge/Java-21%2B-orange)
![Paradigma](https://img.shields.io/badge/Paradigma-POO-blue)
![Status](https://img.shields.io/badge/Status-Concluído-success)
![Licença](https://img.shields.io/badge/Licença-Acadêmica-green)

> *"Onde cada gole vira experiência e cada compra sobe de nível."*

</div>

---

## 🗺️ Sobre o Projeto

A **Byte & Brew** é uma cafeteria temática voltada para o público geek, entusiastas de tecnologia, cultura pop, jogos de tabuleiro e literatura de fantasia. Este sistema gerencia seu cardápio, clientes e vendas, com um programa de fidelidade baseado em **pontos de XP**.

O projeto foi desenvolvido como Trabalho Prático da disciplina de **Orientação a Objetos** da **Universidade de Brasília (UnB/FGA)**, com o objetivo de aplicar na prática conceitos fundamentais de **Programação Orientada a Objetos (POO)**.

---

## ✨ Funcionalidades

- 🧾 **Gestão de Pedidos** — abertura, adição de itens (com validação de estoque) e finalização com cálculo de total
- 🍰 **CRUD de Produtos** — cadastro, atualização de preço/estoque e remoção de Comidas e Bebidas
- 👤 **CRUD de Clientes** — cadastro, atualização de nome e remoção do programa de fidelidade
- 💜 **Programa de Fidelidade** — clientes Standard e VIP acumulam XP por compra, com taxas diferentes
- 🎮 **Pagamento com XP** — clientes VIP podem quitar pedidos inteiros com pontos
- 🎉 **Dia de Evento Geek** — desconto promocional de 10% em todas as bebidas
- ⚠️ **Controle de Estoque** — bloqueio automático de vendas sem estoque disponível
- 🔍 **Pesquisa** — busca de produtos por código e de clientes por CPF
- 🧪 **Teste de Mesa Automatizado** — demonstração completa dos conceitos OO sem input manual

---

## ⚙️ Requisitos

### Java

O projeto foi desenvolvido e testado utilizando o **JDK 21 ou superior**. Não são utilizadas APIs preview nem recursos restritos a uma versão de patch específica.

Verifique sua versão instalada:

```bash
java --version
javac --version
```

---

## 🛠 Tecnologias Utilizadas

| Tecnologia                          | Finalidade                          |
| ------------------------------------ | ------------------------------------ |
| Java 21+                             | Linguagem principal                  |
| Console / `java.util.Scanner`        | Interface de linha de comando (CLI)  |
| Git/GitHub                           | Controle de versão                   |
| Programação Orientada a Objetos      | Estruturação do sistema              |

---

## 🚀 Como Executar

### 1. Clonar o repositório

```bash
git clone https://github.com/igordev-exe/byte-and-brew.git
cd byte-and-brew
```

### 2. Compilar o projeto

**Windows (cmd):**
```cmd
javac src/br/edu/cafeteria/app/*.java ^
      src/br/edu/cafeteria/modelo/*.java ^
      src/br/edu/cafeteria/servico/*.java ^
      src/br/edu/cafeteria/excecao/*.java
```

**Linux/macOS (bash):**
```bash
javac src/br/edu/cafeteria/app/*.java \
      src/br/edu/cafeteria/modelo/*.java \
      src/br/edu/cafeteria/servico/*.java \
      src/br/edu/cafeteria/excecao/*.java
```

### 3. Executar

```bash
java -cp src br.edu.cafeteria.app.Main
```

> Todas as interações do sistema são realizadas via console (linha de comando), usando `java.util.Scanner` para leitura da entrada do usuário.

---

## 🏛️ Arquitetura e Conceitos OO Aplicados

| Conceito                          | Onde é aplicado                                                                 |
| ---------------------------------- | -------------------------------------------------------------------------------- |
| **Herança Simples**                | `Comida` e `Bebida` estendem `Produto`; `ClienteStandard` e `ClienteVIP` estendem `Cliente` |
| **Polimorfismo por Sobrescrita**   | `calcularXPGanho()` tem comportamento diferente em cada subclasse de `Cliente`   |
| **Polimorfismo por Sobrecarga**    | `adicionarItem(Produto)` e `adicionarItem(Produto, int)` em `Pedido`             |
| **Polimorfismo por Inclusão**      | A classe `ItemPedido` e os métodos de `Pedido` processam instâncias do tipo abstrato `Produto` (que dinamicamente podem ser `Comida` ou `Bebida`).                     |
| **Polimorfismo por Coerção**       | Conversão implícita de `int` para `double` em `ItemPedido.getSubtotal()` (quantidade × preço) |
| **Classes Abstratas**              | `Produto` e `Cliente`                                                            |
| **Interface**                      | `Promocional`, implementada por `DiaEventoGeek` para aplicar descontos           |
| **Exceções Customizadas**          | `EstoqueInsuficienteException`, `PontosInsuficientesException` e `QuantidadeInvalidaException` (checked) |
| **Encapsulamento**                 | Todos os atributos são `private`/`protected`, expostos apenas via getters/setters |
| **Atributo Estático**              | Contador sequencial automático de pedidos e constante `TAXA_CONVERSAO`           |

---

## 📦 Estrutura de Pacotes

```text
src/
└── br/edu/cafeteria/
    ├── app/
    │   └── Main.java                        # Ponto de entrada e menus
    ├── excecao/
    │   ├── EstoqueInsuficienteException.java
    │   ├── PontosInsuficientesException.java
    │   └── QuantidadeInvalidaException.java
    ├── modelo/
    │   ├── Produto.java
    │   ├── Comida.java
    │   ├── Bebida.java
    │   ├── Cliente.java
    │   ├── ClienteStandard.java
    │   ├── ClienteVIP.java
    │   ├── Pedido.java
    │   ├── ItemPedido.java
    │   ├── Atendente.java
    │   ├── Tamanho.java          # enum
    │   └── Temperatura.java      # enum
    └── servico/
        ├── Promocional.java      # interface
        ├── DiaEventoGeek.java
        └── GerenciadorCafeteria.java
```

---

## 🃏 Cardápio Inicial (dados pré-carregados)

| Código | Nome                  | Tipo               | Preço    | Estoque |
| ------ | ---------------------- | ------------------- | -------- | ------- |
| C01    | Lembas Bread           | Comida (vegano)      | R$ 15,00 | 10      |
| B01    | Poção de Mana          | Bebida (gelada, M)   | R$ 12,00 | 20      |
| B02    | Café do Programador    | Bebida (quente, G)   | R$ 8,00  | 50      |

### Clientes pré-cadastrados

| CPF | Nome         | Categoria    | XP Inicial |
| --- | ------------ | ------------ | ---------- |
| 111 | Ada Lovelace | VIP 👑       | 0 XP       |
| 222 | Alan Turing  | Standard ⚔️  | 0 XP       |

---

## 📜 Regras de Negócio

- **Cliente Standard** → ganha `1 XP` por R$ 1,00 gasto
- **Cliente VIP** → ganha `2 XP` por R$ 1,00 gasto + pode pagar pedidos com XP
- **Taxa de conversão XP** → `10 XP = R$ 1,00` (exclusivo VIP)
- **Dia de Evento Geek** → `10% de desconto` sobre o total de bebidas do pedido
- **Estoque zerado** → venda bloqueada com `EstoqueInsuficienteException`
- **Quantidade inválida** → adicionar 0 ou quantidade negativa ao pedido é bloqueado com `QuantidadeInvalidaException`
- **XP insuficiente** → pagamento com pontos bloqueado com `PontosInsuficientesException`
- **CPF** → validado estruturalmente (11 dígitos numéricos) e não pode se repetir entre clientes cadastrados
- **Código de produto** → não pode se repetir entre produtos cadastrados

---

## 🎓 Trabalho Acadêmico

**Universidade de Brasília (UnB)**
**Faculdade do Gama (FGA)**
**Disciplina:** Orientação a Objetos
**Professor:** André Luiz Peron Martins Lanna

---

## 👨‍💻 Autores

- [André Alves - 252003794](https://github.com/a-alvezx)

    <img src="https://avatars.githubusercontent.com/u/204260728?v=4" width="150px" style="border-radius: 100px;">

- [Igor Alves - 252026242](https://github.com/igordev-exe)

    <img src="https://avatars.githubusercontent.com/u/224191295?v=4" width="150px" style="border-radius: 100px;">

- [Felippe Ong Su - 242028664](https://github.com/felpp-su)

    <img src="https://avatars.githubusercontent.com/u/197075315?v=4" width="150px" style="border-radius: 100px;">

---

## 📄 Licença

Projeto desenvolvido exclusivamente para fins acadêmicos na disciplina de Orientação a Objetos da Universidade de Brasília.
