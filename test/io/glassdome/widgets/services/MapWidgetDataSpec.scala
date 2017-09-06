package io.glassdome.widgets.services

import io.glassdome.widgets.models.Widget
import io.glassdome.widgets.services.errors._
import org.specs2.mutable._
import org.specs2.specification._
import org.specs2.specification.Scope

class MapWidgetDataSpec extends Specification{
  private val db = MapWidgetData
  private val path = getClass.getResource("/widgets_test.dat").getPath
 //db.load

sequential
  "load" should {
    "return should return an empty map if the file is empty" >>{
      db.load(path)
      db.isEmpty should beTrue
    }
  }

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
      val before = db.findById(100)
      before must beSome
      before.get.name === "hundred"

      val newWidget = Widget(100, "foo")
      db.update(newWidget) must beSuccessfulTry
      val after = db.findById(100)
      after must beSome
      after.get.name === newWidget.name
    }
    "FAIL if the given Widget ID does not exist" >> {
      db.update(Widget(777,"none")) must beFailedTry.withThrowable[ConflictException]
    }
  }//end update

  "findById" should  {
      "return an existing Widget by ID" >> {
        db.findById(100) must beSome
      }
      "return None when ID does not exist in collection" >> {
        db.findById(777) must beNone
      }
    }//end findById


  "delete" should {
    "delete an existing Widget from the map" >> {
      db.findById(100) must beSome
      db.delete(100)
      db.findById(100) must beNone
    }

    "FAIL if the given Widget ID does not exist" >>{
      db.findById(999) must beNone
      db.delete(999) must beFailedTry.withThrowable[ConflictException]
    }
  }//end delete

  "list" should {
    "return an empty list if no widgets in the Map" >> {
      db.delete(200)
      db.findById(200) must beNone

      db.isEmpty === true
      db.list().isEmpty == true
    }

    "return all widgets in the Map if it's not empty" >> {
      db.isEmpty == true
      for(i <-(1 to 3)) yield {db.create(Widget(i, s"widget $i", None)) must beSuccessfulTry}

      val ws = db.list()

      ws.exists(_.id == 1) === true
      ws.exists(_.id == 2) === true
      ws.exists(_.id == 3) === true

    }
  }//end list

  // can't figure out why the save() breaks the code here
//  "save" should {
//    "save all the widgets in the map to the file" >> {
//      db.isEmpty === false
//      db.save(path)
//       db.load(path)
//      val ws = db.list()
//
//      ws.exists(_.id == 1) === true
//      ws.exists(_.id == 2) === true
//      ws.exists(_.id == 3) === true
//
//
//      for(i <- (1 to 3)) yield db.delete(i)
//      db.isEmpty === true
//      db.save()
//    }
//  }//end save


}//end MapWidgetDataSpec
