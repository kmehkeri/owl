package validator

import scala.io.Source
import layout.Layout

object Validator {
  def validate(layout: Layout, input: Source): Boolean = {
    println("Lines in input: " + input.getLines().length)
    true
  }
}
