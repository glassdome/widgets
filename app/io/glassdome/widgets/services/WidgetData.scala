package io.glassdome.widgets.services

import java.io.{BufferedWriter, File, FileInputStream, FileWriter}

import scala.util.{Failure, Success, Try}
import io.glassdome.widgets.models.Widget

import scala.collection.mutable.{HashMap, Map}
import io.glassdome.widgets.services.errors._
import play.api.libs.json._

trait WidgetData {
  
  def findById(id: Int): Option[Widget]
  
  def list(): Seq[Widget]
  
  def create(w: Widget): Try[Widget]
  
  def update(w: Widget): Try[Widget]
  
  def delete(id: Int): Try[Widget]

}



object MapWidgetData extends WidgetData {

  private val widgets = new HashMap[Int, Widget]()
  private val filePath = getFilePath("WIDGET_DATA")

  def findById(id: Int): Option[Widget] = {
    if (widgets.contains(id)) Some(widgets(id))
    else None
  }

  def list(): Seq[Widget] = {
    (widgets.map { case (k, v) => v }).toSeq
  }

  def create(w: Widget): Try[Widget] = Try {
    if (widgets.contains(w.id))
      throw ConflictException("Widget IDs must be unique")
    else concat(w)
  }

  def update(w: Widget): Try[Widget] = Try {
    if (widgets.get(w.id).isEmpty)
      throw ConflictException(s"Widget with ID '${w.id}' does not exist.")
    else concat(w)
  }

  def delete(id: Int): Try[Widget] = Try {
    if (widgets.get(id).isEmpty)
      throw ConflictException(s"Widget with ID '$id' does not exist.")
    else {
      val oldwidget = widgets(id)
      widgets -= id
      oldwidget
    }
  }

  private def concat(w: Widget) = {
    widgets += (w.id -> w)
    w
  }

  private def getFilePath(envVar: String): String ={
    val path = scala.util.Properties.envOrNone(envVar)
    path.getOrElse("")
  }

  def loadData(): Map[Int, Widget] = {
    val ws = Seq(
      Widget(1, "alpha", Some("The first widget!")),
      Widget(2, "beta", Some("The second widget!")),
      Widget(3, "charlie"),
      Widget(4, "delta", Some("The fourth widget!")),
      Widget(5, "echo", Some("Just a widget")))

    ws foreach { w => create(w) }
    widgets
  }


  def load():  Map[Int, Widget] = {
    val file = new File(filePath)
    val stream = new FileInputStream(file)
    val json = try {
      Json.parse(stream)
    } finally {
      stream.close()
    }

    val js = json.toString()
    val jsMap = js.drop(1).dropRight(1)split("}")
    val jsMap2 = jsMap map (x => x.dropWhile(p => (p == '{' || p == ','))) map ( "{" + _ + "}" )

    for( i <- jsMap2 ) yield  {
      val jv: JsValue = Json.parse(i)
      val widgetFromJson: JsResult[Widget] = Json.fromJson[Widget](jv)
      widgetFromJson match {
        case JsSuccess(w: Widget, path: JsPath) => create(widgetFromJson.get)
        case e: JsError => println("Errors: " + JsError.toJson(e).toString())
      }
    }
    widgets
  }

  def save(): Unit = {
    val file = new File(filePath)
    val bw = new BufferedWriter(new FileWriter(file))
    val jsonString = Json.toJson((widgets.map { case (k, v) => v }).
      toSeq.sortBy(_.id)).toString()

    bw.write(jsonString)
    bw.close()
  }
}



//object MongoWidgetData extends WidgetData {
//  
//}
//
//object PostgresWidgetData extends WidgetData {
//  
//}

