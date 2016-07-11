/**
  * Created by astaroth on 10.07.16.
  */

import scala.io.Source

object LayoutParser {
  def parse(input: Source): Layout = {
    println("Lines in layout file: " + input.getLines().length)
    new Layout
  }
}
