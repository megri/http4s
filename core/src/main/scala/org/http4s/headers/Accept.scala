package org.http4s
package headers

import scalaz.OneAnd

object Accept extends HeaderKey.Internal[Accept] with HeaderKey.Recurring

final case class Accept(values: OneAnd[List, MediaRange]) extends Header.RecurringRenderable {
  def key = Accept
  type Value = MediaRange
}

