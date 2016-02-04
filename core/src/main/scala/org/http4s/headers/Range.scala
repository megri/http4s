package org.http4s
package headers

import org.http4s.util.{Renderable, Writer}

import scalaz.NonEmptyList

// See https://tools.ietf.org/html/rfc7233

object Range extends HeaderKey.Internal[Range] with HeaderKey.Singleton {

  def apply(unit: RangeUnit, r1: SubRange, rs: SubRange*): Range =
    Range(unit, NonEmptyList(r1, rs:_*))

  def apply(r1: SubRange, rs: SubRange*): Range = apply(RangeUnit.Bytes, r1, rs:_*)

  def apply(begin: Long, end: Long): Range = apply(SubRange(begin, Some(end)))

  def apply(begin: Long): Range = apply(SubRange(begin, None))

  object SubRange {
    def apply(first: Long): SubRange = SubRange(first, None)
    def apply(first: Long, second: Long): SubRange = SubRange(first, Some(second))
  }

  case class SubRange(first: Long, second: Option[Long]) extends Renderable {
    /** Base method for rendering this object efficiently */
    override def render(writer: Writer): writer.type = {
      writer << first
      second.foreach( writer << '-' << _ )
      writer
    }
  }


}

case class Range(unit: RangeUnit, ranges: NonEmptyList[Range.SubRange]) extends Header.Parsed {
  override def key = Range
  override def renderValue(writer: Writer): writer.type = {
    writer << unit << '=' << ranges.head
    ranges.tail.map( writer << ',' << _)
    writer
  }
}

