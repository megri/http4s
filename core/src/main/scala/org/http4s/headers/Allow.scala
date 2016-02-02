package org.http4s
package headers

import org.http4s.util.Writer

import scalaz.OneAnd

object Allow extends HeaderKey.Internal[Allow] with HeaderKey.Singleton {
  def apply(m: Method, ms: Method*): Allow = Allow(OneAnd(m, ms:_*))
}

case class Allow(methods: OneAnd[List, Method]) extends Header.Parsed {
  override def key = Allow
  override def renderValue(writer: Writer): writer.type =
    writer.addStrings(methods.list.map(_.name), ", ")
}
