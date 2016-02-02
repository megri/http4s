package org.http4s
package headers

import scalaz.OneAnd

object `Proxy-Authenticate` extends HeaderKey.Internal[`Proxy-Authenticate`] with HeaderKey.Recurring

final case class `Proxy-Authenticate`(values: OneAnd[List, Challenge]) extends Header.RecurringRenderable {
  override def key = `Proxy-Authenticate`
  type Value = Challenge
}

