package layout

import scala.io.Source

object LayoutParser {
  def parse(input: Source): Layout = {
    println("Lines in layout file: " + input.getLines().length)
    new Layout
  }
}