import ujson.Obj

import scala.util.Random
object models {
  //table class has a table number and a list of order objects.
  case class Table(tableNumber: Int, orders: List[Order]) {
    // will transform the table object into json style text
    def toJson(): Obj = {
      ujson.Obj("tableNumber" -> tableNumber,
        "orders" -> orders.map { x => List(x.food, x.time) })
    }
  }
  //order has two variables: (string of food, time) Time is randomly set between 5 and 15 min if not provided
  case class Order(food: String, time: String = Random.between(5, 16).toString + ":00 Min"){
  }
}
