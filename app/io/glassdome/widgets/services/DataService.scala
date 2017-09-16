package io.glassdome.widgets.services

import scala.util.Try
import io.glassdome.widgets.models.{Widget, AppUser}

class DataService(db: WidgetData) {

  def findById(id: Int): Option[Widget] = db.findById(id)

  def list(): Seq[Widget] = db.list()

  def create(w: Widget): Try[Widget] = db.create(w)

  def update(w: Widget): Try[Widget] = db.update(w)

  def delete(id: Int): Try[Widget] = db.delete(id)

  def listWidgetsByUser(userId: Int)= db.listWidgetsByUser(userId)

}


class DataService1(db: Database[Widget]) {

  def findById(id: Int): Option[Widget] = db.findById(id)

  def list(): Seq[Widget] = db.list()

  def create(w: Widget): Try[Widget] = db.create(w)

  def update(w: Widget): Try[Widget] = db.update(w)

  def delete(id: Int): Try[Widget] = db.delete(id)

}




class DataService2(db: Database[AppUser] ){

  def findById(id: Int): Option[AppUser] = db.findById(id)

  def list(): Seq[AppUser] = db.list()

  def create(u: AppUser): Try[AppUser] = db.create(u)

  def update(u: AppUser): Try[AppUser] = db.update(u)

  def delete(id: Int): Try[AppUser] = db.delete(id)

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






