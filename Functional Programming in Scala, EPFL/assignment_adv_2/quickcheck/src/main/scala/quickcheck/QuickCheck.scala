package quickcheck

import common._

import org.scalacheck._
import Arbitrary._
import Gen._
import Prop._

import scala.util.Try

abstract class QuickCheckHeap extends Properties("Heap") with IntHeap {

  lazy val genNonEmptyHeap: Gen[H] =
    for {
      x <- arbitrary[Int]
      h <- oneOf(const(empty), genHeap)
    } yield insert(x, h)

  lazy val genHeap: Gen[H] = oneOf(const(empty), genNonEmptyHeap)

  implicit lazy val arbHeap: Arbitrary[H] = Arbitrary(genHeap)

  def popAll(heap: H): List[A] =
    if (isEmpty(heap)) Nil
    else findMin(heap) :: popAll(deleteMin(heap))

  property("gen1") = forAll { (h: H) =>
    val m = if (isEmpty(h)) 0 else findMin(h)
    findMin(insert(m, h)) == m
  }

  property("fact1") = forAll { (x1: Int, x2: Int) =>
    val h = insert(x1, insert(x2, empty))
    val min = if (x1 <= x2) x1 else x2
    findMin(h) == min
  }

  property("fact2") = forAll { (x1: Int) =>
    isEmpty(deleteMin(insert(x1, empty)))
  }

  property("fact3") = forAll { (heap: H) =>
    val elems = popAll(heap)
    elems == elems.sorted
  }

  property("fact4") = forAll { (h1: H, h2: H) =>
    // val min = if (findMin(h1) <= findMin(h2)) findMin(h1) else findMin(h2)
    // findMin(meld(h1, h2)) == min
    val min = List(h1, h2).flatMap { h => Try(findMin(h)).toOption.toList }.sorted.headOption
    val meldedMin = Try(findMin(meld(h1, h2))).toOption
    meldedMin == min
  }

  property("melded heaps should give back elements from 2 original lists") = forAll { (h1: H, h2: H) =>
    popAll(meld(h1, h2)) == (popAll(h1) ++ popAll(h2)).sorted
  }
}
