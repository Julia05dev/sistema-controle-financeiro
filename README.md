# Sistema de Gerenciamento Financeiro

![Java](https://img.shields.io/badge/Java-15-ED8B00?logo=openjdk&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-JDBC-4169E1?logo=postgresql&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-build-C71A36?logo=apachemaven&logoColor=white)
![JUnit](https://img.shields.io/badge/JUnit-5-25A162?logo=junit5&logoColor=white)

Aplicação de linha de comando para gerenciamento de receitas e despesas, desenvolvida em Java e integrada ao PostgreSQL por meio de JDBC.

O sistema permite registrar, consultar, filtrar e remover lançamentos financeiros, calcular o saldo atual e gerar relatórios mensais diretamente a partir do banco de dados. A implementação utiliza arquitetura em camadas, valores monetários com `BigDecimal`, transações com `commit` e `rollback` e regras de integridade na aplicação e no PostgreSQL.

## Funcionalidades

- Cadastro de receitas e despesas
- IDs gerados pelo PostgreSQL
- Persistência dos lançamentos em banco de dados
- Listagem e remoção de lançamentos por ID
- Cálculo do saldo atual
- Filtros por data, tipo, categoria e meio de movimentação
- Validação da compatibilidade entre tipo, categoria e meio de movimentação
- Tratamento de entradas inválidas no terminal
- Testes unitários e de integração com JUnit 5
- Relatório mensal com:
  - total de receitas
  - total de despesas
  - saldo do período
  - receitas agrupadas por categoria
  - despesas agrupadas por categoria
  - lançamentos realizados no mês

## Evolução do projeto

O sistema começou com persistência local em CSV e evoluiu para uma estrutura baseada em PostgreSQL e JDBC.

Essa migração transformou o banco de dados na fonte principal dos lançamentos e permitiu implementar consultas agregadas para geração de relatórios mensais.

Principais avanços desta versão:

- substituição do CSV pelo PostgreSQL
- adoção do padrão DAO para acesso aos dados
- separação entre aplicação, serviço, modelo e persistência
- uso de `BigDecimal` de ponta a ponta para valores monetários
- geração dos IDs exclusivamente pelo banco de dados
- consultas com `SUM`, `GROUP BY` e filtro por período
- sincronização entre a carteira em memória e os dados persistidos
- cobertura de regras de negócio, filtros, DAO e serviço com testes automatizados

## Tecnologias e conceitos utilizados

- Java 15
- Maven
- PostgreSQL
- JDBC
- JUnit 5
- `BigDecimal` para valores monetários
- Java Date/Time API com `LocalDate`
- Collections como `List`, `HashMap`, `EnumMap` e `LinkedHashMap`
- Programação orientada a objetos
- Arquitetura em camadas
- Padrão DAO
- SQL com agregações, agrupamentos e restrições de integridade
- Transações com `commit` e `rollback`
- Variáveis de ambiente para proteção da senha do banco

## Arquitetura

```text
src/
├── main/java/sistemaFinanceiro/
│   ├── aplicacao/
│   │   └── Main.java
│   ├── modelo/
│   │   ├── Carteira.java
│   │   ├── Lancamento.java
│   │   ├── RelatorioMensal.java
│   │   ├── enums/
│   │   └── filtros/
│   ├── persistencia/
│   │   ├── conexaoJDBC/
│   │   └── dao/
│   └── servico/
│       └── SistemaFinanceiro.java
└── test/java/sistemaFinanceiro/
    ├── modelo/
    ├── persistencia/
    └── servico/
```

### Responsabilidades das camadas

- `Main`: interação com o usuário por meio do terminal
- `SistemaFinanceiro`: coordenação das operações e regras do sistema
- `Lancamento`: representação de uma receita ou despesa
- `Carteira`: gerenciamento dos lançamentos mantidos em memória
- `RelatorioMensal`: organização dos resultados consolidados do período
- `LancamentoDAO`: inserção, consulta, exclusão e agregação de dados no PostgreSQL
- `SingleConnection`: criação e gerenciamento da conexão JDBC

## Banco de dados

O script de criação da tabela está disponível em [`database/schema.sql`](database/schema.sql).

A tabela `lancamento` utiliza:

- chave primária gerada como `IDENTITY`
- `NUMERIC(12,2)` para armazenamento dos valores monetários
- restrições `CHECK` para os tipos de lançamento
- restrições de compatibilidade entre tipo e categoria
- restrições de compatibilidade entre tipo e meio de movimentação

Os valores são armazenados como positivos. A diferenciação entre receitas e despesas é feita pelo tipo do lançamento durante o cálculo do saldo.

## Relatório mensal

O relatório mensal é produzido por consultas SQL executadas diretamente no banco de dados.

O período é delimitado pelo primeiro dia do mês selecionado e pelo primeiro dia do mês seguinte. As consultas utilizam:

- `SUM` para calcular os totais
- `GROUP BY` para agrupar valores por tipo e categoria
- ordenação das categorias pelo valor total
- filtro por intervalo de datas

O resultado é organizado pela classe `RelatorioMensal` e apresentado ao usuário pela interface de linha de comando.

## Como executar

### Pré-requisitos

- Java 15 ou superior
- Maven
- PostgreSQL
- Uma IDE com suporte a projetos Maven, como VS Code ou IntelliJ IDEA

### 1. Clone o repositório

```bash
git clone https://github.com/Julia05dev/sistema-controle-financeiro.git
cd sistema-controle-financeiro
```

### 2. Crie o banco principal

No PostgreSQL, crie um banco chamado:

```text
sistema_financeiro
```

Depois, execute nesse banco o conteúdo do arquivo:

```text
database/schema.sql
```

Por padrão, a aplicação utiliza a seguinte conexão:

```text
jdbc:postgresql://localhost:5432/sistema_financeiro
```

E o seguinte usuário:

```text
postgres
```

Caso seu ambiente utilize outros dados, ajuste a URL ou o usuário em `SingleConnection.java`.

### 3. Configure a senha do PostgreSQL

A senha não fica armazenada no código. Ela deve ser informada pela variável de ambiente `DB_PASSWORD`.

No PowerShell:

```powershell
$env:DB_PASSWORD="sua_senha"
```

No Linux ou macOS:

```bash
export DB_PASSWORD="sua_senha"
```

### 4. Compile o projeto

```bash
mvn clean compile
```

Depois, execute pela IDE a classe:

```text
src/main/java/sistemaFinanceiro/aplicacao/Main.java
```

## Testes

Os testes automatizados abrangem:

- criação e validação de lançamentos
- operações da carteira
- cálculo do saldo
- filtros por data, tipo, categoria e meio de movimentação
- conexão JDBC
- inserção, consulta e exclusão no DAO
- sincronização entre o serviço, a carteira e o banco de dados

Para executar os testes de integração, crie também um banco PostgreSQL chamado:

```text
teste
```

Aplique nesse banco o mesmo arquivo:

```text
database/schema.sql
```

Mantenha a variável `DB_PASSWORD` configurada e execute:

```bash
mvn clean test
```

Os testes de persistência verificam o nome do banco antes de limpar a tabela, evitando que os dados do banco principal sejam removidos durante a execução.

## Próximas evoluções

- Implementar a atualização de lançamentos existentes
- Ampliar os testes das consultas do relatório mensal
- Disponibilizar as funcionalidades por meio de uma API REST com Spring Boot
- Evoluir a persistência para JPA/Hibernate
- Adicionar autenticação e suporte a múltiplos usuários em uma versão futura

## Objetivo

Este projeto foi desenvolvido para aplicar fundamentos de back-end em um sistema completo e evolutivo, passando pela modelagem das regras de negócio, persistência relacional, consultas SQL, organização em camadas e testes automatizados.

Mais do que um exercício isolado, ele registra a evolução técnica de uma aplicação inicialmente local para uma solução integrada a banco de dados e preparada para futuras etapas de desenvolvimento web.

## Autora

Desenvolvido por [Julia](https://github.com/Julia05dev), estudante de Ciência da Computação com foco em desenvolvimento back-end.