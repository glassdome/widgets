name := """widgets"""
organization := "io.glassdome"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.2"

libraryDependencies += guice

libraryDependencies += "org.specs2" % "specs2-core_2.12" % "3.9.5" % "test"



// Adds additional packages into Twirl
//TwirlKeys.templateImports += "io.glassdome.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "io.glassdome.binders._"
