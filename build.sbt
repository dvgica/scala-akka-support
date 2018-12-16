organization := "com.pagerduty"

name := "akka-support-http"

scalaVersion := "2.11.12"

crossScalaVersions := Seq("2.11.12", "2.12.8")

licenses += ("Apache-2.0", url("https://www.apache.org/licenses/LICENSE-2.0.html"))

bintrayOrganization := Some("pagerduty")
bintrayRepository := "oss-maven"

publishMavenStyle := true

resolvers := Seq(
"bintray-pagerduty-oss-maven" at "https://dl.bintray.com/pagerduty/oss-maven",
Resolver.defaultLocal
)

libraryDependencies ++= Seq(
  "org.slf4j" % "slf4j-api" % "1.7.+",
  "com.typesafe.akka" %% "akka-http" % "10.0.10",
  "com.pagerduty" %% "metrics-api" % "2.0.0"
)

scalafmtOnCompile in ThisBuild := true
