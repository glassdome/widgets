package io.glassdome.widgets.services

import scala.util.{Try, Success, Failure}
import io.glassdome.widgets.models._
import io.glassdome.widgets.services.errors._
import scalikejdbc._
import play.api.Logger
import io.glassdome.widgets.services.util.PgConnect


object PostgresWidgetData extends WidgetData {

  private[this] val log = Logger(this.getClass)
  private implicit val session: DBSession = AutoSession

  def findById(id: Int): Option[Widget] = {
    mapInstance {
      sql"""SELECT * FROM widget WHERE id = ${id}"""
    }.single.apply
  }

  def list(): Seq[Widget] = mapInstance {
    sql"""SELECT * FROM widget;"""
  }.list.apply


  def listWidgetsByUser(userId: Int) = mapInstance{
    sql"""SELECT * FROM widget WHERE owner = ${userId} """
  }.list.apply

  
  
  def create(w: Widget): Try[Widget] = {
    // Validate: Conflict if widget.id already exists.
     if (!findById(w.id).isDefined){
       Try {
         sql"""
      INSERT INTO widget VALUES(
        ${w.id},
        ${w.name},
        ${w.owner},
        ${w.kind},
        ${w.image},
        ${w.description})
      """.update.apply
       } map { _ =>
         findById(w.id) getOrElse {
           throw new RuntimeException("Unknown error creating widget")
         }
       }
     } else throw ConflictException("Widget IDs must be unique")
  }//end create
  
  def update(w: Widget): Try[Widget] = {
    // Validate: Conflict if widget does not exist.
    if (findById(w.id).isDefined)
    {
      Try {
        sql"""
        UPDATE widget SET
          id = ${w.id},
          name = ${w.name},
          owner = ${w.owner},
          kind = ${w.kind},
          image = ${w.image},
          description = ${w.description}
        WHERE id = ${w.id}
      """.update.apply
      } map { _ =>
        findById(w.id) getOrElse {
          throw new RuntimeException("Unknown error updating widget")
        }
      }
    }
    else throw ConflictException(s"Widget with ID '${w.id}' does not exist.")

  }//end update

 
  def delete(id: Int): Try[Widget] = {
    findById(id).fold {
      throw WidgetNotFoundException(s"Widget with ID '$id' not found")
    }{ widget =>
      Try {
        sql"""DELETE FROM widget WHERE id = ${id}""".update.apply
      } map { _ => widget }
    }
  }

  private[services] def mapInstance(sql: SQL[Widget, NoExtractor]) = {
    sql map { rs =>
      Widget(
          rs.int("id"),
          rs.string("name"),
          rs.int("owner"),
          rs.stringOpt("kind"),
          rs.stringOpt("image"),
          rs.stringOpt("description"))
    }
  }

}

