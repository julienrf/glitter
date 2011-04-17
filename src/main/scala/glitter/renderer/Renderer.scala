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

package glitter.renderer

import glitter._

/**
 * Render xml content
 */
trait Renderer {
  def render(xml: Xml): String
}

/**
 * Render xml content as text, without any pretty fomatting
 */
object TextRenderer extends Renderer {
  override def render(xml: Xml) = xml match {
    case Nodes(children) => children.foldRight("")((node, acc) => acc + render(node))
    case Tag(name, content, attr) => "<"+name+RenderAttr(attr)+">"+render(content)+"</"+name+">"
    case EmptyTag(name, attr) => "<"+name+RenderAttr(attr)+" />"
    case Text(content) => content
    case Empty => ""
  }
}

object BufferedTextRenderer extends Renderer {
  
  override def render(xml: Xml) = {
    
    val buffer = new StringBuffer
    
    def append(xml: Xml): Unit = xml match {
      case Nodes(children) => children.reverse foreach append
      case Tag(name, content, attr) => {
        buffer.append("<"+name+RenderAttr(attr)+">")
        append(content)
        buffer.append("</"+name+">")
      }
      case EmptyTag(name, attr) => buffer.append("<"+name+RenderAttr(attr)+" />")
      case Text(content) => buffer.append(content)
      case Empty =>
    }
    
    append(xml)
    
    buffer.toString
  }
}

object RenderAttr {
  def apply(attr: Map[String, String]) = attr.foldLeft("")((acc, a) => acc+" "+a._1+"=\""+a._2+"\"")
}
