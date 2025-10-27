Spring AI MCP
=============

An opinionated Spring Boot 3.4 application that exposes an AI-enabled MCP (Mission Critical Platform) service. It combines Spring AI’s MCP client/server starters, a PostgreSQL-backed product catalog, and Springdoc (Swagger UI) so you can test conversational endpoints locally or inside Docker with minimal setup.

## Features
- **AI-assisted endpoints** – `/api/mcp/query` streams DB-backed product context into Spring AI ChatClient prompts.
- **Dual MCP roles** – Runs both the MCP server (WebMVC) and MCP client so you can experiment with provider/consumer flows.
- **OpenAPI documentation** – Springdoc starter + `OpenApiConfig` give you a branded Swagger UI at `/swagger-custom.html`.
- **Production-ready scaffolding** – Docker Compose for Postgres + pgAdmin, health checks, actuator, validation, and test profile defaults.

## Architecture At A Glance
| Layer | Technology | Notes |
| --- | --- | --- |
| API | Spring Web MVC | Controller `AiMcpController` exposes AI endpoints. |
| Service | Spring AI ChatClient | `AiMcpService` orchestrates prompt creation and error handling. |
| Persistence | Spring Data JPA + PostgreSQL | `ProductRepository` reads `Product` entities; H2 is used for tests. |
| Docs & Ops | Springdoc, Actuator | `/swagger-custom.html` and `/actuator` for insight. |

## Prerequisites
1. **Java 17** (compatible with the enforced version in `pom.xml`).  
2. **Maven Wrapper** – already included; run with `./mvnw …`.  
3. **Docker & Docker Compose** (optional) for the bundled Postgres/pgAdmin stack.  
4. **Spring AI model key** – e.g., set `SPRING_AI_OPENAI_API_KEY` to your OpenAI key or another supported provider’s credentials.

## Configuration
- Primary runtime settings live in `src/main/resources/application.properties`.
- Tests inherit overrides from `src/test/resources/application-test.properties` (H2 in PostgreSQL compatibility mode).
- Important properties:
  - `spring.datasource.*` – default is PostgreSQL on `localhost:5432/mcp_data`.
  - `spring.ai.openai.api-key` – read from env var with a fallback placeholder.
  - `springdoc.swagger-ui.path=/swagger-custom.html`.
  - `spring.jpa.open-in-view=false` for better transactional discipline.

## Running Locally (PostgreSQL already running)
```bash
export SPRING_AI_OPENAI_API_KEY=sk-your-key   # or put in your shell profile
./mvnw spring-boot:run
```
- API will listen on `http://localhost:8080`.
- Swagger UI: `http://localhost:8080/swagger-custom.html`.
- Health/info endpoints: `http://localhost:8080/actuator`.

## Running Everything With Docker Compose
```bash
# Builds the Spring Boot image and provisions PostgreSQL + pgAdmin
docker compose up --build
```
- Postgres is exposed on `localhost:5432`.  
- pgAdmin is available at `http://localhost:5050` (login creds set in `docker-compose.yml`).  
- The Spring container receives DB creds and `SPRING_AI_OPENAI_API_KEY` from the compose file—remember to change the placeholder secret.

## Interacting With The API
### POST `/api/mcp/query`
Body:
```json
{ "query": "Tell me about gaming laptops" }
```
Response:
```json
{ "response": "AI answer grounded in product catalog…" }
```

### GET `/api/mcp/simple-query?text=Hello`
Echoes a simple conversation that does not automatically inject DB context.

Inspect and test endpoints quickly via Swagger UI or use your favorite HTTP client (curl, Postman, Bruno, etc.).

## Tests & Quality Gates
- Run the full lifecycle (compiles, tests, packaging):
  ```bash
  ./mvnw clean verify
  ```
- Test profile automatically switches to H2, applies `create-drop` schema, and silences Spring AI logs for faster feedback.
- `maven-enforcer-plugin` guarantees Java 17+, while `spring-boot-starter-validation` keeps Bean Validation available at runtime.

## Extending The Project
- **New tools / contexts** – add repositories/services and pass extra context into `AiMcpService`.
- **Different LLM providers** – swap `spring-ai-starter-model-openai` for Azure, Bedrock, or local models; configure via Spring AI properties.
- **Domain changes** – Update the `Product` entity and database migration scripts inside `initdb/` or introduce Liquibase/Flyway.

## Troubleshooting
- **AI errors**: Missing or invalid API key typically surfaces as HTTP 401/429 messages in logs; verify `SPRING_AI_OPENAI_API_KEY`.
-.DATABASE connection issues**: Ensure the Postgres container is healthy (`docker compose ps`) or adjust `spring.datasource.url`.
- **Swagger not loading**: Confirm the app started on port 8080 and that `springdoc.swagger-ui.path` matches `/swagger-custom.html`.

Happy building! Iterate on prompts, expand the MCP toolset, and tailor the project to your AI-assisted workflows.
