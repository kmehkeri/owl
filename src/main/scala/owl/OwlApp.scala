package owl

import scala.io.Source
import layout._
import validator._
import scala.util.{Try,Success,Failure}

object OwlApp {
  def main(args: Array[String]): Unit = {
    // Usage
    if (args.isEmpty || args.head == "-h" || args.head == "--help") {
      println("Usage: owl LAYOUT [INPUT1 [INPUT2 [...] ] ]")
      return
    }

    try {
      // Read and parse layout file
      val layoutSource = Source.fromFile(args.head)
      val layout = LayoutParser.fromString(layoutSource.getLines.mkString)

      // Read and validate inputs
      val inputSources =
        if (args.tail.isEmpty) List(Source.fromInputStream(System.in)) else args.toList.tail.map(Source.fromFile)
      
      layout match {
        case Success(l) => inputSources.foreach(Validator.validate(l, _))
        case Failure(ex) => println(ex.getMessage)
      }
    }
    catch {
      case e: java.io.IOException => println("ERROR: " + e.getMessage)
    }
  }
}
