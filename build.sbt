ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.8"

lazy val root = (project in file("."))
  .settings(
    name := "restaurant-api"
  )

libraryDependencies ++= Seq(
  "org.xerial" % "sqlite-jdbc" % "3.36.0.3",
  "org.tpolecat" %% "doobie-core"      % "1.0.0-RC1",
  "com.lihaoyi" %% "cask" % "0.8.3",
  "com.lihaoyi" %% "requests" % "0.7.1",
  "com.lihaoyi" %% "utest" % "0.7.11" % "test",
  "com.lihaoyi" %% "upickle" % "2.0.0",
  "org.scalikejdbc" %% "scalikejdbc"               % "4.0.0",
  "org.scalikejdbc" %% "scalikejdbc-interpolation" % "4.0.0",
  "ch.qos.logback"  %  "logback-classic"           % "1.2.11",
)
