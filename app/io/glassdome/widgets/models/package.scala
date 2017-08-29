package io.glassdome.widgets

import play.api.libs.json._

package object models {
  
  implicit lazy val widgetFormat = Json.format[Widget]
  
}