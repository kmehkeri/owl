package validator

import scala.io.Source
import scala.util.{Try,Success,Failure}
import layout.Layout

class Validator(debug: Boolean = false) {
  def validate(layout: Layout, input: Source): Try[Boolean] = {
    if (debug) println("[DEBUG] Lines in input: " + input.getLines().length)
    Success(true)
  }
}
