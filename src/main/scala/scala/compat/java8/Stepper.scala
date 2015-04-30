package scala.compat.java8

trait Stepper[@specialized(Double, Int, Long) A, CC] extends Any {
  def characteristics: Int
  def hasStep: Boolean
  def knownSize: Long
  def nextStep: A
  def tryStep(f: A => Unit): Boolean
  def substep(): Stepper[A, CC]
  def typedPrecisely: CC

  // Terminal operations that do not produce another Stepper
  def count(): Long = { var n = 0L; while (hasStep) { nextStep; n += 1 }; n }
  def count(p: A => Boolean): Long = { var n = 0L; while (hasStep) { if (p(nextStep)) n += 1 }; n }
  def exists(p: A => Boolean): Boolean = { while(hasStep) { if (p(nextStep)) return true }; false }
  def find(p: A => Boolean): Option[A] = { while (hasStep) { val a = nextStep; if (p(a)) return Some(a) }; None }
  def fold[@specialized(Double, Int, Long) B](zero: B)(op: (B, A) => B) = { var b = zero; while (hasStep) { b = op(b, nextStep) }; b }
  def foreach(f: A => Unit) { while (hasStep) f(nextStep) }
  def reduce(f: (A, A) => A): A = { var a = nextStep; while (hasStep) { a = f(a, nextStep) }; a }
}

trait StepperGeneric[A] extends Stepper[A, StepperGeneric[A]] with java.util.Iterator[A] with java.util.Spliterator[A] {
  def forEachRemaining(c: java.util.function.Consumer[_ >: A]) { while (hasNext) { c.accept(next) } }
  def hasStep = hasNext()
  def knownSize = getExactSizeIfKnown
  def nextStep = next
  def tryAdvance(c: java.util.function.Consumer[_ >: A]): Boolean = if (hasNext) { c.accept(next); true } else false
  def tryStep(f: A => Unit): Boolean = if (hasNext) { f(next); true } else false
  def trySplit() = substep.typedPrecisely
  final def typedPrecisely: StepperGeneric[A] = this
}

trait StepperDouble extends Stepper[Double, StepperDouble] with java.util.PrimitiveIterator.OfDouble with java.util.Spliterator.OfDouble {
  def forEachRemaining(c: java.util.function.Consumer[_ >: java.lang.Double]) { while (hasNext) { c.accept(java.lang.Double.valueOf(nextDouble)) } }
  def forEachRemaining(c: java.util.function.DoubleConsumer) { while (hasNext) { c.accept(nextDouble) } }
  def hasStep = hasNext()
  def knownSize = getExactSizeIfKnown
  def nextStep = nextDouble
  def tryAdvance(c: java.util.function.Consumer[_ >: java.lang.Double]): Boolean = if (hasNext) { c.accept(java.lang.Double.valueOf(nextDouble)); true } else false
  def tryAdvance(c: java.util.function.DoubleConsumer): Boolean = if (hasNext) { c.accept(nextDouble); true } else false
  def tryStep(f: Double => Unit): Boolean = if (hasNext) { f(nextDouble); true } else false
  def trySplit() = substep.typedPrecisely
  final def typedPrecisely = this
}

trait StepperInt extends Stepper[Int, StepperInt] with java.util.PrimitiveIterator.OfInt with java.util.Spliterator.OfInt {
  def forEachRemaining(c: java.util.function.Consumer[_ >: java.lang.Integer]) { while (hasNext) { c.accept(java.lang.Integer.valueOf(nextInt)) } }
  def forEachRemaining(c: java.util.function.IntConsumer) { while (hasNext) { c.accept(nextInt) } }
  def hasStep = hasNext()
  def knownSize = getExactSizeIfKnown
  def nextStep = nextInt
  def tryAdvance(c: java.util.function.Consumer[_ >: java.lang.Integer]): Boolean = if (hasNext) { c.accept(java.lang.Integer.valueOf(nextInt)); true } else false
  def tryAdvance(c: java.util.function.IntConsumer): Boolean = if (hasNext) { c.accept(nextInt); true } else false
  def tryStep(f: Int => Unit): Boolean = if (hasNext) { f(nextInt); true } else false
  def trySplit() = substep.typedPrecisely
  final def typedPrecisely = this
}

trait StepperLong extends Stepper[Long, StepperLong] with java.util.PrimitiveIterator.OfLong with java.util.Spliterator.OfLong {
  def forEachRemaining(c: java.util.function.Consumer[_ >: java.lang.Long]) { while (hasNext) { c.accept(java.lang.Long.valueOf(nextLong)) } }
  def forEachRemaining(c: java.util.function.LongConsumer) { while (hasNext) { c.accept(nextLong) } }
  def hasStep = hasNext()
  def knownSize = getExactSizeIfKnown
  def nextStep = nextLong
  def tryAdvance(c: java.util.function.Consumer[_ >: java.lang.Long]): Boolean = if (hasNext) { c.accept(java.lang.Long.valueOf(nextLong)); true } else false
  def tryAdvance(c: java.util.function.LongConsumer): Boolean = if (hasNext) { c.accept(nextLong); true } else false
  def tryStep(f: Long => Unit): Boolean = if (hasNext) { f(nextLong); true } else false
  def trySplit() = substep.typedPrecisely
  final def typedPrecisely = this
}

