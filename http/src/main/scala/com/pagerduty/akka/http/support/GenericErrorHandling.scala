package com.pagerduty.akka.http.support

import akka.http.scaladsl.model.{HttpResponse, StatusCodes}
import akka.http.scaladsl.server.Directives.{complete, extractRequest}
import akka.http.scaladsl.server.{ExceptionHandler, RejectionHandler}
import com.pagerduty.metrics.Metrics

import java.io.{PrintWriter, StringWriter}

trait GenericErrorHandling extends MetadataLogging {

  /**
    * Useful for catching and completing unhandled exceptions before they propagate through logging or metrics directives.
    */
  lazy val exceptionHandler = ExceptionHandler {
    case e =>
      extractRequest { req =>
        implicit val reqMeta = RequestMetadata.fromRequest(req)

        metrics.increment("server_error", ("exception", e.getClass.getSimpleName))
        log.error(s"Exception handling request: $e")

        val sw = new StringWriter
        e.printStackTrace(new PrintWriter(sw))
        log.error(s"Request stack trace: ${sw.toString}")

        complete(HttpResponse(status = StatusCodes.InternalServerError))
      }
  }

  /**
    * Useful for catching and handling not found rejections before they propagate through logging or metrics directives.
    */
  lazy val notFoundHandler =
    RejectionHandler
      .newBuilder()
      .handleNotFound { complete(HttpResponse(status = StatusCodes.NotFound)) }
      .result()

  def metrics: Metrics
}
