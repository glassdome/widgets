addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.6.3")


// sbt-ecplise plugin
addSbtPlugin("com.typesafe.sbteclipse" % "sbteclipse-plugin" % "3.0.0")

libraryDependencies += "org.postgresql" % "postgresql" % "9.3-1102-jdbc4"

addSbtPlugin("org.flywaydb" % "flyway-sbt" % "4.2.0")

resolvers += "Flyway" at "https://flywaydb.org/repo"