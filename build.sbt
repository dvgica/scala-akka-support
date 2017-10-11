lazy val sharedSettings = Seq(
  organization := "com.pagerduty",
  scalaVersion := "2.11.11",
  crossScalaVersions := Seq("2.11.11", "2.12.2"),
  licenses += ("Apache-2.0", url("https://www.apache.org/licenses/LICENSE-2.0.html")),
  bintrayOrganization := Some("pagerduty"),
  bintrayRepository := "oss-maven",
  publishMavenStyle := true
)

lazy val http = (project in file("http")).
  settings(sharedSettings: _*).
  settings(
    name := "akka-support-http",
    libraryDependencies ++= Seq(
      "org.slf4j" % "slf4j-api" % "1.7+",
      "com.typesafe.akka" %% "akka-http" % "10.0.10",
      "com.pagerduty" %% "metrics-api" % "2.0.0"
    )
  )

lazy val root = Project(id = "root", base = file(".")).
  dependsOn(http).
  aggregate(http).
  settings(sharedSettings ++ Seq(
    publishLocal := {},
    publish := {},
    publishArtifact := false,
    bintrayReleaseOnPublish := false
  )
)
