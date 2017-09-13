package io.glassdome.widgets.services

import scala.util.Try
import io.glassdome.widgets.models.Widget

//class DataService(db: WidgetData) {
//
//  def findById(id: Int): Option[Widget] = db.findById(id)
//
//  def list(): Seq[Widget] = db.list()
//
//  def create(w: Widget): Try[Widget] = db.create(w)
//
//  def update(w: Widget): Try[Widget] = db.update(w)
//
//  def delete(id: Int): Try[Widget] = db.delete(id)
//
//}



class DataService(db: Database[Widget]) {

  def findById(id: Int): Option[Widget] = db.findById(id)

  def list(): Seq[Widget] = db.list()

  def create(w: Widget): Try[Widget] = db.create(w)

  def update(w: Widget): Try[Widget] = db.update(w)

  def delete(id: Int): Try[Widget] = db.delete(id)

}




