import scala.util.Random
object models {
  class Table(var tableNumber: Int, var order: List[Order]) {
  }
  class Order(var order: String){
    val time = Random.between(5, 16).toString + ":00 Min"
    println(time)
  }

  def main(args: Array[String]): Unit = {
    val orders = List("pizza", "tacos", "salad")
    val listoforders = orders.map(x =>new Order(x))
    val newtable = new Table(1, listoforders)
    println(newtable.tableNumber)
    println(newtable)

  }
}
