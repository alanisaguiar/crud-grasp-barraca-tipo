# Relatório Técnico — Sistema de Feira (CRUD de Barracas)

**Disciplina:** Arquitetura de Sistemas
**Trabalho:** Migração de aplicação console Java para arquitetura web cliente-servidor

---

## 1. Visão Geral

O trabalho consistiu em migrar um CRUD de **Barracas de feira** originalmente escrito em modo texto (console Java com persistência em JSON) para uma arquitetura web moderna composta por:

- **Backend** em Spring Boot 3 expondo uma API REST sobre banco H2 em memória.
- **Frontend** em Angular 21 consumindo essa API e oferecendo telas de listagem e cadastro.

O domínio do sistema gira em torno de duas entidades — `TipoBarraca` (categoria) e `Barraca` (instância vinculada a um tipo) — com regras de negócio como unicidade de nome e bloqueio de remoção de tipos em uso.

---

## 2. Padrões de Projeto Aplicados

### 2.1 MVC (Model-View-Controller)
A separação de responsabilidades segue o modelo MVC:
- **Model:** entidades JPA (`Barraca`, `TipoBarraca`) e DTOs.
- **View:** templates Angular (`lista-barracas.html`, `form-barraca.html` etc.) renderizam o estado dos componentes.
- **Controller:** classes `@RestController` recebem as requisições HTTP e delegam para os services.

### 2.2 Repository
O acesso a dados é abstraído pelo padrão Repository. As interfaces `BarracaRepository` e `TipoBarracaRepository` estendem `JpaRepository<T, ID>`, e o Spring Data gera as implementações em tempo de execução, incluindo *query methods* baseados em convenção (ex.: `findByNome`, `existsByTipoBarracaId`). Isso desacopla a lógica de domínio dos detalhes de persistência — trocar H2 por PostgreSQL não exigiria mexer em service nem controller.

### 2.3 Service Layer
Toda regra de negócio reside em `BarracaService` e `TipoBarracaService`. Exemplos:
- Validação de nome duplicado (`findByNome().ifPresent(...)`).
- Bloqueio de remoção de tipo em uso (`existsByTipoBarracaId`).
- Validação implícita do tipo informado antes de cadastrar uma barraca.

Os controllers ficam intencionalmente "magros", apenas orquestrando entrada e saída. Isso reflete diretamente o critério da disciplina de manter o domínio fora da camada de apresentação.

### 2.4 DTO (Data Transfer Object)
Para evitar acoplamento entre a API e o modelo persistido, criamos DTOs distintos para entrada (`*Request`) e saída (`*Response`). Em particular, `BarracaResponse` incorpora `TipoBarracaResponse` aninhado, eliminando a necessidade de chamadas adicionais para resolver o relacionamento no frontend.

Os DTOs também são onde a **Bean Validation** vive (`@NotBlank`, `@Size`, `@NotNull`), com `@Valid` nos controllers ativando a validação automática.

### 2.5 Dependency Injection (DI)
O padrão DI aparece nas duas pontas:
- **Backend:** o Spring injeta repositories nos services e services nos controllers via construtor.
- **Frontend:** o Angular injeta o `HttpClient` nos services e os services nos componentes via construtor.

A vantagem é que cada classe declara explicitamente do que depende, facilitando teste e substituição.

### 2.6 Observer (RxJS)
No frontend, os services Angular retornam `Observable<T>` e os componentes se **inscrevem** (`subscribe`) para reagir à chegada das respostas HTTP. Esse é o padrão Observer materializado na biblioteca RxJS — exatamente o padrão pedido no enunciado.

### 2.7 Static Factory Method
Os DTOs de resposta expõem métodos estáticos `from(entidade)` (`BarracaResponse.from(barraca)`) que centralizam a conversão de entidade para DTO. Evita repetir a construção do response em cada método de service.

---

## 3. Tratamento de Erros

Foi implementado um `@RestControllerAdvice` (`GlobalExceptionHandler`) que padroniza as respostas de erro:
- `RegraNegocioException` → HTTP **422** com mensagem clara.
- Falhas de Bean Validation → HTTP **400** com a lista de campos inválidos.
- Demais exceções → HTTP **500** sem expor stack trace.

O frontend lê esses status no `error` do Observable e exibe a mensagem ao usuário, em alerta geral (422) ou destacando os campos com problema (400).

---

## 4. Dificuldades Encontradas

1. **Configuração do Kanban via script PowerShell.** O script do professor tinha um bug em que tentava criar issues passando o número da milestone para `gh issue create` (que espera o título). Adicionalmente, o tratamento de duplicatas via `2>&1` interrompia a execução em alguns cenários. Foi necessário ajustar o script para usar `try/catch` e título de milestone.

2. **CORS entre Angular (4200) e Spring Boot (8080).** Por padrão o navegador bloqueia chamadas cross-origin. Resolvemos com um `@Configuration` (`WebConfig`) liberando origens específicas para os endpoints `/api/**`.

3. **Migração de Java 25 para Java 17.** O ambiente da máquina tinha JDK 25, mas o enunciado pede 17. Foi preciso instalar o JDK 17 lado a lado e ajustar `JAVA_HOME` + `Path` para que o Maven usasse a versão correta.

4. **Configuração do Zone.js no Angular 21.** A versão mais nova do Angular CLI gera o `main.ts` configurado para "zoneless change detection", incompatível com NgModule clássico. Foi necessário adicionar `import 'zone.js';` no `main.ts` e limpar o cache `.angular`.

5. **Validação no novo lugar.** No projeto console as regras de validação ficavam em `validar()` dentro da própria entidade. Na arquitetura web, a validação de entrada foi movida para os DTOs (via Bean Validation), enquanto a entidade ficou só com dados. Isso exigiu repensar onde cada regra deveria viver.

---

## 5. Melhorias Futuras

- **Persistência durável.** Trocar o H2 em memória por um banco com armazenamento em disco (PostgreSQL, MySQL ou mesmo H2 file mode), para que os dados sobrevivam a reinícios.
- **Edição de registros.** Atualmente o sistema só permite criar e remover. Adicionar endpoints e telas de edição (`PUT /api/barracas/{id}`).
- **Autenticação e autorização.** Proteger as rotas com Spring Security + JWT, e exibir/ocultar funcionalidades no frontend conforme o perfil do usuário.
- **Paginação e filtros.** Quando o volume de dados crescer, listar tudo de uma vez não escala. O `JpaRepository` já oferece `Pageable` nativo.
- **Testes automatizados.** Adicionar testes unitários nos services (JUnit + Mockito) e de integração nos controllers (`@SpringBootTest`).
- **Pipeline CI/CD.** Configurar GitHub Actions para rodar testes e build a cada push, e eventualmente deploy automático.
- **Frontend mais elaborado.** Adotar uma biblioteca de componentes (Angular Material ou PrimeNG) para padronizar visual e ganhar componentes prontos (modais, snackbars, datatables com sort/filter).

---

## 6. Conclusão

A migração demonstrou na prática como princípios arquiteturais — separação em camadas, padronização de DTOs, tratamento centralizado de erros, injeção de dependências — não são apenas teoria, mas decisões que reduzem acoplamento e simplificam a evolução do sistema. A mesma lógica de domínio do console foi preservada, mas agora acessível por uma API HTTP e por uma interface web responsiva, mantendo o backend reutilizável caso surja um cliente mobile ou desktop no futuro.