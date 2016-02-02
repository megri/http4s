package org.http4s
package headers

import org.http4s.util.Writer

import scalaz.OneAnd

object Cookie extends HeaderKey.Internal[Cookie] with HeaderKey.Recurring

final case class Cookie(values: OneAnd[List, org.http4s.Cookie]) extends Header.RecurringRenderable {
  override def key = Cookie
  type Value = org.http4s.Cookie
  override def renderValue(writer: Writer): writer.type = {
    values.head.render(writer)
    values.tail.foreach( writer << "; " << _ )
    writer
  }
}

