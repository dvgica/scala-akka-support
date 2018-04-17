package com.pagerduty.akka.http.support

import akka.http.scaladsl.model.{HttpHeader, HttpRequest}

case class RequestMetadata(reqId: Option[String]) {
  val requestIdHeader: Option[HttpHeader] = reqId.map(r => RequestIdHeader(r))
}

object RequestMetadata {
  def fromRequest(req: HttpRequest): RequestMetadata = {
    RequestMetadata(RequestIdHeader.extractRequestId(req))
  }
}
