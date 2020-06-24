import com.boom._

// case class CollX(a: scala.collection.immutable.HashSet[String])

object Main {

  def main(args: Array[String]): Unit = {
    println(LookSee.diveInto[scala.collection.immutable.HashSet[String]])
  }
}
