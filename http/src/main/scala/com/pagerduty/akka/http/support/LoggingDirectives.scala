package com.pagerduty.akka.http.support

import akka.http.scaladsl.server.Directive0
import akka.http.scaladsl.server.RouteResult.{Complete, Rejected}
import org.slf4j.Logger

trait LoggingDirectives {
  import akka.http.scaladsl.server.Directives._

  def logRequestAndResult: Directive0 = {
    extractRequestContext.flatMap { ctx =>
      val req = ctx.request
      log.info(s"Received request: ${req.method.value} ${req.uri}")
      mapRouteResult {
        case result: Complete =>
          log.info(s"Completed request with status ${result.response.status}")
          result
        case result: Rejected =>
          log.error(s"Failed to complete request! Rejections were: ${result.rejections}")
          log.error(
            "If you want to see a status code logged here, ensure that exceptions/rejections are handled inside the logRequestAndResult directive"
          )
          result
      }
    }
  }

  def log: Logger
}