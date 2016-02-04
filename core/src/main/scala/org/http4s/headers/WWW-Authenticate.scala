package org.http4s
package headers

import scalaz.NonEmptyList

object `WWW-Authenticate` extends HeaderKey.Internal[`WWW-Authenticate`] with HeaderKey.Recurring

final case class `WWW-Authenticate` protected (values: List[Challenge]) extends Header.RecurringRenderable {
  override def key = `WWW-Authenticate`
  type Value = Challenge
}

