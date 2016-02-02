package org.http4s
package headers

import scalaz.OneAnd

object `WWW-Authenticate` extends HeaderKey.Internal[`WWW-Authenticate`] with HeaderKey.Recurring

final case class `WWW-Authenticate`(values: OneAnd[List, Challenge]) extends Header.RecurringRenderable {
  override def key = `WWW-Authenticate`
  type Value = Challenge
}

