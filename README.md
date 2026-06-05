# Sistema de Feira — CRUD de Barracas e Tipos

Aplicação web full-stack para gerenciamento de barracas de feira, migrada de uma versão console Java para uma arquitetura cliente-servidor com **Spring Boot** no backend e **Angular** no frontend.

> Trabalho final da disciplina de **Arquitetura de Sistemas**.

---

## Sobre o projeto

O sistema permite o cadastro, listagem e remoção de:

- **Tipos de Barraca** (ex.: Alimentação, Artesanato)
- **Barracas** (ex.: "Açaí da Tia"), vinculadas a um tipo

### Regras de negócio aplicadas

- O nome do tipo de barraca não pode ser duplicado.
- O nome da barraca deve ter pelo menos 3 caracteres.
- Não é permitido remover um Tipo de Barraca que esteja em uso por alguma Barraca.
- O backend responde com **HTTP 422 (Unprocessable Entity)** + mensagem clara quando uma regra é violada.
- O backend responde com **HTTP 400 (Bad Request)** + detalhes por campo quando há erro de validação.

---

## Stack utilizada

### Backend (`barraca-api/`)
- Java **17**
- Spring Boot **3.x**
- Spring Web, Spring Data JPA, Bean Validation
- Banco H2 em memória
- Maven

### Frontend (`frontend/`)
- Angular **21**
- TypeScript
- RxJS (Observables)
- HttpClient
- NgModule (modelo clássico)

---

## Como executar

### Pré-requisitos
- Java 17+
- Maven 3.9+
- Node.js 18+
- Angular CLI 21+

### 1. Backend

```bash
cd barraca-api
./mvnw spring-boot:run
```

Backend disponível em `http://localhost:8080`.

Console do H2: `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:barracadb`, usuário `sa`, senha em branco).

### 2. Frontend

Em **outro terminal**:

```bash
cd frontend
npm install
ng serve -o
```

Frontend disponível em `http://localhost:4200` (o navegador abre automaticamente).

---

## Endpoints da API REST

| Método | URL | Descrição |
|--------|-----|-----------|
| GET | `/api/tipos-barraca` | Lista todos os tipos |
| GET | `/api/tipos-barraca/{id}` | Busca tipo por ID |
| POST | `/api/tipos-barraca` | Cadastra novo tipo |
| DELETE | `/api/tipos-barraca/{id}` | Remove um tipo (se não estiver em uso) |
| GET | `/api/barracas` | Lista todas as barracas |
| GET | `/api/barracas/{id}` | Busca barraca por ID |
| POST | `/api/barracas` | Cadastra nova barraca |
| DELETE | `/api/barracas/{id}` | Remove uma barraca |

### Exemplo de payload — criar TipoBarraca

```json
POST /api/tipos-barraca
{
  "nome": "Alimentação",
  "descricao": "Barracas de comida e bebida"
}
```

### Exemplo de payload — criar Barraca

```json
POST /api/barracas
{
  "nome": "Açaí da Tia",
  "descricao": "Açaí natural de tigela",
  "ativo": true,
  "tipoBarracaId": 1
}
```

---

## Estrutura do projeto

```
crud-grasp-barraca-tipo/
├── barraca-api/                      # Backend Spring Boot
│   └── src/main/java/feira/graspcrud/barraca_api/
│       ├── controller/               # Endpoints REST
│       ├── service/                  # Regras de negócio
│       ├── repository/               # Acesso a dados (JpaRepository)
│       ├── domain/                   # Entidades JPA
│       ├── dto/                      # Request/Response
│       ├── exception/                # GlobalExceptionHandler
│       └── config/                   # Configuração CORS
│
├── frontend/                         # Frontend Angular
│   └── src/app/
│       ├── components/
│       │   ├── barracas/             # Lista + Form de barracas
│       │   └── tipos-barraca/        # Lista + Form de tipos
│       ├── models/                   # Interfaces TypeScript
│       ├── services/                 # Serviços HTTP
│       ├── app.ts                    # Componente raiz
│       └── app-routing-module.ts     # Rotas
│
├── crud-feira-barraca/               # Versão console (referência)
└── scrum/                            # Scripts e configs do Scrum/Kanban
```

---

## Padrões de projeto aplicados

| Padrão | Onde aparece |
|--------|--------------|
| **MVC** | Separação Controller / Service / View (Angular) |
| **Repository** | `BarracaRepository`, `TipoBarracaRepository` |
| **Service Layer** | `BarracaService`, `TipoBarracaService` (regra de negócio centralizada) |
| **DTO** | `*Request` e `*Response` desacoplam entidades da API |
| **Dependency Injection** | Spring no backend, Angular no frontend |
| **Observer (RxJS)** | Components se inscrevem em Observables dos services |
| **Static Factory Method** | `BarracaResponse.from(barraca)` para conversões |

Mais detalhes no [RELATORIO.md](./RELATORIO.md).

---

## Metodologia

O projeto foi gerenciado com **Scrum**, usando GitHub Projects como Kanban. Foram realizadas 4 sprints:

1. **Sprint 1** — Setup do ambiente e estrutura inicial do backend
2. **Sprint 2** — Implementação do CRUD REST completo
3. **Sprint 3** — Validações, tratamento global de erros e setup do Angular
4. **Sprint 4** — Telas Angular, integração final e documentação

---

## Autoras

- Alanis Aguiar — 2315059 [@alanisaguiar](https://github.com/alanisaguiar)
