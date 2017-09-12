package io.glassdome.widgets.services


import scala.util.Try
import io.glassdome.widgets.models.Widget


trait WidgetData {
  
  def findById(id: Int): Option[Widget]
  
  def list(): Seq[Widget]
  
  def create(w: Widget): Try[Widget]
  
  def update(w: Widget): Try[Widget]
  
  def delete(id: Int): Try[Widget]

}

trait Database[A] {
  
  def findById(id: Int): Option[A]
  
  def list(): Seq[A]
  
  def create(w: A): Try[A]
  
  def update(w: A): Try[A]
  
  def delete(id: Int): Try[A]
}









