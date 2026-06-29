# Sistema de Gestão de Emergência (JPA/Hibernate) - POB-emergencia

Este projeto é uma aplicação Java desktop desenvolvida para a disciplina de **Persistência de Objetos (POB)** do Curso Superior de Tecnologia em Sistemas para Internet no **IFPB**. O sistema gerencia a triagem de pacientes em Unidades de Pronto Atendimento (UPAs), aplicando os conceitos de persistência relacional com JPA/Hibernate, arquitetura em camadas e tratamento de carregamento tardio (*lazy loading*).

---

## Tecnologias e Configurações

* **Linguagem:** Java 17+
* **Framework ORM:** JPA 3.0 / Hibernate 7.4.1.Final
* **Banco de Dados:** PostgreSQL (principal, local) e MySQL (opcional/configurado)
* **Design Pattern / Arquitetura:** Model-View-Controller (MVC) distribuído em camadas de persistência, requisitos (Fachadas) e interface gráfica (Swing).

---

## Arquitetura do Projeto

O código está estruturado sob a pasta `src` nos seguintes pacotes:

```text
src/
├── appconsole/      # Aplicações de teste em modo console (CRUD e Consultas)
├── appswing/        # Interface Gráfica com Swing (Dashboard e Painéis de Gestão)
├── imagens/         # Recursos visuais (como a logo da aplicação)
├── META-INF/        # Configuração do JPA (persistence.xml)
├── modelo/          # Classes de negócio (entidades mapeadas no JPA)
├── repossitorio/    # Camada de Persistência (DAO Genérico e Repositórios especializados)
├── requisito/       # Camada de Negócio / Fachadas (Validações, regras e controle de sessão)
└── util/            # Utilitários de conexão com o banco e arquivos de propriedades
```

---

## Camadas em Detalhes

### 1. Modelo de Domínio (`modelo`)
Contém as entidades mapeadas como tabelas no banco de dados. Todas utilizam carregamento preguiçoso (`FetchType.LAZY`) em seus relacionamentos para otimização de performance.

* **[Paciente](file:///home/ian/Faculdade/POB/Projetos/POB-emergencia/src/modelo/Paciente.java):** Representa o paciente atendido. Identificado por CPF e possui um histórico de atendimentos (`List<Atendimento>`). Contém o atributo `@Lob` do tipo `byte[] foto` para armazenar a foto do paciente no banco.
* **[Upa](file:///home/ian/Faculdade/POB/Projetos/POB-emergencia/src/modelo/Upa.java):** Representa a Unidade de Pronto Atendimento. Possui uma lista de atendimentos vinculados.
* **[Atendimento](file:///home/ian/Faculdade/POB/Projetos/POB-emergencia/src/modelo/Atendimento.java):** Relaciona um `Paciente` a uma `Upa`. Armazena a data (`LocalDate`) e a descrição dos sintomas (`triagem`).

### 2. Repositórios (`repossitorio`)
Responsáveis pelas operações de CRUD e queries JPQL complexas.
* **`Repositorio<T>`:** Classe genérica abstrata que abstrai as operações básicas (criar, ler, atualizar, deletar) utilizando o `EntityManager`.
* **[RepositorioPaciente](file:///home/ian/Faculdade/POB/Projetos/POB-emergencia/src/repossitorio/RepositorioPaciente.java):** Contém queries para buscar pacientes que visitaram múltiplas UPAs e pacientes com mais de N visitas em uma UPA.
* **[RepositorioUpa](file:///home/ian/Faculdade/POB/Projetos/POB-emergencia/src/repossitorio/RepositorioUpa.java):** Implementa o ranking de lotação das UPAs e a listagem de UPAs visitadas por um paciente específico (via CPF).
* **[RepositorioAtendimento](file:///home/ian/Faculdade/POB/Projetos/POB-emergencia/src/repossitorio/RepositorioAtendimento.java):** Fornece queries para busca de atendimentos por data, por CPF de paciente e por palavra-chave na triagem.

### 3. Fachadas / Regras de Negócio (`requisito`)
Centralizam as regras de validação (ex: CPF com 11 dígitos, campos obrigatórios) e o gerenciamento do ciclo de vida das transações do JPA (`begin`, `commit`, `rollback` e `desconectar`).
* **[FachadaPaciente](file:///home/ian/Faculdade/POB/Projetos/POB-emergencia/src/requisito/FachadaPaciente.java)**, **[FachadaUpa](file:///home/ian/Faculdade/POB/Projetos/POB-emergencia/src/requisito/FachadaUpa.java)** e **[FachadaAtendimento](file:///home/ian/Faculdade/POB/Projetos/POB-emergencia/src/requisito/FachadaAtendimento.java)**.
* **Evitando o erro de Sessão (*LazyInitializationException*):** Como os relacionamentos são `LAZY`, ao fechar a conexão, acessar coleções disparava erros. As fachadas agora realizam o carregamento preventivo (acessando campos como `size()` ou getters) enquanto a transação ainda está ativa no banco, permitindo que a interface ou os consoles utilizem as entidades com segurança após o fechamento da sessão.

### 4. Interface de Usuário
O projeto pode ser executado de duas maneiras:

#### A. Módulo Console (`appconsole`)
Útil para testar a persistência, popular o banco de dados e rodar relatórios rápidos via terminal:
* **[Cadastrar](file:///home/ian/Faculdade/POB/Projetos/POB-emergencia/src/appconsole/Cadastrar.java):** Popula o banco de dados com UPAs, Pacientes e Atendimentos de exemplo.
* **[Listar](file:///home/ian/Faculdade/POB/Projetos/POB-emergencia/src/appconsole/Listar.java):** Imprime todas as entidades do banco.
* **[Consultar](file:///home/ian/Faculdade/POB/Projetos/POB-emergencia/src/appconsole/Consultar.java):** Roda as queries complexas de relacionamento (ex: ranking de lotação, múltiplas UPAs, etc.).
* **[Alterar](file:///home/ian/Faculdade/POB/Projetos/POB-emergencia/src/appconsole/Alterar.java)** / **[Apagar](file:///home/ian/Faculdade/POB/Projetos/POB-emergencia/src/appconsole/Apagar.java):** Demonstra a remoção de entidades e seus vínculos em cascata.

#### B. Módulo Gráfico Swing (`appswing`)
Interface visual de administração moderna contendo:
* **[TelaPrincipal](file:///home/ian/Faculdade/POB/Projetos/POB-emergencia/src/appswing/TelaPrincipal.java):** Dashboard central com navegação no menu lateral (Sidebar) integrado a uma Logo Oficial ([logo.png](file:///home/ian/Faculdade/POB/Projetos/POB-emergencia/src/imagens/logo.png)).
* **Painéis de CRUD:** Gerenciamento visual com tabelas e formulários para [Paciente](file:///home/ian/Faculdade/POB/Projetos/POB-emergencia/src/appswing/PainelPaciente.java), [Upa](file:///home/ian/Faculdade/POB/Projetos/POB-emergencia/src/appswing/PainelUpa.java) e [Atendimento](file:///home/ian/Faculdade/POB/Projetos/POB-emergencia/src/appswing/PainelAtendimento.java).
* **[PainelConsulta](file:///home/ian/Faculdade/POB/Projetos/POB-emergencia/src/appswing/PainelConsulta.java):** Aba dedicada a realizar todas as consultas complexas em tempo real com filtros customizados e exibição automática em tabelas adaptáveis.

---

## 💾 Banco de Dados & Propriedades

O arquivo de configuração do Hibernate está em [src/META-INF/persistence.xml](file:///home/ian/Faculdade/POB/Projetos/POB-emergencia/src/META-INF/persistence.xml), possuindo duas unidades de persistência:
1. `hibernate-postgresql` (PostgreSQL local na porta 5432)
2. `hibernate-mysql` (MySQL local na porta 3306)

O arquivo utilitário [src/util/util.properties](file:///home/ian/Faculdade/POB/Projetos/POB-emergencia/src/util/util.properties) define qual banco de dados usar em tempo de execução:
```ini
sgbd=postgresql
banco=pob
usuario=postgres
senha=admin
ip=localhost
```

---

## Como Executar o Projeto

Você pode rodar as aplicações utilizando o seu Ambiente de Desenvolvimento (IDE) favorito (IntelliJ IDEA ou Eclipse) bastando abrir a pasta raiz do projeto.

### Pelo Terminal (Linux / Bash)
Caso possua as dependências locais no repositório Maven, você pode compilar e rodar manualmente:

1. **Compilar o Projeto:**
   ```bash
   mkdir -p target/classes
   CP=$(find ~/.m2/repository -name "*.jar" -not -name "*sources.jar" | paste -sd ":" -)
   javac -cp "$CP" -d target/classes $(find src -name "*.java")
   ```

2. **Popular o Banco de Dados (Cadastrar):**
   ```bash
   CP=$(find ~/.m2/repository -name "*.jar" -not -name "*sources.jar" | paste -sd ":" -)
   java -cp "$CP:target/classes:src" appconsole.Cadastrar
   ```

3. **Rodar as Consultas JPA (Console):**
   ```bash
   CP=$(find ~/.m2/repository -name "*.jar" -not -name "*sources.jar" | paste -sd ":" -)
   java -cp "$CP:target/classes:src" appconsole.Consultar
   ```

4. **Abrir a Interface Gráfica (Swing):**
   ```bash
   CP=$(find ~/.m2/repository -name "*.jar" -not -name "*sources.jar" | paste -sd ":" -)
   java -cp "$CP:target/classes:src" appswing.TelaPrincipal
   ```
