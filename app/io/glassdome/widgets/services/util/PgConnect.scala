package io.glassdome.widgets.services.util

import scalikejdbc._


/**
 * Configure a ConnectionPool for Postgres
 * 
 * Gathers the necessary connection-string values from the same Flyway
 * environment variables used for evolving the database.s
 */
object PgConnect {
  def openConnection(timeoutMs: Long = 5000L) = {
    
    val (url, user, password) = (for {
      url <- sys.env.get("FLYWAY_URL") orElse {
        throw new RuntimeException("Missing env var FLYWAY_URL")
      }
      usr <- sys.env.get("FLYWAY_USER") orElse {
        throw new RuntimeException("Missing env var FLYWAY_URL")
      }
      pwd <- sys.env.get("FLYWAY_PASSWORD") orElse {
        throw new RuntimeException("Missing env var FLYWAY_URL")
      }
    } yield (url, usr, pwd)).get
    
    val settings = ConnectionPoolSettings(connectionTimeoutMillis = timeoutMs)
    val driver = "org.postgresql.Driver"
    
    Class.forName(driver)
    ConnectionPool.singleton(url, user, password, settings)    
  }  
}