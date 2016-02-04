package org.http4s

import scalaz.NonEmptyList

package object headers {
  private[http4s] implicit class NonEmptyListSyntax[A](nel: NonEmptyList[A]) {
    def exists(p: A => Boolean): Boolean =
      p(nel.head) || {
        var these = nel.tail
        while (!these.isEmpty) {
          if (p(these.headOption.get)) return true
          these = these.tailOption.get
        }
        false
      }

    def contains(elem: A): Boolean =
      exists(_ == elem)

    def mkString(sep: String): String = {
      val sb = new StringBuilder()
      sb.append(nel.head)
      nel.tail.map { a =>
        sb.append(sep)
        sb.append(a)
      }
      sb.toString
    }
  }

}
