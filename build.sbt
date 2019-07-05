lazy val scala213 = "2.13.0"
lazy val scala212 = "2.12.8"
lazy val scala211 = "2.11.12"
lazy val supportedScalaVersions = List(scala213, scala212, scala211)

ThisBuild / organization := "com.pagerduty"
ThisBuild / scalaVersion := scala212

lazy val sharedSettings = Seq(
  crossScalaVersions := supportedScalaVersions,
  licenses += ("Apache-2.0", url("https://www.apache.org/licenses/LICENSE-2.0.html")),
  bintrayOrganization := Some("pagerduty"),
  bintrayRepository := "oss-maven",
  publishMavenStyle := true,
  resolvers := Seq(
    "bintray-pagerduty-oss-maven" at "https://dl.bintray.com/pagerduty/oss-maven",
    Resolver.defaultLocal
  )
)

lazy val http = (project in file("http"))
  .settings(sharedSettings: _*)
  .settings(
    name := "akka-support-http",
    libraryDependencies ++= Seq(
      "org.slf4j" % "slf4j-api" % "1.7.+",
      "com.typesafe.akka" %% "akka-http" % "10.1.8",
      "com.typesafe.akka" %% "akka-stream" % "2.5.23",
      "com.pagerduty" %% "metrics-api" % "2.1.4"
    )
  )

lazy val root = (project in file("."))
  .aggregate(http)
  .settings(
    // crossScalaVersions must be set to Nil on the aggregating project
    crossScalaVersions := Nil,
    publish / skip := true
  )

scalafmtOnCompile in ThisBuild := true
