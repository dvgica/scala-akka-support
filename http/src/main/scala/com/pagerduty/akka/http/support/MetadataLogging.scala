package com.pagerduty.akka.http.support

import org.slf4j.LoggerFactory

trait MetadataLogging {

  val logBase = LoggerFactory.getLogger(getClass)

  case class LoggingImpl(reqMeta: RequestMetadata) {
    def prefix: String = reqMeta.reqId.map(id => s"[X-Request-ID: ${id}] ").getOrElse("")

    def debug(msg: String): Unit = logBase.debug(prefix + msg)
    def info(msg: String): Unit = logBase.info(prefix + msg)
    def error(msg: String): Unit = logBase.error(prefix + msg)
    def warn(msg: String): Unit = logBase.warn(prefix + msg)
  }

  def log(implicit reqMeta: RequestMetadata) = {
    LoggingImpl(reqMeta)
  }
}
