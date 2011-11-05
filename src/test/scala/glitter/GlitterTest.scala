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

import org.scalatest.FunSuite
import org.scalatest.matchers.ShouldMatchers

class GlitterTest extends FunSuite with ShouldMatchers {
  test("Strings are implicitly converted to text") {
    val txt: Xml = "foo"
    txt.render should be ("foo")
  }
  
  test("Symbols are implicitly converted to tags") {
    val tag: Xml = 'foo
    tag.render should be ("<foo />")
  }
  
  test("Attach attributes to tags") {
    val xml = 'foo %('bar->"baz")
    xml.render should be ("<foo bar=\"baz\" />")
  }
  
  test("Attribute with no value") {
    val xml = 'foo % 'bar
    xml.render should be ("<foo bar />")
    
    val xml2 = 'foo %('bar->"baz", 'bah) 
    xml2.render should be ("<foo bar=\"baz\" bah />")
  }
  
  test("Nested tags") {
    val xml = 'foo :: 'bar
    xml.render should be ("<foo><bar /></foo>")
  }
  
  test("Content sequence") {
    val xml = 'foo | "bar"
    xml should be (Nodes(List(Text("bar"), EmptyTag("foo")))) // Yes nodes sequences are stored in reverse order
  }
  
  test("Mixed content") {
    val xml = 'foo :: ("bar" | 'baz :: "bah")
    xml should be (Tag("foo", Nodes(List(Tag("baz", Text("bah")), Text("bar")))))
  }
  
  test("HTML 5 DTD") {
    val dtd = html5dtd
    dtd should be (Text("<!DOCTYPE html>\n"))
  }
  
  test("Chaining nodes should not nest them") {
    val nodes = 'foo | 'bar
    nodes should be (Nodes(List(EmptyTag("bar"), EmptyTag("foo"))))
    (nodes | 'baz) should be (Nodes(List(EmptyTag("baz"), EmptyTag("bar"), EmptyTag("foo"))))
  }
  
  test("Chaining Emtpy should skip it") {
    val xml = Empty | Empty | 'foo
    xml should be (EmptyTag("foo"))
  }
}
