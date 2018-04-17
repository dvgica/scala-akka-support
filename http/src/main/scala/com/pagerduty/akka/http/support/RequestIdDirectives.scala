package com.pagerduty.akka.http.support

import akka.http.scaladsl.server.Directives.mapRequest

trait RequestIdDirectives {
  val addXRequestId = mapRequest { req =>
    RequestIdHeader.extractRequestId(req) match {
      case Some(_) => req
      case _ => req.addHeader(RequestIdHeader(java.util.UUID.randomUUID.toString))
    }
  }
}
