# Akka Support [![CircleCI](https://circleci.com/gh/PagerDuty/scala-akka-support.svg?style=svg)](https://circleci.com/gh/PagerDuty/scala-akka-support)

This library is a collection of useful additions to the Akka ecosystem. Specifically, it contains the following:

 - A few [logging](http/src/main/scala/com/pagerduty/akka/http/support/LoggingDirectives.scala), [metrics collection](http/src/main/scala/com/pagerduty/akka/http/support/MetricsDirectives.scala), and [error handling](http/src/main/scala/com/pagerduty/akka/http/support/GenericErrorHandling.scala) additions for Akka HTTP

## Sub-Modules

- akka-support-http - Useful additions to Akka HTTP

## Installation

- This set of libraries is published to PagerDuty Bintray OSS Maven repository. Add it to your resolvers (PD developers can skip this step):

```scala
resolvers += "bintray-pagerduty-oss-maven" at "https://dl.bintray.com/pagerduty/oss-maven"
```

- Sub-modules can be added individually, depending on the code in use:

```scala
libraryDependencies += "com.pagerduty" %% "akka-support-http" % VersionString
```

## License

Copyright 2017, PagerDuty, Inc.

This work is licensed under the [Apache Software License](https://www.apache.org/licenses/LICENSE-2.0).

## Contributing

Fork, hack, submit pull request. We will get back to you as soon as possible.

## Release

Follow these steps to release a new version:
- Update version.sbt in your PR
- When the PR is approved, merge it to master, and delete the branch
- Travis will run all tests, publish to Bintray, and create a new version tag in Github
