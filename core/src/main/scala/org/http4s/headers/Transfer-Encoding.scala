package org.http4s
package headers

import scalaz.OneAnd
import org.http4s.util.oneandlist._

object `Transfer-Encoding` extends HeaderKey.Internal[`Transfer-Encoding`] with HeaderKey.Recurring

final case class `Transfer-Encoding`(values: OneAnd[List, TransferCoding]) extends Header.RecurringRenderable {
  override def key = `Transfer-Encoding`
  def hasChunked = values.exists(_.renderString.equalsIgnoreCase("chunked"))
  type Value = TransferCoding
}

