package com.pagerduty.akka.http.support

import akka.http.scaladsl.server.Directive0
import akka.http.scaladsl.server.RouteResult.{Complete, Rejected}
import com.pagerduty.metrics.{Metrics, Stopwatch}
import org.slf4j.Logger

trait MetricsDirectives {
  import akka.http.scaladsl.server.directives.BasicDirectives._

  /**
    * This directive emits the response time using metrics-api.
    *
    * You might place it near the root of your routing tree to time all HTTP responses.
    *
    * @param metricName The name of the metric to emit (it's emitted as a histogram)
    * @param tags Any additional tags to include with the metric
    */
  def emitResponseTime(metricName: String, tags: (String, String)*): Directive0 = {
    extractRequestContext.flatMap { ctx =>
      val stopwatch = Stopwatch.start()
      mapRouteResult { result =>
        val elapsed = stopwatch.elapsed().toMicros.toInt
        metrics.histogram(metricName, elapsed, tags: _*)
        result
      }
    }
  }

  /**
    * This directive increments a metrics-api counter for every request. It tags each increment with the response code
    * and any error type.
    *
    * You might place this near the root of your routing tree to count all HTTP requests. Note that you should handle exceptions
    * and 404s lower in the tree if you want this directive to emit metrics for them. See [[GenericErrorHandling]] for useful handlers.
    *
    * @param metricName The name of the metric to increment
    * @param tags Any additional tags to include on the increment
    */
  def emitRequestCount(metricName: String, tags: (String, String)*): Directive0 = {
    mapRouteResult {
      case result: Complete =>
        val statusCode = result.response.status.intValue
        val responseErrorType = statusCode match {
          case i if i >= 400 && i <= 499 => "client"
          case i if i >= 500 && i <= 599 => "server"
          case _ => "none"
        }
        val augmentedTags = tags ++ Seq(
          ("response_code", statusCode.toString),
          ("response_error_type", responseErrorType)
        )
        metrics.increment(metricName, augmentedTags: _*)
        result
      case result: Rejected =>
        log.warn("Response code metric not emitted because it was not known yet")
        log.warn(
          "If you want to see a response code emitted here, ensure that exceptions/rejections are handled inside the emitResponseCodes directive"
        )
        result
    }
  }

  def metrics: Metrics
  def log: Logger
}
