# checkpoint01-semestre2 - API REST com Spring Boot

API REST desenvolvida com Spring Boot. O projeto possui dois domínios: **Brasileirão** e **Olimpíadas**.

- GitHub: `matheuslion01/checkpoint01-semestre2`
- Docker Hub: `ticorisa/checkpoint01-semestre2`

## Tecnologias utilizadas

- Java 17
- Spring Boot 4.0.3
- Spring Data JPA
- MySQL 8
- Lombok
- SpringDoc OpenAPI / Swagger UI
- Docker

## Profiles

A aplicação possui os profiles exigidos para a entrega:

- **default**: utilizado quando nenhum profile é informado. Neste ambiente, o banco e as tabelas podem ser criados/atualizados automaticamente.
- **prd**: utilizado para execução de produção. Neste profile, a aplicação **não cria banco nem tabelas automaticamente** (`spring.jpa.hibernate.ddl-auto=none`).

O profile `prd` é ativado pela variável de ambiente:

```text
SPRING_PROFILES_ACTIVE=prd
```

## Variáveis de ambiente

No profile `prd`, informe as seguintes variáveis:

| Variável | Descrição | Exemplo |
|---|---|---|
| `DB_SERVER_URL` | Endereço do servidor MySQL | `mysql-checkpoint01-semestre2` |
| `DB_SERVER_PORT` | Porta do MySQL | `3306` |
| `DB_SCHEMA` | Nome do banco/schema | `api` |
| `DB_USER` | Usuário do banco | `root` |
| `DB_PWD` | Senha do banco | `root_pwd` |
| `SPRING_PROFILES_ACTIVE` | Profile do Spring Boot | `prd` |

## Execução local com o profile default

Com um MySQL disponível em `localhost:3306`, execute:

```bash
mvn spring-boot:run
```

No profile default, os valores padrão são:

```text
DB_SERVER_URL=localhost
DB_SERVER_PORT=3306
DB_SCHEMA=api
DB_USER=root
DB_PWD=root_pwd
```

A aplicação ficará disponível em:

```text
http://localhost:8080
```

## Preparação do banco para o profile prd

Como o profile `prd` não cria banco nem tabelas, eles devem existir antes de iniciar a aplicação.

### 1. Criar uma rede Docker

```bash
docker network create checkpoint01-semestre2-network
```

### 2. Iniciar o MySQL

```bash
docker run -d --name mysql-checkpoint01-semestre2 --network checkpoint01-semestre2-network -e MYSQL_ROOT_PASSWORD=root_pwd -e MYSQL_DATABASE=api mysql:8
```

Aguarde o MySQL terminar a inicialização.

### 3. Criar as tabelas

O arquivo `src/main/resources/schema-prd.sql` contém a criação das tabelas necessárias.

No **CMD/Git Bash**:

```bash
docker exec -i mysql-checkpoint01-semestre2 mysql -uroot -proot_pwd api < src/main/resources/schema-prd.sql
```

No **PowerShell**:

```powershell
Get-Content src/main/resources/schema-prd.sql | docker exec -i mysql-checkpoint01-semestre2 mysql -uroot -proot_pwd api
```

## Download da imagem no Docker Hub

Depois que a imagem estiver publicada, ela poderá ser baixada com:

```bash
docker pull ticorisa/checkpoint01-semestre2:latest
```

## Execução da aplicação pela imagem do Docker Hub

Com o MySQL preparado conforme a seção anterior, execute:

```bash
docker run --name checkpoint01-semestre2-api --network checkpoint01-semestre2-network -p 8080:8080 -e SPRING_PROFILES_ACTIVE=prd -e DB_SERVER_URL=mysql-checkpoint01-semestre2 -e DB_SERVER_PORT=3306 -e DB_SCHEMA=api -e DB_USER=root -e DB_PWD=root_pwd ticorisa/checkpoint01-semestre2:latest
```

Esse comando contempla:

- mapeamento da porta `8080`;
- seleção do profile `prd`;
- variáveis de ambiente necessárias para a conexão com o banco.

## Swagger / OpenAPI

Com a aplicação em execução, acesse:

```text
http://localhost:8080/
```

A página inicial exibe o Swagger UI com os endpoints disponíveis.

## Endpoints

### Brasileirão - `/campeonatobrasileiro`

| Método | Rota | Descrição |
|---|---|---|
| GET | `/campeonatobrasileiro` | Lista todos os campeonatos |
| GET | `/campeonatobrasileiro/{id}` | Busca campeonato por ID |
| POST | `/campeonatobrasileiro` | Cria um campeonato |
| PUT | `/campeonatobrasileiro/{id}` | Atualiza um campeonato |
| DELETE | `/campeonatobrasileiro/{id}` | Remove um campeonato |

Exemplo de body:

```json
{
  "id": 1,
  "campeao": "Flamengo",
  "artilheiro": "Pedro",
  "revelacao": "Endrick",
  "goleada": "Flamengo 6x0 Vasco"
}
```

### Olimpíadas - `/olimpiadas`

| Método | Rota | Descrição |
|---|---|---|
| GET | `/olimpiadas` | Lista todas as edições |
| GET | `/olimpiadas/{id}` | Busca edição por ID |
| POST | `/olimpiadas` | Cria uma edição |
| PUT | `/olimpiadas/{id}` | Atualiza uma edição |
| DELETE | `/olimpiadas/{id}` | Remove uma edição |

Exemplo de body:

```json
{
  "id": 1,
  "ano": "2024",
  "sede": "Paris",
  "maiorMedalhista": "Estados Unidos",
  "maiorVencedor": "Léon Marchand"
}
```

## Build local da imagem Docker

Na raiz do projeto:

```bash
docker build -t checkpoint01-semestre2 .
```

Para testar o profile `prd` com a imagem criada localmente:

```bash
docker run --rm --network checkpoint01-semestre2-network -p 8080:8080 -e SPRING_PROFILES_ACTIVE=prd -e DB_SERVER_URL=mysql-checkpoint01-semestre2 -e DB_SERVER_PORT=3306 -e DB_SCHEMA=api -e DB_USER=root -e DB_PWD=root_pwd checkpoint01-semestre2
```

## Publicação no Docker Hub

O nome do repositório no Docker Hub deve ser exatamente o mesmo nome do repositório no GitHub.

```bash
docker login
docker build -t ticorisa/checkpoint01-semestre2:latest .
docker push ticorisa/checkpoint01-semestre2:latest
```

Depois da publicação, confirme que a imagem pode ser baixada:

```bash
docker pull ticorisa/checkpoint01-semestre2:latest
```
