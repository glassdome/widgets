# widgets
---
A test application to play with various aspects of Scala and the Play Framework.

### Postgres Configuration

This app uses FlywayDB to manage database migrations. To configure, set the following environment variables as appropriate for your installation:

```

export FLYWAY_URL=jdbc:postgresql://{host}:{port}/{database}
export FLYWAY_USER={username}
export FLYWAY_PASSWORD={password}

```

