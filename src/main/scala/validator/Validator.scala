package validator

import scala.io.Source
import scala.util.{Try,Success,Failure}
import layout.Layout

object Validator {
  def validate(layout: Layout, input: Source): Try[Boolean] = {
    //println("Lines in input: " + input.getLines().length)
    Success(true)
  }
}
