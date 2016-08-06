import org.scalatest.{FlatSpec,Matchers}
import scala.util.{Try,Success,Failure}
import layout._

class LayoutSpec extends FlatSpec with Matchers {
  val badLayout = "CRAP"
  val goodLayout = "* => ANYTHING"

  // Null layout
  "LayoutParser" should "return Failure if empty string is provided for parsing" in {
    val lp = new LayoutParser
    val layout = lp.fromString("")
    layout shouldBe a [Failure[_]]
    layout.failed.get shouldBe a [LayoutException]
  }

  // Broken layout
  it should "return Failure if provided string is not a correct layout" in {
    val lp = new LayoutParser
    val layout = lp.fromString(badLayout)
    layout shouldBe a [Failure[_]]
    layout.failed.get shouldBe a [LayoutException]
  }

  // Good layout with stuff at the end
  it should "return Failure if layout has some trash at the end" in {
    val lp = new LayoutParser
    val layout = lp.fromString(goodLayout + "\n" + "more")
    layout shouldBe a [Failure[_]]
    layout.failed.get shouldBe a [LayoutException]
  }

  // Simple layout
  it should "parse simple layout and return it containing a block" in {
    val lp = new LayoutParser
    val layout = lp.fromString(goodLayout)
    layout shouldBe a [Success[_]]
    layout.get.mainBlock shouldBe Block(AnyMap(), AnythingAssertion())
  }

}

