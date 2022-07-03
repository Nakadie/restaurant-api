ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.8"

lazy val root = (project in file("."))
  .settings(
    name := "restaurant-api"
  )

libraryDependencies ++= Seq(
  "com.lihaoyi" %% "cask" % "0.8.3",
  "com.lihaoyi" %% "requests" % "0.7.1",
  "com.lihaoyi" %% "utest" % "0.7.11" % "test",
  "com.lihaoyi" %% "upickle" % "2.0.0",
)
