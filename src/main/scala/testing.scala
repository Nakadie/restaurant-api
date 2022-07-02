import ujson.Null.obj
import upickle.default._
import upickle.core.{Annotator, TraceVisitor}
import upickle.legacy.reader

import java.io.Reader

object testing extends App {
  val x = ujson.Obj(
    "value1" -> "apple",
    "value2" -> List("pizza", "salad")
  )
  val y = ujson.read(x)

  print(y)

}