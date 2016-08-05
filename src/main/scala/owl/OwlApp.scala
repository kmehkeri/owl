package owl

import scala.io.Source
import scala.util.{Try,Success,Failure}
import scopt.OptionParser
import layout._
import validator._

object OwlApp {
  case class Config(layout: String = "", inputs: List[String] = Nil,
                    debug: Boolean = false, silent: Boolean = false)

  def options(args: Array[String]): Config = {
    val parser = new OptionParser[Config]("owl") {
      head("owl", "1.0")
      help("help").text("prints this usage text")
      opt[Unit]('d', "debug").action( (_, c) => c.copy(debug = true) ).text("display debug messages")
      arg[String]("LAYOUT").action( (x, c) => c.copy(layout = x) ).text("layout file")
      arg[String]("INPUT [INPUT [...]]").optional.unbounded.action( (x, c) => c.copy(inputs = c.inputs :+ x) ).text("input files")
    }

    parser.parse(args, Config()) match {
      case Some(config) => config
      case None => sys.exit(1)
    }
  }

  def main(args: Array[String]): Unit = {
    try {
      // Options and arguments
      val config = options(args)

      // Read and parse layout file
      val layoutSource = Source.fromFile(config.layout)
      val layoutParser = new LayoutParser(config.debug)
      val layout = layoutParser.fromString(layoutSource.getLines.mkString).get

      // Read and validate inputs
      val inputSources =
        if (config.inputs.isEmpty) List(Source.fromInputStream(System.in)) else config.inputs.map(Source.fromFile)
      val validator = new Validator(config.debug)
      
      inputSources.foreach {
        validator.validate(layout, _) match {
          case Success(s) => println("OK")
          case Failure(f) => println("Validation error: " + f.getMessage)
        }
      }
    }
    catch {
      case e: Exception => println("ERROR: " + e.getMessage)
    }
  }
}
