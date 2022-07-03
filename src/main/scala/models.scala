import scala.util.Random
object models {
  case class Table(val tableNumber: Int, val orders: List[Order]) {
    def toJson() = {
      ujson.Obj("tableNumber" -> tableNumber,
      "orders" -> orders.map{x => List(x.food, x.time)})
    }
  }
  case class Order(val food: String, val time: String = Random.between(5, 16).toString + ":00 Min"){

  }

  def main(args: Array[String]): Unit = {
    val menu = List("pizza", "tacos", "salad")
    val listoforders = menu.map(x =>new Order(x))
    val newtable = new Table(1, listoforders)
    println(newtable.tableNumber)
    println(newtable)
    print(newtable.orders(1).food)

  }
}
