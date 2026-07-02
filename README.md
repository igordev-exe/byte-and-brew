# ☕ Byte & Brew — Sistema de Vendas e Fidelidade
 
> *"Onde cada gole vira experiência e cada compra sobe de nível."*
 
Sistema de gerenciamento de vendas e fidelidade para a cafeteria temática geek **Byte & Brew**, desenvolvido como Trabalho Prático da disciplina de **Orientação a Objetos** — UnB/FGA.

---
## 🗺️ Sobre o Projeto
 
A **Byte & Brew** é uma cafeteria temática voltada para o público geek, entusiastas de tecnologia, cultura pop, jogos de tabuleiro e literatura de fantasia. Este sistema gerencia seu cardápio, clientes e vendas, com um programa de fidelidade baseado em **pontos de XP**.
 
---
## ✨ Funcionalidades
 
- 🧾 **Gestão de Pedidos** — abertura, adição de itens e finalização com cálculo de total
- 🍰 **Cardápio Completo** — cadastro de Comidas e Bebidas com atributos específicos
- 👤 **Programa de Fidelidade** — clientes Standard e VIP acumulam XP por compra
- 💜 **Pagamento com XP** — clientes VIP podem quitar pedidos inteiros com pontos
- 🎉 **Dia de Evento Geek** — desconto promocional de 10% em todas as bebidas
- ⚠️ **Controle de Estoque** — bloqueio automático de vendas sem estoque disponível
- 🧪 **Teste de Mesa Automatizado** — demonstração completa dos conceitos OO sem input manual
---
## 🏛️ Arquitetura e Conceitos OO Aplicados
 
| Conceito | Onde é aplicado |
|---|---|
| **Herança Simples** | `Comida` e `Bebida` estendem `Produto`; `ClienteStandard` e `ClienteVIP` estendem `Cliente` |
| **Polimorfismo por Sobrescrita** | `calcularXPGanho()` tem comportamento diferente em cada subclasse de `Cliente` |
| **Polimorfismo por Sobrecarga** | `adicionarItem(Produto)` e `adicionarItem(Produto, int)` em `Pedido` |
| **Polimorfismo por Inclusão** | `Pedido` armazena e processa uma lista genérica de `Produto` |
| **Polimorfismo por Coerção** | Downcasting de `Cliente` para `ClienteVIP` no fluxo de pagamento com XP |
| **Interface** | `Promocional` implementada por `DiaEventoGeek` para aplicar descontos |
| **Exceções Customizadas** | `EstoqueInsuficienteException` e `PontosInsuficientesException` (checked) |
| **Encapsulamento** | Todos os atributos são `private`/`protected`, expostos apenas via getters/setters |
| **Atributo Estático** | Contador sequencial automático de pedidos e constante `TAXA_CONVERSAO` |
 
---
## 📦 Estrutura de Pacotes
 
```
src/
└── br/edu/cafeteria/
    ├── app/
    │   └── Main.java                        # Ponto de entrada e menus
    ├── excecao/
    │   ├── EstoqueInsuficienteException.java
    │   └── PontosInsuficientesException.java
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
 
| Código | Nome | Tipo | Preço | Estoque |
|---|---|---|---|---|
| C01 | Lembas Bread | Comida (vegano) | R$ 15,00 | 10 |
| B01 | Poção de Mana | Bebida (gelada, M) | R$ 12,00 | 20 |
| B02 | Café do Programador | Bebida (quente, G) | R$ 8,00 | 50 |


### Clientes pré-cadastrados
 
| CPF | Nome | Categoria | XP Inicial |
|---|---|---|---|
| 111 | Ada Lovelace | VIP 👑 | 0 XP |
| 222 | Alan Turing | Standard ⚔️ | 0 XP |
 
---

## 📜 Regras de Negócio
 
- **Cliente Standard** → ganha `1 XP` por R$ 1,00 gasto
- **Cliente VIP** → ganha `2 XP` por R$ 1,00 gasto + pode pagar pedidos com XP
- **Taxa de conversão XP** → `10 XP = R$ 1,00` (exclusivo VIP)
- **Dia de Evento Geek** → `10% de desconto` sobre o total de bebidas do pedido
- **Estoque zerado** → venda bloqueada com `EstoqueInsuficienteException`
- **XP insuficiente** → pagamento com pontos bloqueado com `PontosInsuficientesException`
---
 
## 🔗 Link da página
[Página Responsiva](https://a-alvezx.github.io/Pagina-Responsiva/)
## Autores
- [André Alves - 252003794](https://github.com/a-alvezx)

    <img src="https://avatars.githubusercontent.com/u/204260728?v=4" width="150px" style="border-radius: 100px;">
- [Igor Alves - 252026242](https://github.com/igordev-exe)

    <img src="https://avatars.githubusercontent.com/u/224191295?v=4" width="150px" style="border-radius: 100px;">
- [Felippe Ong Su - 242028664](https://github.com/felpp-su)

    <img src="https://avatars.githubusercontent.com/u/197075315?v=4" width="150px" style="border-radius: 100px;">
## 📚 Disciplina
 
**Orientação a Objetos** — Prof. André Luiz Peron Martins Lanna  
Universidade de Brasília (UnB) — Faculdade do Gama (FGA)
