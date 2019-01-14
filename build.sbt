lazy val sharedSettings = Seq(
  organization := "com.pagerduty",
  scalaVersion := "2.11.12",
  crossScalaVersions := Seq("2.11.12", "2.12.8"),
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
      "com.typesafe.akka" %% "akka-http" % "10.1.7",
      "com.typesafe.akka" %% "akka-stream" % "2.5.9",
      "com.pagerduty" %% "metrics-api" % "2.0.0"
    )
  )

lazy val root = (project in file("."))
  .settings(sharedSettings: _*)
  .settings(
    publishLocal := {},
    publish := {},
    publishArtifact := false
  )
  .aggregate(http)

scalafmtOnCompile in ThisBuild := true
skip in publish := true
