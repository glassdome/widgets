package io.glassdome.widgets.services


import scala.util.{Try,Success,Failure}
import io.glassdome.widgets.models.Widget
import scala.collection.mutable.{Map, HashMap}
import io.glassdome.widgets.services.errors._


object MapWidgetData extends WidgetData {
  
  private val widgets = new HashMap[Int, Widget]()
  
  loadData()
  
  def findById(id: Int): Option[Widget] = {
    if (widgets.contains(id)) Some(widgets(id))
    else None
  }
  
  def list(): Seq[Widget] = {
    (widgets.map { case (k,v) => v }).toSeq
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
  
  def loadData(): Map[Int, Widget] = {
    val ws = Seq(
        Widget(1, "alpha", 1, Some("The first widget!")),
        Widget(2, "beta", 1, Some("The second widget!")),
        Widget(3, "charlie", 1),
        Widget(4, "delta", 1, Some("The fourth widget!")),
        Widget(5, "echo", 1, Some("Just a widget")))
   
    ws foreach { w => create(w) }
    widgets
  }
  
}