package object glitter {

  // Implicits
  implicit def symbolToEmptyTag(s: Symbol) = EmptyTag(s.name)
  
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