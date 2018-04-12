package com.pagerduty.akka.http.support

import org.slf4j.LoggerFactory

trait Logging {

  /**
    * This is based on com.pagerduty.bffpublicapi.util.Logging
    */

  val logBase = LoggerFactory.getLogger(getClass)

  case class LoggingImpl(ctx: RequestContext) {
    def prefix: String = ctx.reqId.map(id => s"[X-Request-ID: ${id}] ").getOrElse("")

    def debug(msg: String): Unit = logBase.debug(prefix + msg)
    def info(msg: String): Unit = logBase.info(prefix + msg)
    def error(msg: String): Unit = logBase.error(prefix + msg)
    def warn(msg: String): Unit = logBase.warn(prefix + msg)
  }

  def log(implicit ctx: RequestContext) = {
    LoggingImpl(ctx)
  }
}
