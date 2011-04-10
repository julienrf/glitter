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

package glitter

import renderer.Renderer
import renderer.defaultRenderer

object Glitter {

  // Implicit conversions
  implicit def symbolToEmptyTag(s: Symbol) = EmptyTag(s.name)
  
  implicit def strToText(s: String) = Text(escapeHtml(s))

  implicit def strToStringWrapper(s: String) = new StringWrapper(s)

  // Useful helpers
  /** HTML 5 Doctype declaration */
  def html5dtd = "<!DOCTYPE html>\n".raw

  /** Iterate through a collection. */
  def forM[A] (elmts: Iterable[A])(bind: A => Xml) =
    elmts.foldLeft[Xml](Empty)((acc, elmt) => acc | bind(elmt))

  def escapeHtml(s: String) = s // TODO
}

/** Base class for xml content */
sealed abstract class Xml {

  /** Chain this xml element with a sibling element */
  def | (xml: Xml): Xml = Nodes(List(xml, this))
  
  /** Nest this element in a parent empty tag */
  def :: (parent: EmptyTag) = Tag(parent.name, this, parent.attr)

  /** Render this xml content */
  def render(implicit renderer: Renderer) = renderer.render(this)
}

/** A set of sibling xml nodes */
case class Nodes(children: List[Xml]) extends Xml {
  
  override def | (sibling: Xml) = Nodes(sibling :: children)
}

/** A tag witout content, e.g. `<br />` */
case class EmptyTag(name: String,
               attr: Map[String, String] = Map.empty) extends Xml {

  /** Set attributes to this tag */
  def % (as: (Symbol, String)*) = EmptyTag(name, attr ++ (as map (a =>(a._1.name, a._2))))

  /** Nest some content inside this node */
  def apply(content: Xml) = Tag(name, content, attr)
}

/** A tag with content, e.g. `<span>foo</span>` */
case class Tag(name: String, content: Xml, attr: Map[String, String] = Map.empty) extends Xml

/** Raw text */
case class Text(content: String) extends Xml

/** Convenient empty xml content (useful for the forM monad) */
object Empty extends Xml {
  override def | (sibling: Xml) = sibling
}

class StringWrapper(s: String) {
  def raw = Text(s)
}
