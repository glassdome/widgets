//package io.glassdome.widgets.services
//
//
//import io.glassdome.widgets.models._
//import org.specs2.mutable._
//import org.specs2.specification._
//import org.specs2.specification.Scope
//import io.glassdome.widgets.services.errors._
//
//
//class MapWidgetDataSpec extends Specification {
//  
//  val db = MapWidgetData
//  db.loadData()
//  
//  "list" should {
//    
//    "return all widgets in the Map" >> {
//      val ws = db.list
//      
//      ws.size === 5
//      ws.exists( _.id == 1) === true
//      ws.exists( _.id == 2) === true
//      ws.exists( _.id == 3) === true
//      ws.exists( _.id == 4) === true
//      ws.exists( _.id == 5) === true
//    }
//  }
//  
//  "findById" should {
//    
//    "return an existing Widget by ID" >> {
//      db.findById(1) must beSome
//    }
//    
//    "return None when ID does not exist in collection" >> {
//      db.findById(100) must beNone
//    }
//    
//  }
//
//  "create" should {
//    
//    "create a new Widget if there are no ID conflicts" >> {
//      val wt = db.create(Widget(100, "foo", None))
//      wt must beSuccessfulTry
//      db.findById(100) must beSome
//    }
//    
//    "FAIL if there is an ID conflict" >> {
//      db.create(Widget(200, "foo")) must beSuccessfulTry
//      db.create(Widget(200, "foo")) must beFailedTry.withThrowable[ConflictException]
//    }
//  }
//  
//  "update" should {
//    
//    "update an existing Widget" >> {
//      val before = db.findById(1)
//      before must beSome
//      before.get.name === "alpha"
//      
//      val newWidget = Widget(1, "foo")
//      db.update(newWidget) must beSuccessfulTry
//      val after = db.findById(1)
//      after must beSome
//      after.get.name === newWidget.name
//    }
//    
//    "FAIL if the given Widget ID does not exist" >> {
//      db.update(Widget(777, "none")) must beFailedTry
//    }
//  }
//  
//  "delete" should {
//    
//    "delete an existing Widget from the Map" >> {
//      db.findById(1) must beSome
//      db.delete(1) must beSuccessfulTry
//      db.findById(1) must beNone
//    }
//    
//    "FAIL if the given Widget ID does not exist" >> {
//      db.findById(555) must beNone
//      db.delete(555) must beFailedTry 
//    }
//    
//  }
//  
//  
//}