package io.glassdome.widgets.services

import play.api.libs.json._

package object errors {
  
  abstract class WidgetException(message: String) extends RuntimeException(message) {
    def toJson(): JsValue = {
      Json.obj( "status" -> "error", "message" -> message)
    }
  }
  
  case class UnprocessableException(message: String) extends WidgetException(message)
  case class ConflictException(message: String) extends WidgetException(message)
  case class WidgetNotFoundException(message: String) extends WidgetException(message)
  
}