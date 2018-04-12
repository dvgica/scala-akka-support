package com.pagerduty.akka.http.support

import akka.http.scaladsl.model.{HttpHeader, HttpRequest}

/**
  * This is based on com.pagerduty.bffpublicapi.util.RequestContext
  */

case class RequestContext(reqId: Option[String]) {
  val requestIdHeader: Option[HttpHeader] = reqId.map(r => RequestIdHeader(r))
}

object RequestContext {
  def fromRequest(req: HttpRequest) = {
    RequestContext(RequestIdHeader.extractRequestId(req))
  }
}
