/* Glitter  Copyright (C) 2011  Julien Richard-Foy <julien@richard-foy.fr>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

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
  
  implicit def traversableToNodes(ns: Traversable[Xml]) = Nodes(ns.toList.reverse)
  implicit def arrayToNodes[A <: Xml](ns: Array[A]) = Nodes(List(ns: _*).reverse)

  // Useful helpers
  /** HTML 5 Doctype declaration */
  def html5dtd = "<!DOCTYPE html>\n".raw
}