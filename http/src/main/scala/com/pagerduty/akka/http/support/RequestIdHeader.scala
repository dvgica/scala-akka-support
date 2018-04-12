package com.pagerduty.akka.http.support

import akka.http.scaladsl.model.HttpRequest
import akka.http.scaladsl.model.headers.{ModeledCustomHeader, ModeledCustomHeaderCompanion}

import scala.util.Try

/**
  * This is based on com.pagerduty.bffpublicapi.util.RequestIdHeader
  */

final class RequestIdHeader(reqId: String) extends ModeledCustomHeader[RequestIdHeader] {
  override def renderInRequests = true
  override def renderInResponses = true
  override val companion = RequestIdHeader
  override def value: String = reqId
}

object RequestIdHeader extends ModeledCustomHeaderCompanion[RequestIdHeader] {
  override val name = "X-Request-Id"
  override def parse(value: String) = Try(new RequestIdHeader(value))

  def extractRequestId(req: HttpRequest): Option[String] =
    req.headers.collectFirst({
      case RequestIdHeader(reqId) => reqId
    })
}
