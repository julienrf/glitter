package object glitter {

  // Implicits
  implicit def symbolToEmptyTag(s: Symbol) = EmptyTag(s.name)
  
  implicit def symbolToAttr(s: Symbol) = Attribute(s.name)
  
  implicit def tupleToAttr(t: (Symbol, String)) = Attribute(t._1.name, Some(t._2))
  
  implicit def makeTuple[A](a: A) = new {
    def ~[B](b: B) = (a, b)
  }
  
  implicit def strToText(s: String) = Text(xml.Utility.escape(s))

  implicit def strToStringWrapper(s: String) = new StringWrapper(s)
  
  implicit val defaultRenderer = renderer.BufferedTextRenderer

  // Useful helpers
  /** HTML 5 Doctype declaration */
  def html5dtd = "<!DOCTYPE html>\n".raw

  /** Iterate through a collection. */
  def forM[A] (elmts: Traversable[A])(bind: A => Xml) =
    elmts.foldLeft[Xml](Empty)((acc, elmt) => acc | bind(elmt))
}