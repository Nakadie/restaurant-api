
import ujson.{Arr, Js, Obj, Value, read}
import upickle.default._

//extending to cask.MainRoutes sets the default server location to http://localhost:8080
object httpresponse extends cask.MainRoutes{

  @cask.get("/")
  def index() = {
    "Hello!"
  }

  //display all tables with their current orders ########done
  @cask.getJson("/tables")
    def displayAll() = {
    ujson.Arr(values.tables.map { x => x.toJson()})
  }

  //display all orders for specific table ######### done
  @cask.get("/tables/orders")
  def displayTableOrders(tableNum: cask.Request): Value = {
    val data = ujson.read(tableNum)
    val table = values.tables.filter { x => x.tableNumber == data("tableNumber").value }
    val ret = table(0).toJson()
    val arr = ujson.Arr(ret)
    arr(0)
  }


  //place orders for specific table #######done
  @cask.post("/tables/orders")
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
  def changeOrder(value: cask.Request): String = {
    val data = ujson.read(value)
    val target = values.tables.filter(x => x.tableNumber == data("tableNumber").num)
    val newOrder = data("newOrder")
    val oldOrder = data("oldOrder")
    val orderlist = target(0).orders.map { x => if (x.food == oldOrder.str) models.Order(newOrder.str) else x }
    values.tables = values.tables.map { x => if (x.tableNumber == data("tableNumber").num) models.Table(x.tableNumber, orderlist) else x }
    "complete"
  }


  //10 threads and make sure each makes a request.
  //return as json
  initialize()
}