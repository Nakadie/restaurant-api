import ujson.Arr

import scala.collection.mutable.ListBuffer

object values {
  val x = new models.Table(1, List(new models.Order("pizza"),new models.Order(("stew"))))
  val y = new models.Table(2, List(new models.Order(("salad"))))
  class tables()

  var tables: ListBuffer[models.Table] = ListBuffer(x)
  /*def updateTables(newOrder: models.Table): Unit ={
    val values.tables = values.tables.map{
      x => if (x.tableNumber == newOrder.tableNumber) newOrder else x
    }
  }*/

  val z: Arr = ujson.Arr(values.tables.map { x => x.toJson()})
  val a = tables.filter { x => x.tableNumber == 1}

  def main(args: Array[String]): Unit ={
    println(ujson.Arr(a(0).toJson()))

    println(tables)
  }
}
