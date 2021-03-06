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
               attr: List[Attribute] = Nil) extends Xml {

  /** Set attributes to this tag */
  def % (as: Attribute*) = EmptyTag(name, attr ++ as)

  /** Nest some content inside this node */
  def apply(content: Xml) = Tag(name, content, attr)
}

/** A tag with content, e.g. `<span>foo</span>` */
case class Tag(name: String, content: Xml, attr: List[Attribute] = Nil) extends Xml

/** Raw text */
case class Text(content: String) extends Xml {
  override def | (sibling: Xml) = sibling match {
    case Text(c) => Text(content + c)
    case _       => super.|(sibling)
  }
}

/** Convenient empty xml content */
case object Empty extends Xml {
  override def | (sibling: Xml) = sibling
}

/** An XML attrbute. Its value is optional to allow HTML5 empty attributes */
case class Attribute(name: String, value: Option[String] = None)

class StringWrapper(s: String) {
  def raw = Text(s)
}
