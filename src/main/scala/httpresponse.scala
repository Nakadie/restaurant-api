
import ujson.{Arr, Js, Obj, Value, read}
import upickle.default._

//extending to cask.MainRoutes sets the default server location to http://localhost:8080
object httpresponse extends cask.MainRoutes{

  //display all tables with their current orders
  @cask.getJson("/tables")
  // no variables. Will display all of the table values in the list, will return an empty list if no orders.
    def displayAll() = {
    val data = ujson.Arr(values.tables.map { x => x.toJson()})
    data(0)
  }

  //display all orders for specific table
  @cask.get("/tables/orders")
  //1 variable: (tableNumber) as json. Will display the table number and orders for a specific table.
  def displayTableOrders(tableNum: cask.Request): Value = {
    val data = ujson.read(tableNum)
    val table = values.tables.filter { x => x.tableNumber == data("tableNumber").value }
    val ret = table(0).toJson()
    val arr = ujson.Arr(ret)
    arr(0)
  }

  //place orders for specific table
  @cask.post("/tables/orders")
  //2 variables: (tableNumber, orders) as json. Will place an order if an order is not already in the tables list.
  def placeOrders(req: cask.Request) = {
    val data = ujson.read(req)
    val orders = data("orders").arr
    val ordersList = orders.map{x => models.Order(x.str)}
    val neworder = models.Table(data("tableNumber").num.toInt, ordersList.toList)
    val currentTables = values.tables.map{x => x.tableNumber}
    if (currentTables.contains(neworder.tableNumber)) {
      "already have order"
    } else {
      values.tables += neworder
      "complete"
    }

  }

  //change a single order from one item to another
  @cask.patch("/tables/orders")
  //takes 3 variables tableNumber, newOrder, oldOrder. it will search through tables to find the table and update the orders list
  def changeOrder(value: cask.Request): String = {
    val data = ujson.read(value)
    val target = values.tables.filter(x => x.tableNumber == data("tableNumber").num)
    val newOrder = data("newOrder")
    val oldOrder = data("oldOrder")
    val orderlist = target(0).orders.map { x => if (x.food == oldOrder.str) models.Order(newOrder.str) else x }
    values.tables = values.tables.map { x => if (x.tableNumber == data("tableNumber").num) models.Table(x.tableNumber, orderlist) else x }
    "complete"
  }

  initialize()
}