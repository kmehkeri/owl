package layout

import scala.util.matching.Regex
import scala.util.parsing.combinator.RegexParsers

sealed trait Assertion
case class AnythingAssertion() extends Assertion
case class NumberAssertion() extends Assertion
case class StringAssertion(string: String) extends Assertion

sealed trait Split
case class FixedSplit() extends Split
case class DelimitedSplit(delimiter: String) extends Split
case class LineSplit() extends Split

case class Sub(split: Split, blocks: List[Block])

sealed trait Map
case class AnyMap() extends Map
case class FirstItemMap() extends Map
case class LastItemMap() extends Map
case class RangeMap(from: Int, to: Int = -1) extends Map
case class RegexMap(regex: Regex) extends Map

case class Block(map: Map, definition: String)

object LayoutParser extends RegexParsers {

  // Primitives
  def int: Parser[Int] = "[0-9]+".r ^^ { _.toInt }
  def string = """"[^"]*?"""".r ^^ { str => str.substring(1, str.length - 1) }
  def regexp =  """/[^/]*?/""".r ^^ { str => str.substring(1, str.length - 1).r }

  // Assertions
  def anything_assertion = "ANYTHING" ^^ { _ => AnythingAssertion() }
  def number_assertion = "NUMBER" ^^ { _ => NumberAssertion() }
  def string_assertion = string ^^ { s => StringAssertion(s) }
  def assertion = anything_assertion | number_assertion | string_assertion

  // Splits
  def fixed_split = "FIXED" ^^ { _ => FixedSplit() }
  def delimited_split = ("SPLIT" <~ "BY") ~ string ^^ { case _ ~ delimiter => DelimitedSplit(delimiter) }
  def line_split = "LINES" ^^ { _ => LineSplit() }
  def split = fixed_split | delimited_split | line_split

  // Sub
  def sub = split ~ blocks ^^ { case s ~ b => Sub(s, b) }

  // Definition
  def definition = assertion | sub

  // Map
  def any_map = "*" ^^ { _ => AnyMap() }
  def first_item_map = "^" ^^ { _ => FirstItemMap() }
  def last_item_map = "$" ^^ { _ => LastItemMap() }
  def range_map = int ~ (":" ~> int) ^^ { case from ~ to => RangeMap(from, to) }
  def position_map = int ^^ { case pos => RangeMap(pos, pos) }
  def regex_map = regexp ^^ { r => RegexMap(r) }
  def map = any_map | first_item_map | last_item_map | range_map | position_map | regex_map

  // Block
  def block: Parser[Block] = map ~ ("=>" ~> definition) ^^ {
                               case map ~ definition => Block(map, definition.toString)
                             }
  def blocks = block.*

  // Entry point
  def fromString(input: String): Layout = {
    parse(block, input) match {
      case Success(matched, _) => println(matched)
      case Failure(msg, _) => println(msg)
      case Error(msg, _) => println(msg)
    }
    new Layout
  }
}
