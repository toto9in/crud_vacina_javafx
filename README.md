# CRUD_Vacina_JavaFX

## Descrição do Projeto

O projeto **crud_vacina_javafx** é uma aplicação JavaFX que visa simular a administração de vacinas em uma população. A interface gráfica permite realizar operações básicas de um CRUD em relação às vacinas disponíveis. Além disso, a aplicação possibilita o registro de aplicações de vacinas em pessoas, salvando essas informações em um banco de dados PostgreSQL.

## Funcionalidades

### 1. CRUD de Vacinas

#### 1.1 Tabela de Vacinas
Existe uma tabela na interface gráfica que exibe a lista de vacinas cadastradas. Cada linha da tabela apresenta informações como **Código**, **Nome** e **Descrição** da vacina. Essa tabela é atualizada à medida que novas vacinas são adicionadas, editadas ou removidas.

#### 1.2 Adicionar Vacina
Permite adicionar uma nova vacina à tabela, inserindo informações como código, nome e descrição.

#### 1.3 Editar Vacina
Permite modificar as informações de uma vacina existente na tabela, como nome e descrição.

#### 1.4 Remover Vacina
Permite excluir uma vacina da tabela.

#### 1.5 Pesquisar Vacina
Facilita a busca por vacinas específicas na tabela, oferecendo campos com filtro

### 2. Tabela de Pessoas

Exibe uma tabela que contém informações sobre pessoas, como código, nome, CPF e data de nascimento. Essa tabela serve como base para a aplicação de vacinas.

### 3. Aplicação de Vacina

#### 3.1 Seleção de Pessoa e Vacina
Após selecionar uma pessoa e vacina nas tabela, o usuário pode clicar no botão "criar aplicacão" para aplicar a vacina na determinada pessoa.

#### 3.2 Registro no Banco de Dados
Após clicar no botão "Criar Aplicação", as informações, incluindo a data de aplicação, o código da pessoa selecionada e o código da vacina escolhida, são inseridas na tabela "aplicacao" do banco de dados.

## Tecnologias Utilizadas

- **JavaFX:** Utilizado para a criação da interface gráfica.
- **PostgreSQL:** Banco de dados utilizado para armazenar informações sobre vacinas, pessoas e aplicações.

