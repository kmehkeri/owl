/**
  * Created by astaroth on 05.07.16.
  */

import java.nio.file.{Paths, Files}

object OwlApp {
  def main(args: Array[String]): Unit = {
    // Usage
    if (args.isEmpty || args.head == "-h" || args.head == "--help") {
      println("Usage: owl LAYOUT [INPUT1 [INPUT2 [...] ] ]")
      return
    }

    // Validate arguments
    args foreach { file =>
      println(file + "\t" + (if (Files.isRegularFile(Paths.get(file))) "is ok" else "does not exist or is not a readable file!"))
    }

    // Read and parse layout file
    val layout = LayoutParser.parse(args.head)

    // Read and validate inputs
    args.tail.foreach { file => Validator.validate(layout, file) }
  }
}
