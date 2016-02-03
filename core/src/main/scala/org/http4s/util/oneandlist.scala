package org.http4s.util

import scalaz.OneAnd

object oneandlist {
  implicit class OneAndListSyntax[A](oneAnd: OneAnd[List, A]) {
    def toList =
      oneAnd.head :: oneAnd.tail

    def contains(a: A): Boolean =
      (oneAnd.head == a) || oneAnd.tail.contains(a)

    def collectFirst[B](pf: PartialFunction[A, B]): Option[B] =
      pf.lift(oneAnd.head).orElse(oneAnd.tail.collectFirst(pf))

    def exists(p: A => Boolean): Boolean =
      p(oneAnd.head) || oneAnd.tail.exists(p)

    def mkString(sep: String): String =
      toList.mkString(sep)

    def map[B](f: A => B): OneAnd[List, B] =
      OneAnd(f(oneAnd.head), oneAnd.tail.map(f))

    def append(anotherOneAnd: OneAnd[List, A]) =
      OneAnd(oneAnd.head, oneAnd.tail ::: anotherOneAnd.toList)
  }
}
