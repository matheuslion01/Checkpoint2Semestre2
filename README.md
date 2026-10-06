# Checkpoint 02 - API REST com Spring Boot e SQL Server

API REST desenvolvida em Java com Spring Boot para realizar operações de consulta, inserção, alteração e exclusão de dados armazenados em um banco de dados Microsoft SQL Server.

## Tecnologias utilizadas

- Java 17
- Spring Boot 4
- Spring Web
- Spring Data JPA
- Hibernate
- Microsoft SQL Server
- Docker
- Maven
- Swagger / OpenAPI
- Lombok

## Estrutura do projeto

```text
src/
└── main/
    ├── java/br/com/fiap/cp01_api02/
    │   ├── controller/
    │   │   ├── BrasileiraoController.java
    │   │   └── OlimpiadasController.java
    │   ├── model/
    │   │   ├── Brasileirao.java
    │   │   └── Olimpiadas.java
    │   ├── repository/
    │   │   ├── BrasileiraoRepository.java
    │   │   └── OlimpiadasRepository.java
    │   └── Application.java
    │
    └── resources/
        ├── application.properties
        ├── application-prd.properties
        └── schema-prd.sql
```

## Banco de dados

A aplicação utiliza **Microsoft SQL Server**.

Configuração utilizada no ambiente local:

| Configuração | Valor |
|---|---|
| SGBD | Microsoft SQL Server |
| Servidor | localhost |
| Porta | 1433 |
| Usuário | sa |
| Banco | api |

A senha do usuário `sa` deve ser definida localmente e **não deve ser publicada no GitHub**.

## Executando o SQL Server com Docker

Certifique-se de que o Docker esteja instalado e em execução.

Execute:

```bash
docker run -d --name sqlserver --rm -e MSSQL_SA_PASSWORD="<SUA_SENHA>" -e "ACCEPT_EULA=Y" -p 1433:1433 mcr.microsoft.com/mssql/server:latest
```

Substitua `<SUA_SENHA>` pela senha configurada para o usuário `sa`.

Para verificar se o container está em execução:

```bash
docker ps
```

O container deverá aparecer com o nome:

```text
sqlserver
```

## Criando o banco de dados

Acesse o SQL Server pelo DBeaver ou outro cliente SQL e execute:

```sql
CREATE DATABASE api;
```

Depois, selecione o banco `api`.

No DBeaver, também é possível executar:

```sql
USE api;
```

### Tabelas

No perfil padrão de desenvolvimento, a aplicação utiliza:

```properties
spring.jpa.hibernate.ddl-auto=update
```

Dessa forma, o Hibernate pode criar/atualizar as tabelas correspondentes às entidades quando a aplicação for executada.

As tabelas utilizadas são:

- `brasileiroes`
- `olimpiadas`

## Configuração da aplicação

O arquivo:

```text
src/main/resources/application.properties
```

deve apontar para o SQL Server.

Exemplo:

```properties
spring.application.name=checkpoint01-semestre2
server.port=8080

springdoc.swagger-ui.path=/

spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=api;encrypt=false;trustServerCertificate=true
spring.datasource.username=sa
spring.datasource.password=<SUA_SENHA>

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.database-platform=org.hibernate.dialect.SQLServerDialect
```

Substitua `<SUA_SENHA>` pela senha configurada no SQL Server.

## Executando a aplicação

O projeto possui Maven Wrapper, portanto não é necessário ter o comando `mvn` instalado globalmente.

### Windows

Na pasta raiz do projeto:

```powershell
.\mvnw.cmd clean
```

Depois:

```powershell
.\mvnw.cmd spring-boot:run
```

### Linux / macOS

```bash
./mvnw clean
```

Depois:

```bash
./mvnw spring-boot:run
```

A aplicação será executada na porta:

```text
8080
```

## Swagger

Após iniciar a aplicação, acesse:

```text
http://localhost:8080/
```

O Swagger permite visualizar e testar todos os endpoints da API.

## Endpoints

### Campeonato Brasileiro

#### Listar todos

```http
GET /campeonatobrasileiro
```

#### Buscar por ID

```http
GET /campeonatobrasileiro/{id}
```

Exemplo:

```http
GET /campeonatobrasileiro/1
```

#### Inserir

```http
POST /campeonatobrasileiro
```

Exemplo de JSON:

```json
{
  "id": 1,
  "campeao": "Flamengo",
  "artilheiro": "Pedro",
  "revelacao": "Endrick",
  "goleada": "Flamengo 6x0 Vasco"
}
```

#### Alterar

```http
PUT /campeonatobrasileiro/{id}
```

Exemplo:

```http
PUT /campeonatobrasileiro/1
```

JSON:

```json
{
  "id": 1,
  "campeao": "Palmeiras",
  "artilheiro": "Pedro",
  "revelacao": "Endrick",
  "goleada": "Flamengo 6x0 Vasco"
}
```

#### Excluir

```http
DELETE /campeonatobrasileiro/{id}
```

Exemplo:

```http
DELETE /campeonatobrasileiro/1
```

---

### Olimpíadas

#### Listar todos

```http
GET /olimpiadas
```

#### Buscar por ID

```http
GET /olimpiadas/{id}
```

Exemplo:

```http
GET /olimpiadas/1
```

#### Inserir

```http
POST /olimpiadas
```

Exemplo de JSON:

```json
{
  "id": 1,
  "ano": "2024",
  "sede": "Paris",
  "maiorMedalhista": "Estados Unidos",
  "maiorVencedor": "Léon Marchand"
}
```

#### Alterar

```http
PUT /olimpiadas/{id}
```

Exemplo:

```http
PUT /olimpiadas/1
```

JSON:

```json
{
  "id": 1,
  "ano": "2024",
  "sede": "Paris",
  "maiorMedalhista": "Estados Unidos",
  "maiorVencedor": "Léon Marchand"
}
```

#### Excluir

```http
DELETE /olimpiadas/{id}
```

Exemplo:

```http
DELETE /olimpiadas/1
```

## Testando a persistência no SQL Server

Após realizar uma operação de inserção pela API, é possível verificar diretamente no SQL Server se o registro foi armazenado.

No DBeaver:

```sql
USE api;

SELECT * FROM brasileiroes;
```

Para consultar os dados de Olimpíadas:

```sql
USE api;

SELECT * FROM olimpiadas;
```

Também é possível verificar alterações e exclusões realizadas pelos endpoints `PUT` e `DELETE`.

## Fluxo da aplicação

```text
Cliente / Swagger
       ↓
Controller
       ↓
Spring Data JPA
       ↓
Repository
       ↓
Hibernate
       ↓
SQL Server
```

## Persistência

A aplicação utiliza Spring Data JPA para realizar a persistência dos dados.

As entidades são:

- `Brasileirao`
- `Olimpiadas`

Os repositories são:

- `BrasileiraoRepository`
- `OlimpiadasRepository`

As operações disponíveis incluem:

- Consulta de registros
- Consulta por ID
- Inserção
- Alteração
- Exclusão

## Execução com Docker da aplicação

O projeto também possui um `Dockerfile` para gerar a imagem da aplicação.

Para criar a imagem:

```bash
docker build -t checkpoint02-api .
```

Para executar a aplicação:

```bash
docker run --name checkpoint02-api -p 8080:8080 checkpoint02-api
```

A conexão com o SQL Server deve estar corretamente configurada para que a aplicação consiga acessar o banco.

## Checklist para demonstração

Antes da apresentação/avaliação, verificar:

- [ ] Docker está executando o SQL Server
- [ ] Container `sqlserver` está ativo
- [ ] Banco `api` existe
- [ ] Aplicação inicia sem erros
- [ ] Swagger abre em `http://localhost:8080/`
- [ ] `GET /campeonatobrasileiro` funciona
- [ ] `POST /campeonatobrasileiro` grava no SQL Server
- [ ] `PUT /campeonatobrasileiro/{id}` altera no SQL Server
- [ ] `DELETE /campeonatobrasileiro/{id}` remove do SQL Server
- [ ] `GET /olimpiadas` funciona
- [ ] `POST /olimpiadas` grava no SQL Server
- [ ] `PUT /olimpiadas/{id}` altera no SQL Server
- [ ] `DELETE /olimpiadas/{id}` remove do SQL Server

## Autores

Projeto desenvolvido para atividade acadêmica da FIAP.

**Integrantes:**

- Nome: ______________________________ — RM: ______________
- Nome: ______________________________ — RM: ______________
