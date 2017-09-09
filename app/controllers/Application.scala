package controllers


import javax.inject._
import play.api._
import play.api.mvc._
import io.glassdome.widgets.services._
import io.glassdome.widgets.services.errors._

import play.api.libs.json._

import scala.util.{Try,Success,Failure}

import io.glassdome.widgets.models._

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
  

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
class Application @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
  
  private val db = new DataService(MapWidgetData)
  
  
  def index() = Action { implicit request: Request[AnyContent] =>
    Ok("Welcome to Widgets!")
  }
  
  def listWidgets() = Action { implicit request =>
    Ok(Json.toJson(db.list.sortBy(_.id)))
  }
  
  def findWidget(id: Int) = Action { implicit request =>
    db.findById(id).fold {
      HandleExceptions(WidgetNotFoundException(s"Widget with ID '$id' not found."))
    }{ w => Ok(Json.toJson(w)) }
  }
  
  def deleteWidget(id: Int) = Action { implicit request =>
    db.delete(id) match {
      case Failure(e) => HandleExceptions(e)
      case Success(w) =>  Ok(Json.toJson(w))
    }
  }
  
  def createWidget() = Action(parse.json) { implicit request =>
    val maybeWidget = for {
      w1 <- parseJson[Widget](request.body)
      w2 <- db.create(w1)
    } yield w2
    
    maybeWidget match {
      case Failure(e) => HandleExceptions(e)
      case Success(w) => Created(Json.toJson(w))
    }
  }

  def updateWidget(id: Int) = Action(parse.json) { implicit request =>

    val maybeWidget = for {
      w1 <- parseJson[Widget](request.body)
      w2 <- db.update(w1)
    } yield w2
    
    maybeWidget match {
      case Failure(e) => HandleExceptions(e)
      case Success(w) => Accepted(Json.toJson(w))
    }
  }
  
  def options(path: String) = Action {Ok("")}
  
  
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