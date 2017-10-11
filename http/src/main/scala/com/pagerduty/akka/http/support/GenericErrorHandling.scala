package com.pagerduty.akka.http.support

import akka.http.scaladsl.model.{HttpResponse, StatusCodes}
import akka.http.scaladsl.server.Directives.complete
import akka.http.scaladsl.server.{ExceptionHandler, RejectionHandler}
import com.pagerduty.metrics.Metrics
import org.slf4j.Logger

trait GenericErrorHandling {

  /**
    * Useful for catching and completing unhandled exceptions before they propagate through logging or metrics directives.
    */
  lazy val exceptionHandler = ExceptionHandler {
    case e =>
      metrics.increment("server_error", ("exception", e.getClass.getSimpleName))
      log.error(s"Exception handling request: $e")
      complete(HttpResponse(status = StatusCodes.InternalServerError))
  }

  /**
    * Useful for catching and handling not found rejections before they propagate through logging or metrics directives.
    */
  lazy val notFoundHandler =
    RejectionHandler
      .newBuilder()
      .handleNotFound { complete(HttpResponse(status = StatusCodes.NotFound)) }
      .result()

  def log: Logger
  def metrics: Metrics
}
