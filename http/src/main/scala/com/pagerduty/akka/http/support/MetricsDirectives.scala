package com.pagerduty.akka.http.support

import akka.http.scaladsl.server.Directive0
import akka.http.scaladsl.server.RouteResult.{Complete, Rejected}
import com.pagerduty.metrics.{Metrics, Stopwatch}
import org.slf4j.Logger

trait MetricsDirectives {
  import akka.http.scaladsl.server.directives.BasicDirectives._

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
