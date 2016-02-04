package org.http4s
package headers

import org.http4s.util.Writer

import scalaz.NonEmptyList

object Allow extends HeaderKey.Internal[Allow] with HeaderKey.Singleton {
  def apply(m: Method, ms: Method*): Allow = Allow(m :: ms.toList)
}

case class Allow protected (methods: List[Method]) extends Header.Parsed {
  override def key = Allow
  override def renderValue(writer: Writer): writer.type =
    writer.addStrings(methods.map(_.name), ", ")
}
