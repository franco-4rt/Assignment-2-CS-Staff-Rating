# CS Staff Rating

A Spring Boot web app for rating CS staff (TAs, professors, instructors, and staff) on clarity, niceness, and knowledgeability. Users can list, view, create, edit, and delete ratings. Each staff member's role shows a role-specific title through the `StaffMemberProfile` interface.

**Live app:** <!-- paste your Render URL here -->

## Tech stack

- Java 21, Spring Boot 4 (Spring MVC, Spring Data JPA, Bean Validation)
- Thymeleaf templates
- PostgreSQL (local Homebrew database for development, Render Postgres in production)
- Docker (used for deployment on Render)

## How to run locally

### Prerequisites

- **Java 21 or newer**
- **PostgreSQL** running locally (installed with Homebrew in this guide)

### 1. Start PostgreSQL and create the database

```bash
brew services start postgresql@18
psql postgres
```

Inside `psql`:

```sql
CREATE DATABASE staffratings;
\q
```

### 2. Database connection

The app reads its database connection from environment variables. When they aren't set, it falls back to a local database:

| Variable | Default (local) |
|---|---|
| `DB_URL` | `jdbc:postgresql://localhost:5432/staffratings` |
| `DB_USERNAME` | `jorgefrancotarasco` |
| `DB_PASSWORD` | *(empty)* |

If your local PostgreSQL user or database differs, set the variables before running, for example:

```bash
export DB_USERNAME=your_mac_username
```

Hibernate creates the `staff_rating` table automatically on first start (`spring.jpa.hibernate.ddl-auto=update`).

### 3. Run the app

From the project root:

```bash
./mvnw spring-boot:run
```

Then open [http://localhost:8080](http://localhost:8080). The root URL redirects to the ratings list at `/index/view`.

### 4. Run the tests

```bash
./mvnw test
```

This runs the controller tests (MockMvc), the validation tests, and the persistence tests. The persistence tests use the same database connection as the app, so PostgreSQL must be running. Each test's changes are rolled back, so no test data is left behind.

## How to deploy (Render)

The app is deployed on Render as a Docker web service, connected to a Render Postgres database.

### 1. Create the database

1. In the Render dashboard, click **New → Postgres**.
2. Once it's created, open its page and find the **Connect** menu. You'll need the **internal hostname**, **database name**, **username**, and **password**.

### 2. Create the web service

1. Click **New → Web Service** and connect this GitHub repository.
2. Choose the **`main`** branch and set the language to **Docker**. Render builds the app using the `Dockerfile` in the project root, so no build or start command is needed.

### 3. Set the environment variables

In the web service, open **Environment** and add:

| Key | Value |
|---|---|
| `DB_URL` | `jdbc:postgresql://<internal-hostname>/<database-name>` |
| `DB_USERNAME` | the database username |
| `DB_PASSWORD` | the database password |

Notes:

- `DB_URL` must use the **JDBC format** (`jdbc:postgresql://...`), not the `postgres://` URL Render displays.
- Use the **internal** hostname, since the web service and database both run on Render.
- Render sets the `PORT` variable automatically. The app reads it through `server.port=${PORT:8080}`.

### 4. Deploy

Saving the variables triggers a deploy. After that, every push to `main` redeploys the app automatically. You can also deploy manually with **Manual Deploy → Deploy latest commit**.

### Notes

- No credentials are committed to the repository. They exist only as environment variables on Render.
- Render's free Postgres databases expire 30 days after creation.
