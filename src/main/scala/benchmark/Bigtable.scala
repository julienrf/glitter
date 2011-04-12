package benchmark

import glitter._

object Bigtable {
  def template(table: Array[Array[Int]]) =
    'table (
        forM (table) (row =>
          'tr :: forM (row) ('td :: _.toString)
        )
    )
  
  def main(args: Array[String]) {
    val table = (for (r <- 1 to 1000) yield (for(c <- 1 to 10) yield (c)).toArray).toArray
    val start = System.currentTimeMillis
    template(table).render
    val stop = System.currentTimeMillis
    
    println("\"Scala\", " + (stop - start))
  }
}
