package org.http4s
package headers

import java.net.InetAddress

import org.http4s.util.Writer

import scalaz.NonEmptyList

object `X-Forwarded-For` extends HeaderKey.Internal[`X-Forwarded-For`] with HeaderKey.Recurring

final case class `X-Forwarded-For` protected (values: List[Option[InetAddress]]) extends Header.Recurring {
  override def key = `X-Forwarded-For`
  type Value = Option[InetAddress]
  override lazy val value = super.value
  override def renderValue(writer: Writer): writer.type = {
    values match {
      case List(head, tail@_*) =>
        head.fold(writer.append("unknown"))(i => writer.append(i.getHostAddress))
        tail.foreach(append(writer, _))
        writer
    }
  }

  @inline
  private def append(sb: Writer, add: Option[InetAddress]): Unit = {
    sb.append(", ")
    if (add.isDefined) sb.append(add.get.getHostAddress)
    else sb.append("unknown")
  }
}

