# ☕ Byte & Brew: Sistema de Vendas e Fidelidade

> **Trabalho Prático - Orientação por Objetos (OO)** > **Universidade de Brasília (UnB) - Faculdade do Gama (FGA)** > **Professor:** André Luiz Peron Martins Lanna  

## 📜 Sobre o Projeto

O **Byte & Brew** é um sistema de gerenciamento de vendas e programa de fidelidade construído inteiramente em **Java**. O projeto foi desenvolvido para atender às regras de negócio de uma cafeteria temática focada no público geek, entusiastas de tecnologia e jogadores de RPG.

O objetivo principal do repositório é demonstrar a aplicação prática e rigorosa dos pilares da **Orientação a Objetos**, englobando desde a abstração das regras de negócio até o encapsulamento estrito e a manipulação de exceções customizadas.

## ⚙️ Funcionalidades Principais

O sistema gerencia o fluxo completo de atendimento da cafeteria através de três módulos principais:

* **Gestão de Cardápio:** Cadastro e controle de estoque de `Produtos`, divididos em `Comidas` (com restrições alimentares e tempo de preparo) e `Bebidas` (com variação de tamanho e dosagem de cafeína).
* **Programa de Fidelidade (XP):** Sistema de acúmulo de pontos baseado no gasto do cliente. 
    * **Cliente Standard (Aventureiro Iniciante):** Acumula 1 XP por Real gasto.
    * **Cliente VIP (Mestre da Guilda):** Acumula 2 XP por Real gasto e possui a exclusividade de pagar seus pedidos resgatando pontos.
* **Fluxo de Vendas (Pedidos):** Abertura de comandas, adição de múltiplos itens com controle de quantidade, cálculo automático de totais, aplicação de descontos em dias de eventos geeks e baixa automática de estoque.

## 🧩 Conceitos de OO Aplicados

Para garantir a qualidade arquitetural e a nota máxima nos critérios de avaliação, a implementação destaca os seguintes conceitos:

- [x] **Encapsulamento Estrito:** Proteção dos atributos utilizando modificadores `private` e `protected`, com acesso exclusivo via métodos de negócio.
- [x] **Herança Simples:** Hierarquia clara entre `Produto` (superclasse abstrata), `Comida` e `Bebida`; e entre `Cliente`, `ClienteStandard` e `ClienteVIP`.
- [x] **Polimorfismo:**
  - **Por Inclusão:** A classe `Pedido` processa itens genéricos tratados como `Produto`.
  - **Por Sobrescrita:** Lógica de pontuação de XP customizada nas subclasses de Cliente.
  - **Por Sobrecarga:** Diferentes assinaturas no método `adicionarItem()` dentro da classe Pedido.
  - **Por Coerção:** Conversão consciente de tipos durante os cálculos financeiros e de XP.
- [x] **Tratamento de Exceções Customizadas:** Lançamento de `EstoqueInsuficienteException` e `PontosInsuficientesException` para garantir a consistência das regras de negócio.
- [x] **Interfaces:** Uso da interface `Promocional` para injeção de regras de desconto sazonais.

## 📂 Estrutura de Pacotes

A arquitetura do código foi desenhada de forma modular:

* `br.edu.cafeteria.modelo`: Contém as entidades principais (Produto, Comida, Bebida, Cliente, Pedido).
* `br.edu.cafeteria.excecao`: Classes de exceções personalizadas de negócio.
* `br.edu.cafeteria.servico`: Interfaces e motores de validação/desconto.
* `br.edu.cafeteria.app`: Classe principal executável contendo o método `main`.

## 🚀 Como Executar

1. Certifique-se de ter o **Java JDK** instalado (versão recomendada: 17 ou superior).
2. Clone este repositório:
   ```bash
   git clone [https://github.com/seu-usuario/byte-and-brew.git](https://github.com/igordev-exe/byte-and-brew.git)
