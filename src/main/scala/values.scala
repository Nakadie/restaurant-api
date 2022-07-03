import ujson.Arr

import scala.collection.mutable.ListBuffer

object values {
  //this is the tables value. it has a list of table objects. It is mutable which makes it not concurrency safe.
  var tables: ListBuffer[models.Table] = ListBuffer()
}
