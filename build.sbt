name := """widgets"""
organization := "io.glassdome"

version := "0.1.0"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.2"


libraryDependencies ++= Seq(
	"org.postgresql" % "postgresql" % "9.4.1208.jre7",
	"org.flywaydb" 	% "flyway-core" 		% "4.2.0",
	"org.specs2" 	% "specs2-core_2.12" 	% "3.9.5" % "test",
	guice
)

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "io.glassdome.binders._"



flywayUrl := sys.env("FLYWAY_URL")
flywayUser := sys.env("FLYWAY_USER")
flywayPassword := sys.env("FLYWAY_PASSWORD")

//flywayLocations := Seq("filesystem:src/main/resources/db/migration")