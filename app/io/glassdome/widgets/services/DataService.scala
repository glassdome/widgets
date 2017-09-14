package io.glassdome.widgets.services

import scala.util.Try
import io.glassdome.widgets.models.{Widget, appUser}

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

class DataService2(db: Database[appUser] ){

  def findById(id: Int): Option[appUser] = db.findById(id)

  def list(): Seq[appUser] = db.list()

  def create(u: appUser): Try[appUser] = db.create(u)

  def update(u: appUser): Try[appUser] = db.update(u)

  def delete(id: Int): Try[appUser] = db.delete(id)

}




//class DataService(db: Database[A]) {
//
//  def findById(id: Int): Option[A] = db.findById(id)
//
//  def list(): Seq[A] = db.list()
//
//  def create(w: A): Try[A] = db.create(w)
//
//  def update(w: A): Try[A] = db.update(w)
//
//  def delete(id: Int): Try[A] = db.delete(id)
//
//}






