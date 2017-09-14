package io.glassdome.widgets.services

import scala.util.{Try, Success, Failure}
import io.glassdome.widgets.models._
import io.glassdome.widgets.services.errors._
import scalikejdbc._
import play.api.Logger
import io.glassdome.widgets.services.util.PgConnect


object PostgresUserData extends Database[appUser] {

  private[this] val log = Logger(this.getClass)
  private implicit val session: DBSession = AutoSession

  def findById(id: Int): Option[appUser] = {
    mapInstance {
      sql"""SELECT * FROM app_user WHERE id = ${id}"""
    }.single.apply
  }

  def list(): Seq[appUser] = mapInstance {
    sql"""SELECT * FROM app_user;"""
  }.list.apply


  def create(u: appUser): Try[appUser] = {
    // Validate: Conflict if widget.id already exists.
    if (!findById(u.id).isDefined){
      Try {
        sql"""
      INSERT INTO app_user VALUES(
        ${u.id},
        ${u.userName},
        ${u.firstName},
        ${u.lastName}
        ${u.password}
        ${u.email}""".update.apply
      } map { _ =>
        findById(u.id) getOrElse {
          throw new RuntimeException("Unknown error creating widget")
        }
      }
    } else throw ConflictException("User IDs must be unique")
  }//end create

  def update(u: appUser): Try[appUser] = {
    // Validate: Conflict if widget does not exist.
    if (findById(u.id).isDefined)
    {
      Try {
        sql"""
        UPDATE app_user SET
          id = ${u.id},
          username = ${u.userName},
          firstname = ${u.firstName},
          lastname = ${u.lastName}
          password = ${u.password}
          email = ${u.email}
        WHERE id = ${u.id}
          """.update.apply
      } map { _ =>
        findById(u.id) getOrElse {
          throw new RuntimeException("Unknown error updating appUser")
        }
      }
    }
    else throw ConflictException(s"Widget with ID '${u.id}' does not exist.")

  }//end update


  def delete(id: Int): Try[appUser] = {
    findById(id).fold {
      throw WidgetNotFoundException(s"User with ID '$id' not found")
    }{ user =>
      Try {
        sql"""DELETE FROM app_user WHERE id = ${id}""".update.apply
      } map { _ => user }
    }
  }

  private[services] def mapInstance(sql: SQL[appUser, NoExtractor]) = {
    sql map { rs =>
      appUser(
        rs.int("id"),
        rs.string("userName"),
        rs.string("firstName"),
        rs.string("lastName"),
        rs.string("password"),
        rs.string("email"))
    }
  }

}


