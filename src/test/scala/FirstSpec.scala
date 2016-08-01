import org.scalatest.{FlatSpec,Matchers}

class FirstSpec extends FlatSpec with Matchers {
  "Everything" should "be ok" in {
    val everything = "ok"
	everything should be ("ok")
  }
}

