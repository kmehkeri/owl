/**
  * Created by astaroth on 05.07.16.
  */
object OwlApp {
  def main(args: Array[String]): Unit = {
    if (args.isEmpty || args.head == "-h" || args.head == "--help")
      println("Usage: owl LAYOUT [INPUT1 [INPUT2 [...] ] ]")
    else
      println("Hello " + args.mkString(" "))
  }
}
