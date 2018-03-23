package org.http4s
package server
package logging

import java.time.format.DateTimeFormatter
import java.time.{Clock, OffsetDateTime}

import org.http4s.headers.Authorization

abstract class ServerLogging[F[_]] {
  def logRequestResponse(request: Request[F], response: Response[F]): Unit
}

object ServerLogging {
  private[this] val logger = org.log4s.getLogger

  def apply[F[_]](log: (Request[F], Response[F]) => Unit): ServerLogging[F] =
    new ServerLogging[F] {
      override def logRequestResponse(request: Request[F], response: Response[F]): Unit =
        log(request, response)
    }

  def disabled[F[_]]: ServerLogging[F] = new ServerLogging[F] {
    override def logRequestResponse(request: Request[F], response: Response[F]): Unit = ()
  }

  def commonLogFormat[F[_]](
      clock: Clock = Clock.systemDefaultZone(),
      log: String => Unit = logger.info): ServerLogging[F] = {
    val formatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy:hh:mm:ss Z")

    apply[F] { (req, res) =>
      log {
        val remotehost = req.remoteAddr.getOrElse("-") + " "
        val rfc931 = "- "
        val authuser = Authorization.from(req.headers).map(_.credentials) match {
          case Some(BasicCredentials(credentials)) => credentials.username + " "
          case _ => "- "
        }
        val date = "[" + formatter.format(OffsetDateTime.now(clock)) + "] "
        val request = req.method + " " + req.uri + " " + req.httpVersion + " "
        val status = res.status.code + " "
        val bytes = res.contentLength.map(_.toString).getOrElse("-")

        remotehost + rfc931 + authuser + date + request + status + bytes
      }
    }
  }
}