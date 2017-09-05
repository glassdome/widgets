package io.glassdome.widgets.services

import io.glassdome.widgets.models.Widget
import io.glassdome.widgets.services.errors._
import org.specs2.mutable._
import org.specs2.specification._
import org.specs2.specification.Scope

class MapWidgetDataSpec extends Specification{
  private val db = MapWidgetData
  db.load()

  "list" should {

    "return all widgets in the Map" >> {
      val ws = db.list()

      ws.exists(_.id == 1) === true
      ws.exists(_.id == 2) === true
      ws.exists(_.id == 3) === true
      ws.exists(_.id == 4) === true
      ws.exists(_.id == 5) === true
    }
  }//end list

  "findById" should  {
    "return an existing Widget by ID" >> {
      db.findById(1) must beSome
    }
    "return None when ID does not exist in collection" >> {
      db.findById(100) must beNone
    }
  }//end findById

  "create" should {
    "return a new Widget if there is no ID conflict" >> {
      val wt = db.create(Widget(100, "hundred", None))
      wt must beSuccessfulTry
      db.findById(100) must beSome
    }

    "FAIL if there is an ID conflict" >> {
      db.create(Widget(200, "two hundred")) must beSuccessfulTry
      db.create(Widget(200, "two hundred")) must beFailedTry.withThrowable[ConflictException]
    }
  }//end create

  "update" should {
    "update an existing Widget " >> {
      val before = db.findById(1)
      before must beSome
      before.get.name === "first"

      val newWidget = Widget(1, "foo")
      db.update(newWidget) must beSuccessfulTry
      val after = db.findById(1)
      after must beSome
      after.get.name === newWidget.name
    }
    "FAIL if the given Widget ID does not exist" >> {
      db.update(Widget(777,"none")) must beFailedTry.withThrowable[ConflictException]
    }
  }//end update

  "delete" should {
    "delete an existing Widget from the map" >> {
      db.findById(2) must beSome
      db.delete(2) must beSuccessfulTry
      db.findById(2) must beNone
    }

    "FAIL if the given Widget ID does not exist" >>{
      db.findById(999) must beNone
      db.delete(999) must beFailedTry.withThrowable[ConflictException]
    }
  }//end delete

}//end MapWidgetDataSpec
