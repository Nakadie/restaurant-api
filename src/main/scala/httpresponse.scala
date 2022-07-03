
import ujson.Value

//extending to cask.MainRoutes sets the default server location to http://localhost:8080
object httpresponse extends cask.MainRoutes{

  @cask.get("/")
  def connectioncheck(): String = {
    "connected"
  }

  //display all tables with their current orders
  @cask.getJson("/tables")
  // no variables. Will display all of the table values in the list, will return an empty list if no orders.
    def displayAll(): Value = {
    val data = ujson.Arr(values.tables.map { x => x.toJson()})
    data(0)
  }

  //display all orders for specific table
  @cask.get("/tables/orders")
  //1 variable: (tableNumber) as json. Will display the table number and orders for a specific table.
  def displayTableOrders(tableNum: cask.Request): Value  = {
    val data = ujson.read(tableNum)
    val tablenum = data("tableNumber").num
    if (values.tables.isEmpty || !values.tables.exists(x => x.tableNumber == tablenum)) {
      "there are no orders"
    } else {
      val table = values.tables.filter { x => x.tableNumber == tablenum }
      val ret = table.head.toJson()
      val arr = ujson.Arr(ret)
      arr(0)
    }
  }

  //place orders for specific table
  @cask.post("/tables/orders")
  //2 variables: (tableNumber, orders) as json. Will place an order if an order is not already in the tables list as new object.
  def placeOrders(req: cask.Request): String = {
    val data = ujson.read(req)
    val tablenum = data("tableNumber").num
    val orders = data("orders").arr
    val ordersList = orders.map { x => models.Order(x.str) }
    val neworder = models.Table(tablenum.toInt, ordersList.toList)
    val currentTables = values.tables.map { x => x.tableNumber }
    if (currentTables.contains(neworder.tableNumber)) {
      "already have order"
    } else {
      values.tables += neworder
      "complete"
    }
  }

  //change a single order from one item to another
  @cask.patch("/tables/orders")
  //takes 3 variables: (tableNumber, newOrder, oldOrder) as json. it will search through tables to find the table and update the orders list as new table object
  def changeOrder(value: cask.Request): String = {
    val data = ujson.read(value)
    val tablenum = data("tableNumber").num
    val newOrder = data("newOrder")
    val oldOrder = data("oldOrder")
    val target = values.tables.filter(x => x.tableNumber == tablenum)
    if (target.isEmpty){
      "does not exist"
    } else {
      val orderlist = target.head.orders.map { x => if (x.food == oldOrder.str) models.Order(newOrder.str) else x }
      values.tables = values.tables.map { x => if (x.tableNumber == tablenum) models.Table(x.tableNumber, orderlist) else x }
      "complete"
    }

  }

  //delete single order from table
  @cask.delete("/tables/orders")
  //takes 2 variables: (tableNumber, deleteOrder) as json. will remove one order from the order list and create new table object
  def removeOrder(value: cask.Request): String = {
    val data = ujson.read(value)
    val tablenum = data("tableNumber").num
    val deleteOrder = data("deleteOrder").str
    val target = values.tables.filter(x => x.tableNumber == tablenum)
    if (target.isEmpty){
      "does not exist"
    }else{
    val orderlist = target.head.orders.filter(x => x.food != deleteOrder)
    values.tables = values.tables.map { x => if (x.tableNumber == tablenum) models.Table(x.tableNumber, orderlist) else x }
    "order deleted"
    }
  }

  //gets single order from table
  @cask.get("tables/orders/order")
  // 2 variables (tableNumber, order) as json. will return the table, order, cooktime as json
  def getOneOrder(value: cask.Request): Value = {
    val data = ujson.read(value)
    val tablenum = data("tableNumber").num
    val order = data("order").str
    val target = values.tables.filter(x => x.tableNumber == tablenum)
    if (target.isEmpty) {
      "does not exist"
    } else {
      val display = target.head.orders.filter(x => x.food == order)
      ujson.Obj(
        "tableNumber" -> tablenum,
        "order" -> display.head.food,
        "cooktime" -> display.head.time
      )
    }
  }


  initialize()
}
