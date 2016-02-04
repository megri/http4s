package org.http4s
package headers

import scalaz.NonEmptyList

object `Transfer-Encoding` extends HeaderKey.Internal[`Transfer-Encoding`] with HeaderKey.Recurring

final case class `Transfer-Encoding` protected (values: List[TransferCoding]) extends Header.RecurringRenderable {
  override def key = `Transfer-Encoding`
  def hasChunked = values.exists(_.renderString.equalsIgnoreCase("chunked"))
  type Value = TransferCoding
}

