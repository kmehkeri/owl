/**
  * Created by astaroth on 10.07.16.
  */

import scala.io.Source

object Validator {
  def validate(layout: Layout, input: Source): Boolean = {
    println("Lines in input: " + input.getLines().length)
    true
  }
}
