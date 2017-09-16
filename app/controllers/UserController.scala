package controllers


import javax.inject._
import play.api._
import play.api.mvc._
import io.glassdome.widgets.services._
import io.glassdome.widgets.services.util.PgConnect
import io.glassdome.widgets.services.errors._

import play.api.libs.json._

import scala.util.{Try,Success,Failure}

import io.glassdome.widgets.models._

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

import org.slf4j.LoggerFactory


/*
 * TODO:
 * - Implement `safe` JSON parsing in POST and PUT methods
 * -
 */

/**
  * This controller creates an `Action` to handle HTTP request to the
  * application's home page.
  */
@Singleton
class UserController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  PgConnect.openConnection()

  private[this] val log = LoggerFactory.getLogger(this.getClass)
  private val db = new DataService2(PostgresUserData)




  def listUsers() = Action { implicit request =>
    println(Json.toJson("User :" + db.list.sortBy(_.id)))
    Ok(Json.toJson(db.list.sortBy(_.id)))
  }

  def findUser(id: Int) = Action { implicit request =>
    db.findById(id).fold {
      HandleExceptions(WidgetNotFoundException(s"User with ID '$id' not found."))
    }{ u => Ok(Json.toJson(u)) }
  }

  def deleteUser(id: Int) = Action { implicit request =>
    db.delete(id) match {
      case Failure(e) => HandleExceptions(e)
      case Success(u) =>  Ok(Json.toJson(u))
    }
  }

  def createUser() = Action(parse.json) { implicit request =>
    log.debug("createAppUser(_): " + Json.prettyPrint(request.body))

    val maybeUser = for {
      u1   <- parseJson[AppUser](request.body)
      u2   <- db.create(u1)
    } yield u2

    maybeUser match {
      case Failure(e) => HandleExceptions(e)
      case Success(u) => Created(Json.toJson(u))
    }
  }

  def updateUser(id: Int) = Action(parse.json) { implicit request =>

    val maybeUser = for {
      u1 <- parseJson[AppUser](request.body)
      u2 <- db.update(u1)
    } yield u2

    maybeUser match {
      case Failure(e) => HandleExceptions(e)
      case Success(u) => Accepted(Json.toJson(u))
    }
  }

  def options(path: String) = Action {Ok("")}


  private[controllers] def normalizeUserJson(json: JsValue) = Try {
    val out = json.as[JsObject] ++ Json.obj("id" -> (json \ "id").as[String].toInt)
    println(Json.prettyPrint(out))
    out
  }

  private def HandleExceptions(ex: Throwable): Result = ex match {
    case e: UnprocessableException  => UnprocessableEntity(e.toJson)
    case e: WidgetNotFoundException => NotFound(e.toJson)
    case e: ConflictException       => Conflict(e.toJson)
    case _ => InternalServerError(ex.getMessage)
  }

  private def parseJson[A](json: JsValue)(implicit reads: Reads[A]): Try[A] = Try {
    json.validate[A].map {
      case a => a }.recoverTotal { e =>
      throw UnprocessableException(JsError.toJson(e).toString)
    }
  }

}
