package org.http4s
package headers

import scalaz.NonEmptyList

object `Accept-Language` extends HeaderKey.Internal[`Accept-Language`] with HeaderKey.Recurring

final case class `Accept-Language` protected (values: List[LanguageTag]) extends Header.RecurringRenderable {
  def key = `Accept-Language`
  type Value = LanguageTag
  def preferred: LanguageTag = values.fold(values.head)((a, b) => if (a.q >= b.q) a else b)
  def satisfiedBy(languageTag: LanguageTag) = values.exists(_.satisfiedBy(languageTag))
}
