package io.glassdome.widgets.services

import scala.util.{Try, Success, Failure}
import io.glassdome.widgets.models._
import scalikejdbc._
import org.slf4j.LoggerFactory



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

object PostgresWidgetData extends WidgetData {
  
  private implicit val session: DBSession = AutoSession
  

  def findById(id: Int): Option[Widget] = {
    ???
  }
  
  def list(): Seq[Widget] = mapInstance {
    sql"""SELECT * FROM widget;"""
  }.list.apply
  
  
  def create(w: Widget): Try[Widget] = {
    ???
  }
  
  def update(w: Widget): Try[Widget] = {
    ???
  }
  
  def delete(id: Int): Try[Widget] = {
    ???
  }
  
  //(implicit session: DBSession = AutoSession)
  private[services] def mapInstance(sql: SQL[Widget, NoExtractor]) = {
    sql map { rs =>
      Widget(
          rs.int("id"),
          rs.string("name"),
          rs.stringOpt("description"))
    }
  }
  
  
/*
  private[data] def mapInstance(sql: SQL[GestaltResourceInstance, NoExtractor]) = {
    sql map { rs => 
      GestaltResourceInstance(
        rs.any("id").asInstanceOf[UUID],
        rs.any("resource_type_id").asInstanceOf[UUID],
        rs.any("resource_state_id").asInstanceOf[UUID],
        rs.any("org_id").asInstanceOf[UUID],
        ResourceOwnerLink.fromJavaMap(rs.any("owner")),
        rs.string("name"),
        rs.stringOpt("description"),
        Some( asHstore(rs.any("created")) ),
        Some( asHstore(rs.any("modified")) ),
        asHstoreOpt(rs.anyOpt("properties")),
        asHstoreOpt(rs.anyOpt("variables")),
        asListOpt(rs.arrayOpt("tags")),
        asHstoreOpt(rs.anyOpt("auth")))   
    }
  }    
 */
  
}

