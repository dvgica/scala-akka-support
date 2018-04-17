package com.pagerduty.akka.http.support

import akka.http.scaladsl.model.HttpRequest
import akka.http.scaladsl.model.headers.{ModeledCustomHeader, ModeledCustomHeaderCompanion}

import scala.util.Try

final class RequestIdHeader(reqId: String) extends ModeledCustomHeader[RequestIdHeader] {
  def renderInRequests = true
  def renderInResponses = true
  val companion = RequestIdHeader
  def value: String = reqId
}

object RequestIdHeader extends ModeledCustomHeaderCompanion[RequestIdHeader] {
  val name = "X-Request-Id"
  def parse(value: String): Try[RequestIdHeader] = Try(new RequestIdHeader(value))

  def extractRequestId(req: HttpRequest): Option[String] =
    req.headers.collectFirst({
      case RequestIdHeader(reqId) => reqId
    })
}
