/**
  * Created by astaroth on 05.07.16.
  */

import java.nio.file.{Paths, Files}
import scala.io.Source

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
      val layout = LayoutParser.parse(layoutSource)

      // Read and validate inputs
      val inputSources =
        if (args.tail.isEmpty) List(Source.fromInputStream(System.in)) else args.toList.tail.map(Source.fromFile)

      inputSources.foreach(Validator.validate(layout, _))
    }
    catch {
      case e: java.io.IOException => println("ERROR: " + e.getMessage)
    }
  }
}
