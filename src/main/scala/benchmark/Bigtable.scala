package benchmark

import glitter._
import glitter.renderer._

object Bigtable {
  def template(table: Array[Array[Int]]) =
    'table (
        for (row <- table) yield {
          'tr :: (for (col <- row) yield ('td :: col.toString))
        }
    )
  
  def bench(name: String, exec: => Unit) = {
    val start = System.currentTimeMillis
    exec
    val stop = System.currentTimeMillis
    
    println("\""+name+"\", " + (stop - start))
  }
    
  def main(args: Array[String]) {
    val table = (for (r <- 1 to 1000) yield (for(c <- 1 to 10) yield (c)).toArray).toArray
    
    bench("Glitter", template(table).render(TextRenderer))
    bench("Glitter (buffered)", template(table).render(BufferedTextRenderer))
  }
}
