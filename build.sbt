ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.8"

lazy val root = (project in file("."))
  .settings(
    name := "restaurant-api"
  )

libraryDependencies ++= Seq(
  /*"org.scalikejdbc" %% "scalikejdbc"       % "3.5.0",
  "com.h2database"  %  "h2"                % "1.4.200",
  "ch.qos.logback"  %  "logback-classic"   % "1.2.3",*/
  "org.xerial" % "sqlite-jdbc" % "3.7.2",
  "org.tpolecat" %% "doobie-core"      % "1.0.0-RC1",
  "com.lihaoyi" %% "cask" % "0.8.3",
  "com.lihaoyi" %% "requests" % "0.7.1",
  "com.lihaoyi" %% "utest" % "0.7.11" % "test",
  "com.lihaoyi" %% "upickle" % "2.0.0"
)
